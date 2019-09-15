package sequences

/**
 * Implement the function that builds a sequence of Fibonacci numbers using 'sequence' function. Use 'yield'.
 *
 * <pre>
 * fun fibonacci(): Sequence<Int> = sequence {
 *     TODO()
 * }
 *
 * fun main(args: Array<String>) {
 *     fibonacci().take(4).toList().toString() eq
 *             "[0, 1, 1, 2]"
 *
 *     fibonacci().take(10).toList().toString() eq
 *             "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
 * }
 *
 * infix fun <T> T.eq(other: T) {
 *     if (this == other) println("OK")
 *     else println("Error: $this != $other")
 * }
 * </pre>
 *
 * @author Kaushik N. Sanji
 */

fun fibonacci(): Sequence<Int> = sequence {
    // Set the first two numbers in the fibonacci series
    var nextPair = 0 to 1

    // Generates infinite sequence
    while (true) {
        // Yield the first number at every iteration
        yield(nextPair.first)
        // Compute the next pair of numbers in the fibonacci series
        nextPair = nextPair.second to nextPair.first + nextPair.second
    }
}

fun main(args: Array<String>) {
    fibonacci().take(4).toList().toString() eq
            "[0, 1, 1, 2]"

    fibonacci().take(10).toList().toString() eq
            "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34]"
}

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}