package nullability

/**
 * Add and implement an extension function 'isEmptyOrNull()' on the type String?.
 * It should return true, if the string is null or empty.
 *
 * <pre>
 * fun main(args: Array<String>) {
 *     val s1: String? = null
 *     val s2: String? = ""
 *     s1.isEmptyOrNull() eq true
 *     s2.isEmptyOrNull() eq true
 *
 *     val s3 = "   "
 *     s3.isEmptyOrNull() eq false
 * }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

fun main(args: Array<String>) {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s2.isEmptyOrNull() eq true

    val s3 = "   "
    s3.isEmptyOrNull() eq false
}

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}

private fun String?.isEmptyOrNull(): Boolean {
    return this == null || this.isEmpty()
}