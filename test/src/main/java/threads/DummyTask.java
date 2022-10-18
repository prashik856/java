package threads;

// We can do multiple inheritance using extends and implements
public class DummyTask extends Task implements Runnable{
    @Override
    public void run() {
        Printer printer = new Printer();
        printer.executeTaskPrinter2();
    }
}
