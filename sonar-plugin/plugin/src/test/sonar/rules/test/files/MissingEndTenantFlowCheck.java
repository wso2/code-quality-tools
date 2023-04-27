/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/sonar/rules/test/files in order to not be part of the maven compilation.
 **/
class myClass {

    int num;

    void startTenantFlow() {
        // Do nothing.
    }

    void endTenantFlow() {
        // Do nothing.
    }

    void foo() {
        // Do nothing.
    }

    void mainMethod() {

        foo();
        endTenantFlow();

        try // Noncompliant
        {
            startTenantFlow();
            int i;


        } catch (Exception e) {

        } finally {
        }

        try {
            String myString;
            int myNum;
            startTenantFlow();

        } catch (Exception e) {

        } finally {
            endTenantFlow(); // Compliant
        }

        try // Noncompliant
        {
            String myString;
            int myNum;
            startTenantFlow();

        } catch (Exception e) {

        } finally {
            int i;
        }

    }
}
