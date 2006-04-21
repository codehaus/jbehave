package jbehave.plugin.eclipse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

public class BehaviorSearchEngine {

	private static final String ZHU = Signature.SIG_VOID;

	public static IType findBehaviors(final Object selection)
			throws InterruptedException, InvocationTargetException {
		final Set result = new HashSet();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor pm) throws InterruptedException {
				doFindBehaviours(selection, result, pm);
			}
		};
		PlatformUI.getWorkbench().getProgressService()
				.busyCursorWhile(runnable);
		if (result.size() == 0) {
			return null;
		}
		return ((IType[]) result.toArray(new IType[result.size()]))[0];
	}

	public static void doFindBehaviours(Object selection, Set result,
			IProgressMonitor pm) throws InterruptedException {
		pm.beginTask("Searching behavior...", 1);
		try {
			try {
				collectTypes(selection, new SubProgressMonitor(pm, 1), result);
			} catch (CoreException e) {
				JBehavePlugin.log(e.getStatus());
			}
			if (pm.isCanceled()) {
				throw new InterruptedException();
			}
		} finally {
			pm.done();
		}
	}

	private static void collectTypes(Object element, IProgressMonitor pm,
			Set result) throws CoreException/* , InvocationTargetException */{
		pm.beginTask("Searching behavior...", 10);
		element = computeScope(element);
		try {
			while ((element instanceof ISourceReference)
					&& !(element instanceof ICompilationUnit)) {
				if (element instanceof IType) {
					if (isBehaviorType((IType) element)) {
						result.add(element);
						return;
					}
				}
				element = ((IJavaElement) element).getParent();
			}
			if (element instanceof ICompilationUnit) {
				ICompilationUnit cu= (ICompilationUnit)element;
				IType[] types= cu.getAllTypes();
	
				for (int i= 0; i < types.length; i++) {
					if (isBehaviorType(types[i])) {
						result.add(types[i]);
					}
				}
			} 
//			else if (element instanceof IJavaElement) {
//			    List testCases= findTestCases((IJavaElement)element, new SubProgressMonitor(pm, 7));
//				List suiteMethods= searchSuiteMethods(new SubProgressMonitor(pm, 3), (IJavaElement)element);			
//				while (!suiteMethods.isEmpty()) {
//					if (!testCases.contains(suiteMethods.get(0))) {
//						testCases.add(suiteMethods.get(0));
//					}
//					suiteMethods.remove(0);
//				}
//				result.addAll(testCases);
//			}
		} finally {
			pm.done();
		}
	}

	public static boolean isBehaviorType(IType element)
			throws JavaModelException {
		return isPublicConcrete(element)
				&& (hasShouldMethod(element) || implementingBehaviors(element));
	}

	private static boolean implementingBehaviors(IType type)
			throws JavaModelException {
		IType[] interfaces = type.newSupertypeHierarchy(null)
				.getAllSuperInterfaces(type);
		for (int i = 0; i < interfaces.length; i++)
			if (interfaces[i].getFullyQualifiedName('.').equals(
					JBehavePlugin.BEHAVIOR_INTERFACE_NAME))
				return true;
		return false;
	}

	private static boolean isPublicConcrete(IType type)
			throws JavaModelException {
		int flags = type.getFlags();
		return Flags.isPublic(flags) && !Flags.isAbstract(flags);
	}

	private static Object computeScope(Object element)
			throws JavaModelException {
		// if (element instanceof IFileEditorInput)
		// element= ((IFileEditorInput)element).getFile();
		if (element instanceof IResource)
			element = JavaCore.create((IResource) element);
		if (element instanceof IClassFile) {
			IClassFile cf = (IClassFile) element;
			element = cf.getType();
		}
		return element;
	}

	private static boolean hasShouldMethod(IType type)
			throws JavaModelException {
		IType[] thisAndAllSuperTypes = type.newSupertypeHierarchy(null).getAllTypes();
		for (int i = 0; i < thisAndAllSuperTypes.length; i++) {
			if (typeHasShouldMethod(thisAndAllSuperTypes[i])) {
				return true;
			}
		}
		return false;
	}

	private static boolean typeHasShouldMethod(IType type) throws JavaModelException {
		IMethod[] methods = type.getMethods();
		for (int i = 0; i < methods.length; i++) {
			IMethod method = methods[i];
			if (method.getElementName().startsWith("should")
					&& isPublicVoid(method)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPublicVoid(IMethod method)
			throws JavaModelException {
		int flags = method.getFlags();
		return Flags.isPublic(flags) && !Flags.isStatic(flags)
				&& !Flags.isAbstract(flags)
				&& method.getReturnType().equals(ZHU);
	}

}
