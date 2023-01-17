import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.ApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.LocalApplicationAuthenticator;
import org.wso2.carbon.user.api.UserStoreManager;

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




