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

package org.wso2.code.quality.sonar.plugin.custom.check.variable.classname;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.util.Objects;

/**
 * Custom check to see if class names contain the word 'Class' or 'Enum'.
 */
@SuppressWarnings("all")
@Rule(key = "ClassNameCheck")
public class ClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private static final String MESSAGE =
            "Don’t use the word ‘Class’ as part of class names or ‘Enum’ as part of enum class names.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {

        String className = Objects.requireNonNull(tree.simpleName()).name();

        if (StringUtils.containsIgnoreCase(className, "class") || StringUtils.containsIgnoreCase(className, "enum")) {
            context.reportIssue(this, tree, MESSAGE);
        }
        super.visitClass(tree);
    }
}
