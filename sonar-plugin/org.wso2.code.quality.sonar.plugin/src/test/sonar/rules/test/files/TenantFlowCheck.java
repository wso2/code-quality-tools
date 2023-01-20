/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/sonar/rules/test/files in order to not be part of the maven compilation.
 **/
class myClass {

    int num;

    void startTenantFlow(){
        // Do nothing.
    }

    void endTenantFlow(){
        // Do nothing.
    }

    void mainMethod() {

        startTenantFlow(); // Noncompliant
        endTenantFlow(); // Noncompliant

        try {
            startTenantFlow();
            int i;


        } catch(Exception e) {

        } finally {
            endTenantFlow();
        }

        try {
            endTenantFlow(); // Noncompliant

        } catch(Exception e) {
            startTenantFlow(); // Noncompliant
            endTenantFlow(); // Noncompliant

        } finally {
            endTenantFlow();
        }

    }

    void anotherMethod() {

        try {
            startTenantFlow();
            int i;

        } catch(Exception e) {
            startTenantFlow(); // Noncompliant

        } finally {
            endTenantFlow();
        }

        try {
            String myString;
            int myNum;
            startTenantFlow(); // Noncompliant

        } catch(Exception e) {

        } finally {
            endTenantFlow();
        }

    }
}
