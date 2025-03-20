// Staring my groovy code
def var = 'Hello Prashik'
println var

// Differences in Java
int method(String arg) {
    return 1
}

int method(Object arg) {
    return 2
}

Object o = "Object";
int result = method(o);
println('Return Value is: ' + result)
// The return value will be 1.
// This is becaus Java