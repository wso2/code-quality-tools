/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/sonar/rules/test/files in order to not be part of the maven compilation.
 **/
class student { } // Compliant

class myClass { } // Noncompliant

class testclass extends myclass{ } // Noncompliant

enum sampleenum{ } // Noncompliant

enum SampleEnum{ } // Noncompliant

enum Season{ } // Compliant
