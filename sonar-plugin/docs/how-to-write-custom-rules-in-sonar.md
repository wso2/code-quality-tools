# Introduction
Welcome to the documentation for writing custom rules in Sonar for WSO2! At WSO2, we are committed to maintaining high code quality standards and continuously improving our development processes. One way we can do this is by using the SonarQube platform to analyze our codebase for potential issues. However, we may have specific coding guidelines or standards that are unique to our company and not covered by the standard checks and plugins. That's where custom rules come in. With custom rules, we can define our own rules to ensure that our code adheres to our specific standards. In this documentation, you will learn how to write custom rules for Sonar and how to integrate them into our workflow at WSO2. By following these guidelines, you can help us maintain high code quality and contribute to the continuous improvement of our development processes.

# Files Involved
There are three files involved when writing custom rules for Sonar:
1. Test file
1. Test class
1. Rule class

## Test file - write up some test cases
In SonarQube, test files are used to verify that the rules you have implemented in your custom plugin are working correctly. When you write test cases for your custom rules, you should aim to cover as many scenarios as possible to ensure that the rule is working correctly and providing accurate results.

In a test file for a custom rule in SonarQube, the "Compliant" and "Noncompliant" keywords are used to specify the expected result of the rule for a given code snippet. The "Compliant" keyword indicates that the rule should not produce any issues for the given code snippet, while the "Noncompliant" keyword indicates that the rule should produce at least one issue.

Here is an example of how these keywords might be used in a test case which reports an issue if variable name length is less than 2 characters:


## Test class - rule’s unit test
Unit tests are used to verify the behavior of a custom rule being implemented. The CheckVerifier class, provided by the Java Analyzer rule-testing API, is used to validate the rule implementation and abstract initialization mechanisms of the analyzer. When a rule is verified, the verifier collects lines marked as noncompliant and checks that the rule raises the expected issues and only those issues.
The following example shows how to write a test that points to your previous file with the test cases:

```
@Test
void test() {

    CheckVerifier.newVerifier()
           .onFile("src/test/files/MyFirstCustomCheck.java")
           .withCheck(new MyFirstCustomCheck())
           .verifyIssues();
}
```

## Rule class
The rule implementation class is the main file that contains the code for your custom rule in SonarQube for Java. It defines the logic that the rule will use to analyze the code and identify any issues.

Before writing the custom rule, you need to understand some background information. The SonarQube Java Analyzer parses a Java code file and creates a data structure called the Syntax Tree, which represents each element of the Java language. Each element, called a Kind, has a specific interface that describes its particularities. For example, a method declaration is represented by the METHOD Kind and the MethodTree interface. The [Kind enum in the Java Analyzer API](https://github.com/SonarSource/sonar-java/blob/6.13.0.25138/java-frontend/src/main/java/org/sonar/plugins/java/api/tree/Tree.java#L47) lists all of the available Kinds.

There are two ways in which you can write custom rules, one such way is the IssuableSubscriptionVisitor class from the API provides methods for raising issues and defines the strategy for analyzing a file. It uses a subscription mechanism to specify which types of tree nodes the rule should react to. The nodesToVisit() method is used to specify the node types that the rule should cover.

The other way is to use the semantic API. The SonarAnalyzer for Java also constructs a semantic model of the code being analyzed, which provides more information about the code. This model includes data about symbols used in the code, such as a method's owner, its usages, parameter and return types, and any exceptions it may throw. You can explore the [semantic package of the API](https://github.com/SonarSource/sonar-java/tree/6.13.0.25138/java-frontend/src/main/java/org/sonar/plugins/java/api/semantic) to see the full range of information that is available during analysis.

# 3 Tutorial
Before jumping into writing a WSO2 specific custom rule, make sure to try out the [example tutorial](https://github.com/SonarSource/sonar-java/blob/master/docs/CUSTOM_RULES_101.md) given in the sonar documentation to get familiar with the jargon and get some hands-on experience. Afterwards, go through the below section to write a more WSO2 specific check.

# 4 Let's write a WSO2 Specific Check

## The Problem
According to our [PR review guidelines](https://docs.google.com/document/d/1WZDZrpNuSVDMoFf0XOAQmtNk59T9R7aDPGY_U0elEq0/edit?usp=sharing), we can not use class level variables in certain services. For example, if any class is a subtype of the WSO2 application authenticator or user store manager then that particular class should not be able to define class level variables. The variables defined should always be static and final.

So let’s see how we can use sonar to write this very custom check to adhere to WSO2 coding standards and best practices.

## Approach
Let’s first clone the sonar repo which has a custom template for us already to work with in their ‘docs/java-custom-rules-example’ folder - https://github.com/SonarSource/sonar-java.git.

Then let's add two artifact items to our pom.xml(in the custom sonar template its called pom_SQ_8_9_LTS.xml):

```
<artifactItem>
    <groupId>org.wso2.carbon.identity.framework</groupId>
    <artifactId>org.wso2.carbon.identity.application.authentication.framework</artifactId>
    <version>5.25.52</version>
    <type>jar</type>
</artifactItem>
<artifactItem>
    <groupId>org.wso2.carbon</groupId>
    <artifactId>org.wso2.carbon.user.api</artifactId>
    <version>4.9.0-m3</version>
    <type>jar</type>
</artifactItem>
```

And run: `mvn clean install` or `mvn clean install -f pom_SQ_8_9_LTS.xml` if you are using the template’s pom.
We do this so that, when writing our test cases, the import will get detected by our test file.

***

Then as mentioned above, we will have to create 3 files:

**Test file**
Let’s create this in the ‘src/test/files' folder and call it ClassLevelVariablesInServicesCheck.java

In this file, we will be adding test cases that will be covering most of the possible use cases:

```
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.ApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.LocalApplicationAuthenticator;
import org.wso2.carbon.user.api.UserStoreManager;

class Registry implements UserStoreManager {

    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
    
    public boolean authenticate(String s, Object o) throws UserStoreException {
        return false;
   }
}

class BasicAuthenticator extends AbstractApplicationAuthenticator {

    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}

class myClass {

    private int number;
    private static final long serialVersionUID = 123;
}

```
As you can see in the above code, we are marking variables that are not static and final as noncompliant if they are part of a class that contains the ApplicationAuthenticator or UserStoreManager in its inheritance chain.

***

**Test Class**
Next, we shall write the rule’s unit test to verify the behavior of our custom rule and ensure that our rule is functioning correctly.

Let’s create a class called `ClassLevelVariablesInServicesCheckTest` in the ‘src/test/java/org/sonar/samples/java/checks/’ and add our unit test to it like below(you may have to import class `org.sonar.java.checks.verifier.CheckVerifier`):

```
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

```

***
**Rule Class**
This example shows a custom rule implementation class that extends the BaseTreeVisitor class and overrides the visitMethod(), visitVariable() and visitClass() methods. These method overrides specifies that the rule should visit class, variable and method nodes in the syntax tree and defines the custom logic for analyzing each class.

In this case, the rule is checking whether the class is a subtype of either an Application Authenticator or User Store Manager and raising an issue if it does not contain the static or finally keywords. It also makes sure that it is not raising an issue for variable declarations inside methods.

```
/**
* Custom check to see if class level variables exist in specific services.
*/
@Rule(key = "ClassLevelVariablesInServicesCheck")
public class ClassLevelVariablesInServicesCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private boolean subTypeOfService;
    private boolean inMethod;
    private final List<String> serviceClassesToCheckList = new ArrayList<>(getServicesToCheck());
    private static final String MESSAGE = "Do not use class level variables in Authenticators.";

    @Override
    public void scanFile(JavaFileScannerContext context) {

        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {

        subTypeOfService = false;
        inMethod = false;

        for (String serviceName : serviceClassesToCheckList) {
            if (tree.symbol().type().isSubtypeOf(serviceName)) {
                subTypeOfService = true;
            }
        }
        super.visitClass(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {

        if (subTypeOfService && (!inMethod) && (!tree.symbol().isStatic() ||
                !tree.symbol().isFinal())) {
            context.reportIssue(this, tree, MESSAGE);
        }

        super.visitVariable(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {

        inMethod = true;
        super.visitMethod(tree);
    }

private List<String> getServicesToCheck() {

    return Collections.unmodifiableList(Arrays.asList(
            "org.wso2.carbon.identity.application.authentication.framework.ApplicationAuthenticator",
            "org.wso2.carbon.user.api.UserStoreManager"));
    }

}

```

# Summary
Custom rules in SonarQube for Java are designed to help developers avoid common mistakes. It can even be written to target WSO2’s specific needs such as declaring class level variables in certain services. By using custom rules and sonar, we can improve the quality and maintainability of our code and ensure that our specific code standards are maintained at all times. 



