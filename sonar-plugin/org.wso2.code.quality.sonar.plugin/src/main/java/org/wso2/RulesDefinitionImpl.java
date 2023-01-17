/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import static org.wso2.util.PluginConstants.JAVA;
import static org.wso2.util.PluginConstants.RESOURCE_PATH;
import static org.wso2.util.PluginConstants.WSO2_REPOSITORY_KEY;
import static org.wso2.util.PluginConstants.WSO2_REPOSITORY_NAME;

import java.util.ArrayList;

/**
 * Declare rule metadata in server repository of rules.
 * That allows to list the rules in the page "Rules".
 */
public class RulesDefinitionImpl implements RulesDefinition {

    @Override
    public void define(Context context) {

        NewRepository repository = context.createRepository(WSO2_REPOSITORY_KEY, JAVA).setName(WSO2_REPOSITORY_NAME);
        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_PATH);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(RulesList.getChecks()));
        repository.done();
    }
}
