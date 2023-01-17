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

import org.junit.jupiter.api.Test;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.wso2.util.PluginConstants.WSO2_REPOSITORY_KEY;
import static org.wso2.util.PluginConstants.WSO2_REPOSITORY_NAME;

class RulesDefinitionImplTest {

  @Test
  void test() {

    RulesDefinitionImpl rulesDefinitionImpl = new RulesDefinitionImpl();
    org.sonar.api.server.rule.RulesDefinition.Context context = new org.sonar.api.server.rule.RulesDefinition.Context();
    rulesDefinitionImpl.define(context);
    Repository repository = context.repository(WSO2_REPOSITORY_KEY);

    assert repository != null;
    assertThat(repository.name()).isEqualTo(WSO2_REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo("java");
    assertThat(repository.rules()).hasSize(RulesList.getChecks().size());
    assertThat(repository.rules().stream().filter(Rule::template)).isEmpty();
    assertAllRuleParametersHaveDescription(repository);
  }

  private static void assertAllRuleParametersHaveDescription(Repository repository) {

    for (Rule rule : repository.rules()) {
      for (Param param : rule.params()) {
        assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
      }
    }
  }

}
