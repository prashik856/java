package threads;

public class YoutubeThreads {
    public void learnThreads() throws InterruptedException {
        /*
         * What is a java thread?
         * This is a lightweight sub process
         * It is a smallest independent unit of a program
         * Contains a separate path of execution
         * Every java program contains at least one thread
         * a thread is created and controlled by the java.lang.Thread package
         *
         * Thread Lifecycle
         * A java thread can lie only in one of the shown states at any point of time
         * New -> Runnable -> Running -> Terminated
         * Waiting state can flip between Runnable and Running.
         *
         * New
         * A new thread begins its life cycle in this state and remains here until the program
         * starts the thread. It is also known as a born thread.
         *
         * Runnable
         * Once a newly born thread starts, the thread comes under runnable state. A thread stays in this tate is until
         * it is executing it's task. (Start music app)
         *
         * Running
         * In this state, a thread starts executing by entering run() mehtod and the yeild() method can send them
         * to go back to Runnable state (Start playing music)
         *
         * Waiting
         * A threads enters this state when it is temporarily in an inactive state i.e. it is till alive but it is
         * not eligible to run. It can be in waiting, sleeping or blocked state. (Pause)
         *
         * Terminated
         * A runnable thread enters the terminated state when it completes its task or otherwise terminates. (terminate
         * music app)
         *
         * Another example is downloading a video.
         *
         * */

        /*
         * Creating a thread
         * Why do we want to create a thread?
         *
         * Main thread? -> The thread used by main method.
         * What if we want an instruction where we want to run a long running job?
         * When this happens, the instructions after running the above job will block all other
         * instructions. This will push a lot of load on the main thread that we should avoid.
         * In these cases, we should create a new thread.
         *
         * A thread can be created by two ways:
         *
         * Thread Class
         * Create a Thread class
         * Overrride run() method
         * Create object of the class
         * Create objject of the class
         * Invoke start() method
         *
         * Runnable Interface
         * Create a thread class implementing Runnable interface
         * Override run() method
         * Create object of the class
         * Invoke start() method using the object
         * */

        // Using thread class
        Loader loader = new Loader();
        loader.start();

        // Using Runnable Interface
        Thread t = new Thread(new LoaderRunnalbe());
        t.start();

        // Every instruction in a single thread runs sequentially
        // Threads always execute code in a sequence

        // Job 1
        System.out.println("Job 1");
        System.out.println("===Application started===");

        Printer printer = new Printer();
        // Job 2
        System.out.println("Job 2");
        printer.executeTask();

        // Usecase where we need a thread.
        // Till Job 2 is not finished, below written jobs are wairint and are not executed
        // In case Job 2 is a long running operation, i.e. several documents are suppose to be printed.
        // In such a use case, OS/JVM shall give a message that app is not responding
        // Some sluggish behavior in app can be observed -> App seems to hand

        // Rather than having a normal class, let's create printer a thread.
        // Job 3
        System.out.println("Job 3");
        printer.executeTaskPrinter2();

        // Job 4 using Threads
        System.out.println("Job 4");
        PrinterThread printerThread = new PrinterThread();
        printerThread.start(); // This shall internally executed run method
        // Now, main and printerThread are being run parallely or concurrently.
        // This printerthread can be called as worker thread or a child thread.
        // This will not affect performance of main thread
        // printerthread is running separately and main is running separately

        // Multiple interhitance is not supported in Java
        // This will print from printer 2
        System.out.println("Job 5");
        Runnable runnable = new DummyTask();
        Thread task = new Thread(runnable);
        task.start();

        // Ends
        System.out.println("Job 6");
        System.out.println("===Application Closed ====");

        /*
        * Differentiate between thread class and runnable interface
        *
        * Thread class
        * A class extending Thread class can't exteend any other class
        * thread class is extended only if there is a need of overriding other methods of it
        * Enables tight coupling
        *
        * Runnable Interface
        * Along with Runnable a class can implement any other interface
        * Runnable is implemented only if there is a need of special run method
        * Enables loose coupling
        *
        * Common:
        * Each Thread creates its unique object
        * More Memory consumption
        * */

        /*
        * Java Main thread
        * Main thread is the most important thread of a java program
        * it is executed whenever a java program starts
        * every program must contain this thread for its execution to take place
        * java main thread is needed because of the following reasons
        * -> From this other child threads are spawned
        * -> It must be the last thread to finish execution i.e. when the main thread stops program terminates.
        * */

        /*
        *
        * Any thread can be marked as daemon thread.
        * This is the thread which will run alongside the main thread
        *
        * */
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();


        /*
        * Multi threading in Java
        * more than 1 threads in our program.
        *
        * Multi-Threading
        * Multi threading is the ability of a program to run two or more threads concurrently, where each thread can
        * handle a different task at the same time making optimal use of the available resources.
        * */
        new SyncApp().startApp();

        /*
         * Where is sync needed?
         * We need this when multiple threads are working on the same object.
         *
         * */

        /*
         * Thread Pool
         * Java thread pool manages the pool of worker threads and conatains a queue that keep the tasks waiting
         * to get executed.
         *
         *
         * */

        /*
        * Thread Methods
        *
        * Creating Multipel Threads
        * Joining Threads
        * Thread.sleep()
        * Inter Thread Communication
        * Daemon Thread - Going to get executed along with main thread
        * */
    }
}
