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

package org.wso2;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

import java.util.List;

import static org.wso2.util.PluginConstants.WSO2_REPOSITORY_KEY;

/**
 * Provide the "checks" (implementations of rules) classes that are going be executed during
 * source code analysis.
 *
 * This class is a batch extension by implementing the {@link org.sonar.plugins.java.api.CheckRegistrar} interface.
 */
@SonarLintSide
public class FileCheckRegistrar implements CheckRegistrar {

    /**
     * Register the classes that will be used to instantiate checks during analysis.
     */
    @Override
    public void register(RegistrarContext registrarContext) {

        // Call to registerClassesForRepository to associate the classes with the correct repository key.
        registrarContext.registerClassesForRepository(WSO2_REPOSITORY_KEY, checkClasses(),
                testCheckClasses());
    }

    /**
     * Lists all the main checks provided by the plugin.
     */
    public static List<Class<? extends JavaCheck>> checkClasses() {

        return RulesList.getJavaChecks();
    }

    /**
     * Lists all the test checks provided by the plugin.
     */
    public static List<Class<? extends JavaCheck>> testCheckClasses() {

        return RulesList.getJavaTestChecks();
    }
}
