package threads;

public class DaemonThread extends Thread{
    @Override
    public void run() {
        System.out.println("This is a daemon thread");
    }
}
