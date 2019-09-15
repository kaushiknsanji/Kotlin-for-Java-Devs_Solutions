package inline_functions

/**
 * Write the code that the Kotlin compiler will generate while inlining the filter function (instead of calling it).
 * Note that the compiler generates Java bytecode, but for simplicity, write the similar code in Kotlin.
 * The simplified declaration of 'filter' is given below.
 *
 * <pre>
 * fun filterNonZero(list: List<Int>) = list.filter { it != 0 }
 *
 * fun filterNonZeroGenerated(list: List<Int>): List<Int> {
 *     TODO()
 * }
 *
 * fun main(args: Array<String>) {
 *     val list = listOf(1, 2, 3)
 *
 *     filterNonZero(list).toString() eq "[1, 2, 3]"
 *     filterNonZeroGenerated(list).toString() eq "[1, 2, 3]"
 * }
 *
 * inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
 *     val destination = ArrayList<T>()
 *     for (element in this) {
 *         if (predicate(element)) {
 *             destination.add(element)
 *         }
 *     }
 *     return destination
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

fun filterNonZero(list: List<Int>) = list.filter { it != 0 }

fun filterNonZeroGenerated(list: List<Int>): List<Int> {
    val destination = mutableListOf<Int>()
    for (element: Int in list) {
        if (element != 0) {
            destination.add(element)
        }
    }
    return destination
}

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)

    filterNonZero(list).toString() eq "[1, 2, 3]"
    filterNonZeroGenerated(list).toString() eq "[1, 2, 3]"
}

inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    val destination = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            destination.add(element)
        }
    }
    return destination
}

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}