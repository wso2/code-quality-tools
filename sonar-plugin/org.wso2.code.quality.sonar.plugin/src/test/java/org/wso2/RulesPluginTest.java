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

import org.junit.jupiter.api.Test;

import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

class RulesPluginTest {

  @Test
  void testName() {

    Plugin.Context context = new Plugin.Context(new MockedSonarRuntime());

    new RulesPlugin().define(context);

    assertThat(context.getExtensions()).hasSize(2);
  }
  private static class MockedSonarRuntime implements SonarRuntime {

    @Override
    public Version getApiVersion() {

      return Version.create(7, 9);
    }

    @Override
    public SonarProduct getProduct() {

      return SonarProduct.SONARQUBE;
    }

    @Override
    public SonarQubeSide getSonarQubeSide() {

      return SonarQubeSide.SCANNER;
    }

    @Override
    public SonarEdition getEdition() {

      return SonarEdition.COMMUNITY;
    }
  }

}
