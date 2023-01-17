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

import org.sonar.plugins.java.api.JavaCheck;
import org.wso2.custom.check.authenticator.ClassLevelVariablesInServicesCheck;
import org.wso2.custom.check.licenseheader.IncInCommentsCheck;
import org.wso2.custom.check.tenantflow.MissingEndTenantFlowCheck;
import org.wso2.custom.check.tenantflow.TenantFlowCheck;
import org.wso2.custom.check.variable.VariableLengthCheck;
import org.wso2.custom.check.variable.classname.ClassNameCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that returns the collection of all the custom checks.
 */
public final class RulesList {

    private RulesList() {

        // Preventing objects to be created from this class.
    }

    public static List<Class<? extends JavaCheck>> getChecks() {

        List<Class<? extends JavaCheck>> checks = new ArrayList<>(getJavaChecks());
        return Collections.unmodifiableList(checks);
    }

    /**
     * These rules are going to target MAIN code only.
     */
    public static List<Class<? extends JavaCheck>> getJavaChecks() {

        return Collections.unmodifiableList(Arrays.asList(
                IncInCommentsCheck.class,
                VariableLengthCheck.class,
                ClassNameCheck.class,
                TenantFlowCheck.class,
                MissingEndTenantFlowCheck.class,
                ClassLevelVariablesInServicesCheck.class));
    }

    /**
     * These rules are going to target TEST code only.
     */
    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {

        return Collections.emptyList();
    }
}
