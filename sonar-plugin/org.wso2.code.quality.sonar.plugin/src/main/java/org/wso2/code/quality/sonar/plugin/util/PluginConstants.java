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

package org.wso2.code.quality.sonar.plugin.util;

/**
 * Constants used in WSO2 Code Specific plugin.
 */
public abstract class PluginConstants {

    private PluginConstants() {

    }

    public static final String JAVA = "java";
    public static final String RESOURCE_PATH = "org/wso2/code/quality/sonar/plugin/rules/descriptions";
    public static final String WSO2_REPOSITORY_KEY = "wso2-llc";
    public static final String WSO2_REPOSITORY_NAME = "WSO2 custom rules";
    public static final String START_TENANT_FLOW = "starttenantflow";
    public static final String END_TENANT_FLOW = "endtenantflow";
    public static final String FULLY_QUALIFIED_NAME_APPLICATION_AUTHENTICATOR = "org.wso2.carbon.identity" +
            ".application.authentication.framework.ApplicationAuthenticator";
    public static final String FULLY_QUALIFIED_NAME_USER_STORE_MANAGER = "org.wso2.carbon.user.api.UserStoreManager";
}
