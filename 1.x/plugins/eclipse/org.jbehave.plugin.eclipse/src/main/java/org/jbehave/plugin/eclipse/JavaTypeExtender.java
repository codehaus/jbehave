package org.jbehave.plugin.eclipse;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public class JavaTypeExtender extends PropertyTester {
	private static final String PROPERTY_IS_Behavior = "isBehaviour"; //$NON-NLS-1$

	/**
	 * @inheritDoc
	 */
	public boolean test(Object receiver, String method, Object[] args,
			Object expectedValue) {
		IJavaElement javaElement = null;
		if (receiver instanceof IAdaptable) {
			javaElement = (IJavaElement) ((IAdaptable) receiver)
					.getAdapter(IJavaElement.class);
		}
		if (javaElement != null) {
			if (!javaElement.exists()) {
				return false;
			}
		}
//		if (javaElement instanceof IJavaProject
//				|| javaElement instanceof IPackageFragmentRoot
//				|| javaElement instanceof IPackageFragment) {
//			return true;
//		}
		if (javaElement != null) {
			if (PROPERTY_IS_Behavior.equals(method)) { //$NON-NLS-1$
				return isBehaviour(javaElement);
			}
		}
		return false;
	}

	private boolean isBehaviour(IJavaElement element) {
		try {
			IType testType = null;
			if (element instanceof ICompilationUnit) {
				ICompilationUnit cu = (ICompilationUnit) element;
				testType = cu.getType(Signature.getQualifier(cu
						.getElementName()));
			} else if (element instanceof IClassFile) {
				testType = ((IClassFile) element).getType();
			} else if (element instanceof IType) {
				testType = (IType) element;
			} else if (element instanceof IMember) {
				testType = ((IMember) element).getDeclaringType();
			}
			if (testType != null && testType.exists()
					&& BehaviourSearchEngine.isBehaviorType(testType)) {
				return true;
			}
		} catch (JavaModelException e) {
		}
		return false;
	}
}