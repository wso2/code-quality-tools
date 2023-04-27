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
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import static org.wso2.code.quality.sonar.plugin.util.PluginConstants.END_TENANT_FLOW;
import static org.wso2.code.quality.sonar.plugin.util.PluginConstants.START_TENANT_FLOW;

/**
 * Custom check to see if endTenantFlow() is missing after a startTenantFlow() invocation.
 */
@Rule(key = "MissingEndTenantFlowCheck")
public class MissingEndTenantFlowCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private boolean startTenantFlowExists;
    private boolean endTenantFlowExists;
    private static final String MESSAGE = "endTenantFlow() is missing in the finally block.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {

        endTenantFlowExists = false;
        startTenantFlowExists = false;
        scan(tree.block());

        if (startTenantFlowExists) {
            scan(tree.finallyBlock());
            if (!endTenantFlowExists) {
                context.reportIssue(this, tree, MESSAGE);
            }
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {

        String methodInvocationName = tree.symbol().name();

        if (StringUtils.containsIgnoreCase(methodInvocationName, START_TENANT_FLOW)) {
            startTenantFlowExists = true;
            return;
        }

        if (startTenantFlowExists && StringUtils.containsIgnoreCase(methodInvocationName, END_TENANT_FLOW)) {
            endTenantFlowExists = true;
        }
    }
}
