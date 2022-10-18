package lambda;

public class Lambda {
    public void testLambda() {
        System.out.println("Learning Lambdas");

        // Lambda
        SomeLambda someLambda = (stringValue) -> {
            System.out.println("Hello " + stringValue);
        };

        someLambda.printHello("Prashik");

        System.out.println("");
    }
}
