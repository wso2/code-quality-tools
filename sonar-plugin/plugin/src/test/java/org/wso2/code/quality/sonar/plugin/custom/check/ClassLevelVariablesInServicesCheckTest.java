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

package org.wso2.code.quality.sonar.plugin.custom.check;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;
import org.wso2.code.quality.sonar.plugin.custom.check.authenticator.ClassLevelVariablesInServicesCheck;

class ClassLevelVariablesInServicesCheckTest {

    @Test
    void test() {

        CheckVerifier.newVerifier()
                .onFile("src/test/sonar/rules/test/files/ClassLevelVariablesInServicesCheck.java")
                .withCheck(new ClassLevelVariablesInServicesCheck())
                .withClassPath(FilesUtils.getClassPath("target/test-jars"))
                .verifyIssues();
    }
}
