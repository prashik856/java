package threads;

public class Printer {
    public void executeTask() {
        for(int i=0; i<10; i++) {
            System.out.println("Printing document " + i + " from Printer 1");
        }
    }

    public void executeTaskPrinter2() {
        for(int i=0; i<10; i++) {
            System.out.println("Printing document " + i + " from Printer 2");
        }
    }
}
