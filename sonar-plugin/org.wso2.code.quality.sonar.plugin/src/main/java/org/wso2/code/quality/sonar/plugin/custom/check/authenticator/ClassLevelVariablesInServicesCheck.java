/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.code.quality.sonar.plugin.custom.check.authenticator;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;

/**
 * Custom check to see if class level variables exist in specific services.
 */
@Rule(key = "ClassLevelVariablesInServicesCheck")

public class ClassLevelVariablesInServicesCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private boolean inMethod;
    private boolean annotationExists;
    private static final String MESSAGE = "Do not use class level variables in WSO2 Specific Services.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {

        annotationExists = false;
        inMethod = false;

        setAnnotationIfExists(tree.symbol());
        checkAnnotationInInterfaces(tree.superInterfaces());
        checkAnnotationInSuperClass(tree.superClass());

        super.visitClass(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {

        if (annotationExists && (!inMethod) && (!tree.symbol().isStatic() || !tree.symbol().isFinal())) {
            context.reportIssue(this, tree, MESSAGE);
        }
        super.visitVariable(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {

        inMethod = true;
        super.visitMethod(tree);
    }

    private void checkAnnotationInInterfaces(List<TypeTree> interfaces) {

        // Checks if immediate interfaces implemented have the annotation 'WSO2SpecificService'.
        for (TypeTree currentInterface : interfaces) {
            setAnnotationIfExists(currentInterface.symbolType().symbol());
            if (annotationExists) {
                return;
            }
        }
    }

    private void checkAnnotationInSuperClass(TypeTree superClass) {

        // Checks if any of the superclasses has the annotation 'WSO2SpecificService'.
        while (superClass != null) {
            setAnnotationIfExists(superClass.symbolType().symbol());
            // Checks if any of the interfaces in the inheritance chain include the annotation 'WSO2 Specific Service'.
            checkSuperInterfacesOfSuperClass(superClass.symbolType().symbol().interfaces());
            if (annotationExists) {
                return;
            }
            superClass = superClass.symbolType().symbol().declaration().superClass();
        }
    }

    private void checkSuperInterfacesOfSuperClass(List<Type> superInterfacesOfSuperClass) {

        for (Type currentInterface : superInterfacesOfSuperClass) {
            setAnnotationIfExists(currentInterface.symbol());
            if (annotationExists) {
                return;
            }
        }
    }

    private void setAnnotationIfExists(Symbol.TypeSymbol symbol) {

        if (symbol.metadata().isAnnotatedWith("WSO2SpecificService")) {
            annotationExists = true;
        }
    }
}
