/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/sonar/rules/test/files in order to not be part of the maven compilation.
 **/
// foo

// Noncompliant@+1
// Noncompliant@+1 {{Remove usage of this "WSO2 Inc." comment. Replace it with LLC.}}
// wso2 inc.

// Noncompliant@+1
// WSO2 Inc.

// Noncompliant@+1
// [WSO2 Inc.]

// WSO2 LLC
// wso2 llc
// WSo2 LLC
