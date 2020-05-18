package samples;

public class Animal{
    //Protected access modifier, which makes the members visible only to subclasses.
    protected int legs;
    public Animal(){
        System.out.println("Constructor of Animal");
        legs=3;
    }

    public void eat(){
        System.out.println("Eat animal eat!");
    }

    public void bark(){
        System.out.println("Woof-Woof");
    }

    public void makeSound(){
        System.out.println("Animalistic sound.!");
    }
}
