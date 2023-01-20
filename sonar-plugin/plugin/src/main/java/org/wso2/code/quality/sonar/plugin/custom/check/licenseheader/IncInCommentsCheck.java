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

package org.wso2.code.quality.sonar.plugin.custom.check.licenseheader;

import org.sonar.check.Rule;
import org.sonar.java.checks.CommentContainsPatternChecker;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

/**
 * Custom check to see if Inc. exists in license header.
 */
@Rule(key = "IncInComments")
public class IncInCommentsCheck extends IssuableSubscriptionVisitor {

    private static final String PATTERN = "WSO2 Inc.";
    private static final String MESSAGE = "Remove usage of this \"WSO2 Inc.\" comment. Replace it with LLC.";
    private final CommentContainsPatternChecker checker = new CommentContainsPatternChecker(this, PATTERN,
            MESSAGE);

    @Override
    public List<Tree.Kind> nodesToVisit() {

        return Collections.singletonList(Tree.Kind.TRIVIA);
    }

    @Override
    public void visitTrivia(SyntaxTrivia syntaxTrivia) {

        checker.checkTrivia(syntaxTrivia);
    }
}
