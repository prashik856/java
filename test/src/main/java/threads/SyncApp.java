package threads;

class MyThread extends Thread {
    MyThread() {}
    @Override
    public void run() {
        new SyncApp().printDocuments("AmyJackson.pdf", 5);
    }
}

class YourThread extends  Thread {
    YourThread(){}
    @Override
    public void run() {
        new SyncApp().printDocuments("LisaAnn.pdf", 8);
    }
}

public class SyncApp {
    public SyncApp() {}
    public void startApp() throws InterruptedException {
        System.out.println("Application Started");

        // Exception in thread main
        // main method represents the main thread
        //int i = 10/0;

        // We have multiple threads working
        // This is synchronication
        // Mulitple threads are going to work on the same object, we are going to synchronize them
        // Since we are providing different objects to thread, we are able to print all objects sequentially
        // If we use the same objects, the printing will be jumbled up because the call is asynchronous
        printDocuments("PrashikRaut.pdf", 10);

        MyThread myThread = new MyThread();
        myThread.start();
        // Synchronizing coming into action
//        myThread.join();

        YourThread yourThread = new YourThread();
        yourThread.start();
        // Synchronizing coming into action
//        yourThread.join();

        // We can always add a synchronized keyword to that method

        System.out.println("Application ended");
    }

    /*
    * Adding synchronized to our method adds a lock to this particular object
    * So, when multiple threads are going to access the same method, they will have to wait before the
    * object is available to them.
    *
    * What is the benefit?
    * We don't have to add join() method because of this.
    * */
    synchronized public void printDocuments(String docName, int num) {
        for (int i = 0; i < num; i++) {
            System.out.println("Print Document " + docName + " " + i);
        }
    }

    // We can also add a synchronized block to lock a certain object
//    synchronized (printer) {
//        printer.executeTask();
//    }

}
