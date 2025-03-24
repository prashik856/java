import groovy.transform.PackageScope

import java.sql.Time

// Differences in Java

// Multi Methods
println "2. Multi Method Difference"
int method(String arg) {
    return 1
}

int method(Object arg) {
    return 2
}

Object o = "Object";
int result = method(o);
println('Return Value is: ' + result)
println()
// The return value will be 1.
// This is because Java is Static typed language, which will call the method(Object arg) to return 2
// Whereas Groovy will choose at runtime, when the method is called. Since it is called with a String, it will return 1.

// Array Initializers
println "3. Array Initializers Difference"
/*
int[] array = {1, 2, 3};             // Java array initializer shorthand syntax
int[] array2 = new int[] {4, 5, 6};  // Java array initializer long syntax
* */
int[] array = [1, 2, 3]
println array

int[] array2 = new int[] {4, 5, 6} // Groovy 3.0+ supports the Java-style array initialization long syntax
println(array2)

def array3 = new int[] {7,8,9} // Groovy 3.0+ supports the Java-style array initialization long syntax
println(array3)

println()

println "4. Package Scope Visibility"
class Person {
    String name // Name does not become package private here.
}

// Package private field by annotating it with private
class Person2 {
    @PackageScope String name
}

println()

println("5. ARM blocks")
// A way in Groovy to read a file
new File("/home/prashik/Documents/Github/java/groovy/learning/1.Introduction.md")
        .eachLine('UTF-8') {
    println it
}

// Version closer to java
new File("/home/prashik/Documents/Github/java/groovy/learning/1.Introduction.md")
    .withReader('UTF-8') {
        reader -> {
            reader.eachLine {
                println it
            }
        }
    }

println()

println("6. Static Inner Classes")
class A {
    static class B{}
}
def C = new A.B();
println()

println("6.1. Anonymous Inner Classes")
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

CountDownLatch called = new CountDownLatch(1);
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    @Override
    void run() {
        called.countDown()
    }
}, 0)
assert called.await(10, TimeUnit.SECONDS);

println()

println("6.2. Creating Instances of Non-Static Inner Classes")
/*
In Java:
public class Y {
    public class X {}
    public X foo() {
        return new X();
    }
    public static X createX(Y y) {
        return y.new X();
    }
}
* */
public class Y {
    public class X {}
    public X foo() {
        return new X()
    }
    public static X createX(Y y) {
        return new X(y)
    }
}
println()

println("7.Lambda expressions and the method reference operator")
/*
In Java 8:
Runnable run = () -> System.out.println("Run");  // Java
list.forEach(System.out::println);
* */
// In Groovy
Runnable run = {println 'run'}
//list.each {println it}

println()

println("9.String and Character literals")
/*
Singly-quoted literals in Groovy are used for String, and double-quoted result in String or GString, depending whether there is interpolation in the literal.
* */
try {
    assert 'c'.class == String
    assert "c".class == String
    assert "c${1}".class in GString
} catch (Exception e) {
    e.printStackTrace()
}

char a = 'a'
try {
    assert Character.digit(a, 16) == 10: 'But Groovy does boxing'
    assert Character.digit((char) 'a', 16) == 10

    assert Character.digit('a', 16) == 10
    assert false: 'Need explicit cast'
} catch (Exception e) {
    e.printStackTrace()
}

// Groovy supports two styles of casting and in the case of casting to char there are subtle differences when casting a multi-char strings. The Groovy style cast is more lenient and will take the first character, while the C-style cast will fail with exception.
try {
    assert ((char) "c").class == Character
    assert ("c" as char).class == Character

    ((char) 'cx') == 'c'
    assert false: 'will fail - not castable'

    assert ('cx' as char) == 'c'
    assert 'cx'.asType(char) == 'c'
} catch (Exception e) {
    e.printStackTrace()
}

println()

println("10.Behaviour of ==")
// In Java, == means equality of primitive types or identity for objects.
// In Groovy, == means equality in all places.
// For non-primitives, it translates to a.compareTo(b) == 0, when evaluating equality for Comparable objects, and a.equals(b) otherwise.
// To check for identity (reference equality), use the is method: a.is(b). From Groovy 3, you can also use the === operator (or negated version): a === b (or c !== d).

println()

println("11. Primitives and Wrappers")
/*
public class Main {
    float f1 = 1.0f
    Float f2 = 2.0f

    float add(Float a1, float a2) { return a1 + a2; }

    Float calc() { return add(f1, f2); }

    public static void main(String[] args) {
       Float calcResult = new Main().calc();
       System.out.println(calcResult); // => 3.0
    }
}
* */

// In Groovy
class Main {

    float f1 = 1.0f
    Float f2 = 2.0f

    float add(Float a1, float a2) { a1 + a2 }

    Float calc() { add(f1, f2) }
}

assert new Main().calc() == 3.0

// Groovy, also supports primitives and object types, however, it goes a little further in pushing OO purity; it tries hard to treat everything as an object. Any primitive typed variable or field can be treated like an object, and it will be auto-wrapped as needed.
/*


public class Main {           // Java

    public float z1 = 0.0f;

    public static void main(String[] args){
      new Main().z1.equals(1.0f); // DOESN'T COMPILE, error: float cannot be dereferenced
    }
}
* */

// Using Groovy


class Main {
    float z1 = 0.0f
}
assert !(new Main().z1.equals(1.0f))

int i
m(i)

// 1
void m(long l) {
    println "in m(long)"
}

// 2
void m(Integer i) {
    println "in m(Integer)"
}

// 1 - This is the method that Java would call, since widening has precedence over unboxing.
// 2 - This is the method Groovy actually calls, since all primitive references use their wrapper class.

