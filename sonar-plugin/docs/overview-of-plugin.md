# Introduction
Welcome to the documentation for the custom Sonar Java plugin for WSO2! This plugin is designed to offload the PR review process and assist in detecting common violations of WSO2’s specific coding standards.

In WSO2, we have a set of coding best practices and security best practices which are not common in Java. So using default or basic static analysis tools such as find bugs and check style will not be able to analyze and identify these types of bugs.

So, after completing a thorough comparison between the widely used static analysis tools in the industry, we determined that Sonar is the most ideal static analysis tool to meet our needs.

With this plugin, we will be able to write custom rules that can be used to detect common WSO2 specific violations and security guidelines. Developers can easily integrate Sonar into their workflow and ensure that their code adheres to WSO2’s coding standards before a code review session. I hope that this plugin will help to streamline the code review process and improve the overall quality of our codebase.

# How does the plugin work
[Sonar Documentation](https://docs.sonarqube.org/latest/extend/developing-plugin/)

Entry point of plugin given in pom in the sonar packaging plugin as <pluginClass>org.wso2.RulesPlugin</pluginClass>

**RulesPlugin** - entry point of plugin

RulesPlugin implements the plugin interface. What is meant by entry point - for plugins to inject extensions into sonarqube we need to use this plugin interface

**RulesDefinitionImpl Class**

So the custom rules are added as extensions using this RulesDefinitionImpl class (server extension) which implements a RulesDefinition interface from the sonar api that is instantiated at server startup.

**Define Method**
The define method in this class creates a repository in which our custom rules will be added.

Then the ruleMetaDataLaoder has addRulesByAnnotatedClass method which maps the particular custom rule to the description based on the rule key.

**RulesDefintionImplTest Class**
The RulesDefintionImplTest runs unit tests (this file is not mandatory). It verifies the repository name and whether the size of the repository rules matches that of the RulesList.getChecks().size() and whether all our custom classes have been given a description.

**RulesList Class**
The RulesList class is where we add the names of the custom checks we want to be included in our repository.

***

Back to RulesPlugin, the next extension is where objects are instantiated but this time using the **FileCheckRegistrar** class where the custom rules classes are matched and registered to the repository.

This provides the checks (classes that we have written that will be executed during code analysis). It implements the **CheckRegistrar** interface

The register method registers java checks for a given repository

The objects are instantiated during code analysis and the methods in CheckRegistrar are called to get the classes that will be used to instantiate checks.

Then the FileCheckRegistrarTest uses the sonar java api CheckRegistrar to see if the size of the classes added matches the amount of custom classes we want to add. (This test class is not mandatory)


