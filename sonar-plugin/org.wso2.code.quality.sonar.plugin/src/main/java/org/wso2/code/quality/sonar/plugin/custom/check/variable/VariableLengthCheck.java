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

package org.wso2.code.quality.sonar.plugin.custom.check.variable;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ForStatementTree;

import org.sonar.plugins.java.api.tree.VariableTree;

/**
 * Custom check to see if variable name length exceeds 1.
 */
@Rule(key = "VariableLengthCheck")
public class VariableLengthCheck extends BaseTreeVisitor implements JavaFileScanner {

    private int inForOrCatchLevel;
    private JavaFileScannerContext context;

    private static final String MESSAGE = "Variable name length should be more than 1.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {

        inForOrCatchLevel++;
        super.visitForStatement(tree);
    }

    @Override
    public void visitCatch(CatchTree tree) {

        inForOrCatchLevel++;
        super.visitCatch(tree);
    }
    
    @Override
    public void visitVariable(VariableTree tree) {

        if (tree.simpleName().name().length() <= 1 && inForOrCatchLevel < 1) {
            context.reportIssue(this, tree.simpleName(), MESSAGE);
        }

        if (inForOrCatchLevel >= 1) {
            inForOrCatchLevel--;
        }
        super.visitVariable(tree);
    }
}
