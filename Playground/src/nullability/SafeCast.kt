package nullability

/**
 * Type cast 'as' throws ClassCastException, if the cast is unsuccessful. Safe cast 'as?' returns null,
 * if the cast is unsuccessful. Declare the s variable to make the first line print null and
 * the second one throw an exception.
 *
 * <pre>
 * fun main(args: Array<String>) {
 *     val s = TODO()
 *     println(s as? Int)    // null
 *     println(s as Int?)    // exception
 * }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

fun main(args: Array<String>) {
    val s = "Kotlin"
    println(s as? Int)    // null
    println(s as Int?)    // exception
}