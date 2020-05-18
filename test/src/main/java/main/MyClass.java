package main;

import samples.Animal;
import samples.Counter;
import samples.Dog;
import samples.Vehicle;
import samples.Cat;
import desktop.Machine;
import desktop.Robot;
import kingdom.Name;

import samples.Rank;

import threads.Loader;
import threads.LoaderRunnalbe;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

class MyClass {
    //defining a method
    static void sayHello(){
        System.out.println("Say hello man code");
    }

    //Method parameters
    static void sayHello(String name){
        System.out.println("Hello " + name);
    }

    //Method return types
    // return keyword
    static int sum(int a, int b){
        return a + b;
    }

    static double max(double a, double b){
        if(a>b){
            return a;
        }
        else{
            return b;
        }
    }

    static int max(int a, int b){
        if(a>b){
            return a;
        }
        else{
            return b;
        }
    }

    static int div(int a, int b) throws ArithmeticException{
        if(b==0){
            throw new ArithmeticException("Divisoin by Zero");
        }
        else{
            return a/b;
        }
    }

    //Entry point
    //static: method can be run without creating an instance of the class containing the main method.
    // void test() -> Doesn't return anything.
    // Input parameters is the array of string called args
    public static void main(String[ ] args){
        //System is a class here.
        System.out.println("Hello World.");
        System.out.println("Revision of java yo");
        /*
        This is a multiline comment yo
        or Block comment
        */

        /**
         * This is a documnetation comment.
         */

         //Variables
         /*
            short
            int
            long
            float
            double
            doolean
            char
            String
         */
        String name = "Prashik";
        int age = 5;
        double overs = 3.5;
        char group = 'A';
        boolean isTrue = true;
        int a=1, b=2, c=3;


        //Operators
        // Value used in either side of an operator is called an operand
        int x = 6 + 3;
        // 6 and 4 are the operands of the + operator

        /**
         + addition
         - sub
         * mul
         / divide
         % modulo
         */
        // If int is used as datatype, whole number will be received in division
        // Else, we need to use double, to retrieve the value with decimal point.

        /**
         Increment or decrement operators
         x++, ++x
         x--, --x
         */

        /**
         * Prefix and Postfix
         * Prefix (first incremented, and then used)
         * Postfix (First used, and then incremented)
         */

        /**
         * Assignment operators
         * Addition and assignment
         */
        int  num1 = 4, num2 = 5;
        num2 += num1; //num2 = num2 + num1


        //Strings
        String s = "Heelo";

        //String Concatenation
        // + operator between strings add them together to make a new string.
        String firstName, lastName;
        firstName = "Prashik";
        lastName = "Raut";
        System.out.println("My name is " + firstName + " " + lastName);

        //Getting userinput
        // We use the Scanner object to do this
        //Scanner myvar = new Scanner(System.in);
        //System.out.println(myvar.nextLine());
        /**
         * We can use different methods here
         * Read a byte - nextByte()
         * Read a short - nextShort()
         * Read an int - nextInt()
         * Read a long - nextLong()
         * Read a fload - nextFloat()
         * Read a double - nextDouble()
         * Read a boolean - nextBoolean()
         * Read a complete line - nextLine()
         * Read a word - next()
         */

         //Decision Making.
         /**
          * Comparative operators
          <
          >
          !=
          ==
          <=
          >=
          */
        x=7;
        if(x==7){
            System.out.println("Value of x is seven");
        }

        //If and nested ifs
        if(age ==15){
            if(age>16){
                System.out.println("haha not possible");
            } else{
                System.out.println("This is definitely print");
            }
            System.out.println("Value of age is 15");
        } else{
            System.out.println("Value of age is not 15");
        }

        //Else if to check multiple conditions
        if(x>0){
            System.out.println("Bleh bleh bleh");
        } else if(age<100){
            System.out.println("Age is less than 100");
        } else if(age>50){
            System.out.println("Jaane de");
        }

        //AND OPERATOR &&
        //OR  ||
        //NOT !
        // !(age > 100)

        //SWITCH STATEMENT
        int day=7;
        switch(day){
            case 1:
                System.out.println("Monday");
                break;

            case 2:
                System.out.println("Tuesday");
                break;
            
            default:
                System.out.println("Default Statement");
            //Since default is last statement, no need for break
        }


        //while loops
        x=10;
        while(x>0){
            System.out.println(x);
            x--;
        }

        //For loops
        for(x=0; x<7; x++){
            System.out.println(x);
        }

        //do while
        x=1;
        do{
            System.out.println(x);
            x++;
        } while(x<5);

        //Loop control Statements
        x=1;
        while(x>10){
            System.out.println(x);
            if(x==5){
                System.out.println("Enough");
                break;
            }

            if(x==9){
                System.out.println("Do nothing. Move forward.");
                continue;
            }
        }

        //Arrays
        int[ ] arr;

        //To define capacity
        int[ ] arr1 = new int[10];

        //To reference the variable
        arr1[0] = 15;

        //Initializing arrays
        String[] arr3 = {"A", "B", "C", "D"};
        System.out.println(arr3[3]);
        int[ ] arr4 = {1,3,4,5};

        //Array length
        System.out.println(arr4.length);

        //Enhanced For loops
        int[ ] primes = {1,2,3,4,5};
        for(int t: primes){
            System.out.println(t);
        }
        //Going through all the elements of the array

        //Multidimentional
        int[][] arr5 = {{1,2,3}, {1,2,3}};
        System.out.println(arr5[0][0]);

        int[][] arr6 = new int[10][10];


        //OOPS
        // Each object has three dimensions: identity, attributes and behavior.

        //Class
        // class describes what the object will be (blueprints).
        // Each class has a name and each is used to define attributes and behavior.
        // e.g. attributes: name, height, weight, gender, age
        // behavior: walk, run, jump, speak, sleep

        //Methods
        // Methods define behavior.
        // System.out.println() is a method.

        //calling that method
        sayHello();
        sayHello();
        sayHello();

        sayHello("Prashik Raut");
        sayHello("Amy Santiago");

        x = sum(2,6);
        System.out.println(x);

        //Creating instances
        Animal dog = new Animal();
        dog.bark();

        //Creating new instances
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle();
        Vehicle v3 = new Vehicle("blue");
        v1.setColor("red");
        v2.horn();

        //Access Modifiers
        // public is an access modifier. It is used to set level of access.
        // public - The class is accessible by any other class
        // default - The class is accessible only by classes in the same package.

        //Choices for attributes and methods
        // default - a variable or method declared with no access control modifier is available to any other class
        // in the same package.
        // public - accessible to any other class
        // protected - Provides the same access as the default access modifier with the addition that subclasses
        // can access protected methods and variables of the superclass.
        // private - Accessible only within the declared class itself.

        //Getters and setters are the basic foundations of encapsulation.

        // Passing by value and passing by reference
        // For reference, we can use getter and setters.


        //The Math Class
        //Math.abs(-1) - returns abs value
        //Math.ceil(0.4) - returns round up ceil value
        //Math.floor(23.5) - returns round up floor value
        //Math.max(4,5) - returns max value
        //Math.min(1,5) - returns min value
        //Math.pow(a,b) - return a^b
        //Math.sqrt() - returns square root
        //Math.sin() - sine of number
        //Math.cos() - returns cosine


        //Static
        // When you declare a variable or a method as static, it belongs to the class, rather than to a specific instance.
        // That means only one instance of static member exists.

        Counter c1 = new Counter();
        Counter c2 = new Counter();
        //COUNT is accessible because the variable is defined as public variable.
        System.out.println(Counter.COUNT);
        //Here the output will be 2, because the COUNT variable is static, and it will be incremented everytime the
        // Constructor is called. Guess this is different then.
        //Common practice to use upper case when naming a static variable.

        //Same concept applies to static methods
        Vehicle.horn();

        // Example of static class are those of math class
        // Math.abs(), here, we were able to call abs() method without actaully creating the instance of Math class.

        // the main method must always be static.

        //Final keyword
        // We use this keyword to mark a variable constant, so that it can be assigned only once.

        final double PI = 3.14;
        //Now value of PI will be constant, and any attempt to assign it a value will cause an error.
        //Methods and classes can also be marked final.
        // This restricts the method so that they can't be overridden and classes so that they can't be subclassed.


        //Packages
        //used to avoid name conflicts and to control access to classes.
        // group made up of similar types of classes, along with sub-classes.

        //Encapsulation
        //4 core concepts in OOP.
        /**
         * Encapsulation - implimentation details are not visible to users. Variables of one class will be hidden from other class, accessibel only through the methods of the current class. This is called data hiding.
         * To acheive this, we declare class variables as private, and declare getter and setter variables for them.
         * It controls the way data is accessed or modified
         * More flexible and easily changed code
         * Ability to change one part of the code without affecting other parts
         *
         * Inheritance - Process that enables one class to acquire the properties of another. This way the information is placed in more manageable, hierarchical order.
         * Class inheriting the properties of another is the subclass [derived class or child class.].
         * The class whose properties are inheirted is the superclass[base class or parent class]
         * class Dog extends Animal {some code}
         * When one class is inherited from another class, it inherits all the superclass' non-private variables and methods.
         * Constructors are not member methods, and so are not inherited by subclasses.
         * The constructor of the superclass is called when the subclass is instantiated.
         * We can access the superclass from the subclass using the super keyword.
         * For e.g., super.var accesses the var member of the superclass
         *
         * Polymorphism- refers to the idea of "having many forms" occurs when there is hierarchy of classes related to each other through inheritance.
         * A call to a member method will cause a different implementation to be executed depending on the type of object invoking the method.
         * E.g. Make Dog and Cat class which inherit the same Animal class,Each has it's own implementation of the makeSound() method.
         * Since reference contains the dog object, the method makeSound() of the Dog class will be called.
         *Simple, polymorphism is one method with different implementation.
         *
         * Method Overriding: a subclass can define a behavior that's specific to the subclass type.
         * i.e. a subclass can implement a parent class method based on its requirement.
         * Rules of overriding:
         * - Should have same return type and arguments.
         * - access level cannot be more restrictive than the overridden method's access level.
         * - A method declared final or static cannot be overridden.
         * - if a method cannot be inherited, it cannot be overridden.
         * - Constructors cannot be overridden.
         * Method overriding is also known as runtime polymorphism.
         *
         * Method overloading: Method has same name, but different parameters
         * Very useful when we need same method functionality for different types of parameters.
         * Method overrloading is also known as compile-time polymorphism.
         *
         * Abstraction: Data abstraction provides the outside world only essential information, in a process of representing essential features without including implementation details.
         * We focus on essential qualities, rather than the specific characteristics of the particular example.
         * This is achieved using 'abstract' classes and 'interfaces'.
         * Abstract class is defined using the 'abstract' keyword.
         * If a class is declared abstract, it cannot be instantiated.
         * To use an abstract class, you have to inherit it from another class.
         * Any class that contains an abstract method should be defined as abstract.
         * An abstract method is a method that is declared wihtout an implementation
         * e.g. abstract void walk();
         *
         * Interface- is a completely abstract class that contains ONLY ABSTRACT METHODS (NO ATTRIBUTES).
         * - Defined using interface keyword.
         * - May contain only static final variables
         * - Cannot contain constructor because interfaces cannot be instantiated.
         * - A class can implement any number of interfaces
         * - An interface is implicitly abstract. You do not need to use the abstract keyword while declaring an interface.
         * - Each method in an interface is also implicitly abstract, so the abstract keyword is not needed.
         * - Methods in interface are implicityly public.
         * - A class can inherit from just one superclass, but can implement multiple interfaces.
         * - use implements keyword to use an interface with your class.
         * When we implement an interface, we need to override all of its methods.
         */

        Dog dog1 = new Dog();
        dog1.bark();
        dog1.eat();

        Animal animal1 = new Animal();
        animal1.makeSound();

        Dog dog2 = new Dog();
        dog2.makeSound();

        Cat cat = new Cat();
        cat.makeSound();

        //Method overloading
        double d1=0.434, d2=9.434;
        int d3=434, d4=3243;
        System.out.println(max(d1, d2));
        System.out.println(max(d3, d4));


        //Type Casting
        // Assigning a value of one type to a variable of another type is known as Type Casting.
        a = (int) 3.14;
        System.out.println(a);

        double a2 = (double) 42.1231;
        b = (int) a2;
        System.out.println(b);


        //Type Casting for classes

        //Upcasting
        //You can cast an instance of a subclass to it's superclass
        Animal a3 = new Cat();
        // Upcasting is automatic

        //Downcasting
        // Casting an object of supercloass to it's subclass it called downcasting
        Animal a4 = new Cat();
        ((Animal)a4).makeSound();
        a4.makeSound();

        //Why is upcasting automatic, downcasting manual? Well, upcasting can never fail. But if you have a group of different Animals and want to downcast
        // Them all to a Cat, then there's a chance that some of these Animal are actually Dogs, so the process fails.

        //Anonymous Classes
        // Way to extend existing classes on the fly.
        //After constructor call, we have opened a curly braces and have overridden the start method's implementation on the fly
        // The @Override annotation is used to make code easier to understand, because it makes it more obvious when methods are overridden
        // This modification is applicable only to current object, and not the class itself.
        // So if we create another object of that class, the start method's implementation will be the one defined in the class.
        Machine machine = new Machine(){
            @Override
            public void start(){
                System.out.println("Woooooooooooooo");
            }
        };
        machine.start();

        Machine machine1 = new Machine();
        machine1.start();


        //Inner classes
        //Java supports nesting classes. A class can be a member of another class.
        // Once we declare this new nested class as private, it cannot be accessed from an object outside the class.
        Robot robot = new Robot(1);


        //The Equal method
        //  When we create objects, the variables store references to the objects.
        // So, when we use compare (==), it is actually comparing the references and not the object values.
        Name n1 = new Name("Prashik");
        Name n2 = new Name("Prashik");
        System.out.println(n1==n2);
        //Despite have two objects with similar name, the equality tests returns false because we have two different objects (two different references or memory locations).

        //equals() method
        // Each object has a predefined equals() method that is used for semantical equality testing.
        // Need to implement equals and hashcode()
        System.out.println(n1.equals(n2));


        //Enums
        // Enums are special type used to define collections of constants.
        // Enum cannot be local
        Rank rank = Rank.SOLDIER;
        //Basically, enums define variables that represent members of a fixed set.

        //After declaring enums, we can check for the corresponding values with, for e.g., a switch statement.
        switch (rank){
            case CAPTAIN:
                System.out.println("Captain says HI");
                break;

            case SOLDIER:
                System.out.println("Soldier says Hi");
                break;

            case SERGEANT:
                System.out.println("Sergeant says Hi");

            default:
                System.out.println("No one says Hi");
        }

        //Always use enums when a variable (especially a method parameter) can only take one out of a small set of possible values.
        // If we use enums instead of integers (or string codes), we increase compile-time checking and avoid errors from passing in invalid constants, and you document which values are legal to use.
        // E.g. month, names, days of the week, deck of cards, etc.



        //using the Java API
        // http://docs.oracle.com/javase/7/docs/api/


        //Exceptions
        //Problem that occurs during program execution.
        // Exception handling - Handles runtime erros to maintain normal application flow.
        // Can be caught using combination of try and catch keywords
        /*
        try{
            Some statements
            } catch (Exception e){
        }
         */
        //Catch statement involves declaring the type of exception you are trying to catch.
        // The exception type can be used to catch all possible exceptions.
        try{
            int a6[] = new int[2];
            System.out.println(a6[5]);
        } catch (Exception e){
            System.out.println("An error occured.");
        }
        // Exception e is used to catch all possible exceptions.

        //throw
        // The throw keyword allows you to manually generate exceptions from methods.
        // Some of the numerous available exceptions types include IndexOutOfBoundsException, IllegalArgumentException, ArithmeticException, etc.
        // div() method is using throw
        // throw statement in the method div() defines the type of exceptions the method can throw.
        // Multiple exceptions can be defined in the throws statement using a comma-separated list.
        // A single try block can contain multiple catch blocks, to handle different exceptions.
        /*
        try{
            some code
            }
        catch (ExceptionType1 e1){
            }
        catch (ExceptionType2 e2){
            }
        catch (ExceptionType3 e3){
            }
         */
        //We can use Exception type to handle all other exception as the last catch.



        //Threads
        // java is multi-threaded programming language.
        // We can subdivide specific operatinos within a single application into individual threads that all run in parallel.

        // Two ways to create a thread.
        /**
         * Extent the Thread class: Inherit the Thread class, override it's run() method, and write the functionality of the thread in the run() method.
         * Then we can create a new object of our class and call it's start method to run the thread.
         */
        Loader loader = new Loader();
        loader.start();
        //Here, Loader class extends Thread class and overrides its run() method. When we create the loader object and call its start() method, the run() method statements execute on a different thread.


        //Running thread using Runnable
        //Implementing the Runnable interface, implement the run() method.
        // Then create a new Thread object, and pass the Runnable class to it's constructor, and start the thread by calling the start() method.
        Thread t = new Thread(new LoaderRunnalbe());
        t.start();
        //Thread.sleep() method pauses a thread for specified period of time.
        // Thread.sleep(1000) -> 1 sec pause.
        // Thread.sleep() Throws an InterruptedException, so we need to take care of it as well.
        //Implementing using runnable is preferred because it enables you to extend from another class.

        //Types of Exceptions
        /**
         * Checked - checked during compilation
         * Unchecked (runtime) checked during runtime
         */


        //ArrayList
        // Special classes to store and manipulate groups of objects.
        // ArraysLists are created with an initial size, but when this size is exceed, the collection is automatically enlarged.
        // need to import it from java.util.ArrayList
        ArrayList arrayList = new ArrayList();

        //Provide the capacity and type of the objects the ArrayList would hold.
        ArrayList<String> colors = new ArrayList<>(10);
        //ArrayLists store objects. Thus, the class specified must be class type.
        // We cannot use int or double here. Need to Provide the special class types for these.
        // Integer for int, Double for double, and so on.

        //It also provides a number of useful methods for manipulating its objects.
        // add() methods adds new objects to ArrayList, remove() method removes the object.

        ArrayList<String> color = new ArrayList<String>();
        color.add("Red");
        color.add("Blue");
        color.add("Green");
        System.out.println(color);
        //Other userful methods are
        /**
         * contains(val) - return true if list contains specified element
         * get(int index) - returns element at index
         * size() - return no. of elements
         * clear() - removes all elements
         */




        //LinkedLists
        // need to import java.util.LindedList
        // Very similar to ArrayList
        LinkedList<String> linkedList = new LinkedList<String>();
        // Cannot specify initial capacity for LinkedLists.
        linkedList.add("Red");
        linkedList.add("Blue");
        linkedList.add("Green");
        linkedList.remove("Green");

        /**
         * LinkedLists vs ArrayLists
         * Most notable is the way they store objects.
         * ArrayLists is far better for storing and accessing data, and it is very similar to normal array.
         * LinkedLists is better for manipulating data, such as making numerous inserts and deletes.
         * In addition to storing objects, it stores the memory address of the elements that follows it. Each elements contains a link to the neighboring element.
         *
         * Use ArrayLists when we need rapid access to data.
         * Use LinkedList when we need to make a large number of inserts and/or deletes.
         */
        for(String i: linkedList){
            System.out.println(i);
        }



        //HashMap
        /**
         * Hashmap is used for storing data collections as key and value pairs.
         * One object as key, and the other as value.
         * put(), remove() and get() methods are used to add, delete and access values in HashMap.
         * Cannot contain duplicate keys. Adding a new item with a key that already exists overwrites the old element.
         * Hashmap class provides containsKey() and containsValue() methods to determine the presence of a specified key or value.
         * If you try to get a value that is not present in your map, it return null value.
         * null value is a special value that represents the absence of a value.
         */
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("Amy", 123);
        hashMap.put("Prashik", 434);
        hashMap.put("Raut", 4646);
        System.out.println(hashMap);
        System.out.println(hashMap.get("Amy"));
        //Use get method and the corresponding method to access the hashmap element.



        //HashSets
        /**
         * A set is a collection that cannot contain duplicate elements.
         * One of the implementations of the set is HashSet class.
         * use size() method to get the number of elements in the HashSet
         */
        HashSet<String> set = new HashSet<String>();
        set.add("A");
        set.add("B");
        set.add("C");
        System.out.println(set);



        //LinkedHashSet
        /**
         * The HashSet does not automatically retain the order of elements as they are added.
         * To order the elements, use LinkedHashSet, which maintains a linked list of the set's elements in the order in which they were inserted.
         * What is hashing?
         * A hash table stores information through a mechanism called hashing, in which a key's informational content is used to determine a unique value called a hashcode.
         * Each element in the HashSet is associated with it's unique hash code.
         */



        //Sorting Lists
        /**
         * Collections class
         * need to import java.util.Collections
         * Collections.sort() is the most populart.
         * The methods in Collection class are static, so no need to create an instance.
         * max(Collection C)
         * min(Collection C)
         * reverse(Collection C)
         * shuffle(Collection C)
         *
         */
        Collections.sort(color);
        System.out.println(color);
        //We can also do the same for integer type ArrayList/LinkedList/HashMap/HashSet/LinkedHashSet



        //Iterators
        /**
         * An object that enables to cycle through a collection, obtain or remove elements.
         * Each of the collection classes provides an iterator() method, that returns an iterator to start of the collection.
         * The Iterator class provides the following methods:
         * hasNext(): return true, if there is atleast one more element.
         * next(): return next object and advances
         * remove(): Removes the last ovject that was returned by the next from the collection.
         * need to import this package java.util.Iterator
         * Typically used in loops.
         */
        Iterator<String> it = color.iterator();
        String value = it.next();
        System.out.println(value);
        //Loop implementation
        while(it.hasNext()){
            value = it.next();
            System.out.println(value);
        }



        //Working with files
        /**
         * java.io Package inclues a File class that allows working with files.
         * getName() method returns the name of the file
         */
        // Create a file object and specify the path of the file in the constructor.
        File file = new File("/home/prashik/test.txt");
        if(file.exists()){
            System.out.println(file.getName() + " exists!");
        }
        else{
            System.out.println("File does not exists!");
        }

        //Reading a file
        // One of the simplest way is to use the Scanner class.
        // Constructor of Scanner class can take File object as input.
        // The file contents are output word by word because next() methoh returns each word seperately
        // We can close a file using scanner as the below example states.
        try{
            File file1 = new File("/home/prashik/test.txt");
            Scanner scanner = new Scanner(file1);
            while(scanner.hasNext()){
                System.out.println(scanner.next());
            }
            scanner.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
        }



        //Creating and writing Files
        /**
         * Formatter: useful class in java.utils package, which is used to create content and write it to files.
         * If the file already exists, this will overwrite it.
         * %s means string, and it gets replaced by first arg.
         * \n is newline.
         */
        String fileName = "/home/prashik/Github/Java/test/test.txt";
        try{
            File file2 = new File(fileName);
            if(!file2.exists()){
                System.out.println("text.txt file does not exists. Creating it...");
                try{
                    Formatter formatter = new Formatter(fileName);
                    formatter.format("%s %s %s", "1", "John", "Smith \r\n");
                    formatter.format("%s %s %s", "2", "Amy", "Brown \n");
                    formatter.close();
                }
                catch (Exception e){
                    System.out.println("Error while creating the text file.");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error occured.");
        }


    }
}