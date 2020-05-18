package samples;

public class Vehicle{
    //Defining attributes

    // Best practice is to set them to private.
    // Then they can se set using getters and setters.
    private int maxSpeed;
    private int wheels;
    private String color;
    private double fuelcapacity;

    //Constructors
    /**
     * Invoked when the object is created and are used to initialize them.
     * Cam be used to provide initail values for object attributes
     * Constructor name must be same as it's class name.
     * No return type.
     */

    public Vehicle(){
        this.setColor("red");
    }

    public Vehicle(String color){
        this.setColor(color);
    }

    // Now, here the method is defined as static.
    // so we can call this method without actually creating an instacne of this class.
    // Like this. samples.Vehicle.horn();
    public static void horn(){
        System.out.println("Beep! Beep!");
    }

    //Getters
    public int getMaxSpeed(){
        return maxSpeed;
    }

    public int getWheels(){
        return wheels;
    }

    public  String getColor(){
        return color;
    }

    public double getFuelcapacity(){
        return fuelcapacity;
    }

    //Setters
    public void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public void setWheels(int wheels){
        this.wheels = wheels;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setFuelcapacity(double fuelcapacity){
        this.fuelcapacity = fuelcapacity;
    }
}