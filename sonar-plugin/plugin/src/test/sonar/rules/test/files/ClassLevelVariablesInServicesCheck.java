/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/sonar/rules/test/files in order to not be part of the maven compilation.
 **/
interface SecondInterface extends MyInterface {

    int number;
    static final long serialVersionUID;
}

@WSO2SpecificService
class WSO2SpecificServiceClass {

    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}

class TestClass implements MyInterface {
    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}

class TestClass2 extends TestClass {
    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}

class TestClass3 extends TestClass2 {
    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}

@WSO2SpecificService
interface MyInterface {

    int number;
    static final long serialVersionUID;
}

public class SecondClass implements MyInterface {

    private int number; // Noncompliant
    private static final long serialVersionUID = 123;

    @WSO2SpecificService
    boolean myMethod() {
        return true;
    }
}

@interface WSO2SpecificService{ }

class Registry extends SecondClass {
    private int number; // Noncompliant
    private static final long serialVersionUID = 123;
}
