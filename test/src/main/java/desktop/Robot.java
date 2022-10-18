package desktop;

public class Robot {
    int id;
    public Robot(int i){
        id = i;
        Brain b = new Brain();
        b.think();
    }

    // Class Robot has an inner class Brain. 
    // The inner class can access all of the member variables and methods of it's outer class, 
    // but it cannot be accessed from any outside class.
    private class Brain{
        public void think(){
            System.out.println(id + " is thinking.");
        }
    }
}
