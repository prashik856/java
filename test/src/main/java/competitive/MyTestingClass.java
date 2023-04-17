package competitive;

public class MyTestingClass {
    MyTestingClass(){}

    public static void testSplit() {
        String s = "Hello.My.Name.Is.Prashik";
        String[] splitS = s.split("\\.");
        for(int i=0; i<splitS.length; i++) {
            System.out.println(splitS[i]);
        }
    }
}
