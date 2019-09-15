package oop

/**
 * Complete the declaration of the class A without throwing NullPointerException explicitly
 * so that NPE was thrown during the creation of its subclass B instance.
 *
 * <pre>
 * open class A(open val value: String) {
 *     init {
 *        //TODO
 *     }
 * }
 *
 * class B(override val value: String) : A(value)
 *
 * fun main(args: Array<String>) {
 *     B("a")
 * }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

open class A(open val value: String) {
    init {
        println(value.length)
    }
}

class B(override val value: String) : A(value)

fun main(args: Array<String>) {
    B("a")
}