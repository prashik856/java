package threads;

public class RunThreads {
    public void learnThreads() {
        System.out.println("Learning Threads");

        // Threads
        // java is multi-threaded programming language.
        // We can subdivide specific operations within a single application into individual threads
        // that all run in parallel.

        // Two ways to create a thread.
        /**
         * Extent the Thread class: Inherit the Thread class, override it's run() method,
         and write the functionality of the thread in the run() method.
         * Then we can create a new object of our class and call it's start method to run the thread.
         */
        System.out.println("Using Loader");
        Loader loader = new Loader();
        loader.start();
        //Here, Loader class extends Thread class and overrides its run() method.
        // When we create the loader object and call its start() method,
        // the run() method statements execute on a different thread.


        // Running thread using Runnable
        // Implementing the Runnable interface, implement the run() method.
        // Then create a new Thread object, and pass the Runnable class to it's constructor,
        // and start the thread by calling the start() method.
        System.out.println("Using Runnable");
        Thread t = new Thread(new LoaderRunnalbe());
        t.start();
        // Thread.sleep() method pauses a thread for specified period of time.
        // Thread.sleep(1000) -> 1 sec pause.
        // Thread.sleep() Throws an InterruptedException, so we need to take care of it as well.
        // Implementing using runnable is preferred because it enables you to extend from another class.

        //Types of Exceptions
        /**
         * Checked - checked during compilation
         * Unchecked (runtime) checked during runtime
         */

        System.out.println("");
    }
}
