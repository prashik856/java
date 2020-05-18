package samples;

public class Dog extends  Animal{
    public Dog(){
        System.out.println("Constructor of Dogs");
        legs=4;
    }

    public void makeSound(){
        System.out.println("Woof Woof!");
    }
}
