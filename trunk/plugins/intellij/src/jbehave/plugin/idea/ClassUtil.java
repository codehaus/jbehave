package org.jbehave.plugin.idea;

import com.intellij.psi.PsiClass;

public class ClassUtil {
    public static String fullName(PsiClass psiClass) {
        return psiClass.getQualifiedName();
    }

    public static String shortName(String fullName) {
        int index = fullName.lastIndexOf(".");
        return index == -1 ? fullName : fullName.substring(index + 1, fullName.length());
    }

    public static String shortName(PsiClass psiClass) {
        return shortName(fullName(psiClass));
    }
}
