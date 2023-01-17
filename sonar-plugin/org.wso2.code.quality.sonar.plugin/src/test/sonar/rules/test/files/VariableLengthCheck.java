class myClass {

    int a; // Noncompliant
    boolean b; // Noncompliant
    char c; // Noncompliant

    float aa; // Compliant
    int bb; // Compliant
    boolean cc; // Compliant

    void foo() {
        for (int i = 0; i < 10; i++) {
            // Do something.
        }
    }

    void foo() {
        int s = 3; // Noncompliant
        int sb;

        for (int i = 0; i < 10; i++) {
            int ab;
            int a; // Noncompliant

            for (int z = 0; z < 10; z++) {
                // Do something.
                int zz;
                int z; // Noncompliant
            }
        }
    }

    void foo() {
        try {

        } catch (Exception e) {

        } finally {

        }
    }

}