package kingdom;

public abstract class Insect {
    /**
     * Every insect makes a sound, and we have different kinds of insects, that's why we have defined this class as abstract.
     * Each subclass insect will have to implement the makeSound() method in their implementation to make this code work.
     * This is used when there is no meaningful definition for the method in the superclass.
     *
     */
    int legs=0;
    public abstract void makeSound();
}
