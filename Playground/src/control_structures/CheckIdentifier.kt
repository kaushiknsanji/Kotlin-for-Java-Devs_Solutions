package control_structures

/**
 * Problem Statement:
 * Implement the function that checks whether a string is a valid identifier. A valid identifier is a non-empty string
 * that starts with a letter or underscore and consists of only letters, digits and underscores.
 *
 * @author Kaushik N Sanji
 */

fun isValidIdentifier(s: String): Boolean {
    return """^\D\w+""".toRegex().matches(s)
}

fun main(args: Array<String>) {
    println(isValidIdentifier("name"))   // Should print true
    println(isValidIdentifier("_name"))  // Should print true
    println(isValidIdentifier("_12"))    // Should print true
    println(isValidIdentifier(""))       // Should print false
    println(isValidIdentifier("012"))    // Should print false
    println(isValidIdentifier("no$"))    // Should print false
}