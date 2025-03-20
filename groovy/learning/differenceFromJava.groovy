import groovy.transform.PackageScope

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
