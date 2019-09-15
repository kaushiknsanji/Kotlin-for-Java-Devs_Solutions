package lambda_with_receiver

/**
 * Implement member extension functions 'record' and 'unaryPlus' so that the code below compiles and stores
 * specified words. These functions should be unavailable outside of the 'Words' class.
 *
 * <pre>
 * class Words {
 *     private val list = mutableListOf<String>()
 *
 *     // TODO
 *
 *     override fun toString() = list.toString()
 * }
 *
 * fun main(args: Array<String>) {
 *     val words = Words()
 *     with(words) {
 *         // The following two lines should compile:
 *         "one".record()
 *         +"two"
 *     }
 *     words.toString() eq "[one, two]"
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

class Words {
    private val list = mutableListOf<String>()

    /**
     * Member extension function to add a [String] to the [list] of words.
     */
    fun String.record() = list.add(this)

    /**
     * Member extension operator function to add a [String] to the [list] of words.
     */
    operator fun String.unaryPlus() = record()

    override fun toString() = list.toString()
}

fun main(args: Array<String>) {
    val words = Words()
    with(words) {
        // The following two lines should compile:
        "one".record()
        +"two"
    }
    words.toString() eq "[one, two]"
}

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}