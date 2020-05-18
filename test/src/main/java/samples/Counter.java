package samples;

public class Counter{
    public static int COUNT=0;
    //Count variable will be shared by all the objects of the class.
    public Counter(){
        COUNT++;
    }
}