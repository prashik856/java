package threads;

public class PrinterThread extends Thread{
    @Override
    public void run() {
        Printer printer = new Printer();
        printer.executeTask();
    }
}
