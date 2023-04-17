package competitive;

public class MyAlgorithm {
    public static void main(String[] args) {
        String someAlgorithmResult = SomeAlgorithm.solve("Prashik");
        System.out.println("Some Algo Result: " + someAlgorithmResult);

        int someOtherAlgorithmResult = SomeOtherAlgorithm.solve(200, 145);
        System.out.println("Some Other Algo Result: " + someOtherAlgorithmResult);

        System.out.println("Testing Split String");
        MyTestingClass.testSplit();
    }
}
