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

package org.wso2.code.quality.sonar.plugin.custom.check.tenantflow;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BreakStatementTree;
import org.sonar.plugins.java.api.tree.ContinueStatementTree;
import org.sonar.plugins.java.api.tree.DoWhileStatementTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.SwitchStatementTree;
import org.sonar.plugins.java.api.tree.ThrowStatementTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;

import static org.wso2.code.quality.sonar.plugin.util.PluginConstants.END_TENANT_FLOW;
import static org.wso2.code.quality.sonar.plugin.util.PluginConstants.START_TENANT_FLOW;

/**
 * Custom check to make sure TenantFlow() invocations are inside try/finally blocks.
 * Scenario 1 - Checks whether endTenantFlow() is called in the finally block only.
 * Scenario 2 - Checks whether startTenantFlow() is in a try block and it is the first line in a try block.
 */
@Rule(key = "TenantFlowCheck")
public class TenantFlowCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private boolean inTryStatement;
    private boolean inFinallyBlock;
    private boolean inFirstLine;
    private static final String END_TENANT_FLOW_MESSAGE = "endTenantFlow() should be in the finally block only.";
    private static final String START_TENANT_FLOW_MESSAGE = "startTenantFlow() should be the first line in a" +
            " try block.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {

        inTryStatement = true;
        inFirstLine = true;
        scan(tree.block());
        inTryStatement = false;

        scan(tree.catches());

        inFinallyBlock = true;
        scan(tree.finallyBlock());
        inFinallyBlock = false;
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {

        String methodInvocationName = tree.symbol().name();

        if (StringUtils.containsIgnoreCase(methodInvocationName, END_TENANT_FLOW) && !inFinallyBlock) {
            context.reportIssue(this, tree, END_TENANT_FLOW_MESSAGE);
            return;
        }

        if (StringUtils.containsIgnoreCase(methodInvocationName, START_TENANT_FLOW)
                && (!inTryStatement || !inFirstLine)) {
            context.reportIssue(this, tree, START_TENANT_FLOW_MESSAGE);
        }

        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {

        inFirstLine = false;
        super.visitVariable(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {

        inFirstLine = false;
        super.visitMethod(tree);
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {

        inFirstLine = false;
        super.visitForStatement(tree);
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {

        inFirstLine = false;
        super.visitForEachStatement(tree);
    }

    @Override
    public void visitWhileStatement(WhileStatementTree tree) {

        inFirstLine = false;
        super.visitWhileStatement(tree);
    }

    @Override
    public void visitDoWhileStatement(DoWhileStatementTree tree) {

        inFirstLine = false;
        super.visitDoWhileStatement(tree);
    }

    @Override
    public void visitSwitchStatement(SwitchStatementTree tree) {

        inFirstLine = false;
        super.visitSwitchStatement(tree);
    }

    @Override
    public void visitReturnStatement(ReturnStatementTree tree) {

        inFirstLine = false;
        super.visitReturnStatement(tree);
    }

    @Override
    public void visitThrowStatement(ThrowStatementTree tree) {

        inFirstLine = false;
        super.visitThrowStatement(tree);
    }

    @Override
    public void visitContinueStatement(ContinueStatementTree tree) {

        inFirstLine = false;
        super.visitContinueStatement(tree);
    }

    @Override
    public void visitBreakStatement(BreakStatementTree tree) {

        inFirstLine = false;
        super.visitBreakStatement(tree);
    }
}
