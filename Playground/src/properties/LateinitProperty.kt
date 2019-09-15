package properties

/**
 * Without modifying the class 'A' complete the code in 'main' so that
 * an exception UninitializedPropertyAccessException was thrown.
 * Then fix the newly added code in 'main' so that no exception was thrown.
 *
 * <pre>
 * class A {
 *     private lateinit var prop: String
 *
 *     fun setUp() {
 *         prop = "value"
 *     }
 *
 *     fun display() {
 *         println(prop)
 *     }
 * }
 *
 * fun main(args: Array<String>) {
 *     val a = A()
 *     //TODO
 * }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

class A {
    private lateinit var prop: String

    fun setUp() {
        prop = "value"
    }

    fun display() {
        println(prop)
    }
}

fun main(args: Array<String>) {
    val a = A()
    //a.display() //Throws UninitializedPropertyAccessException
    //Fix: Start
    a.setUp()
    a.display()
    //Fix: End
}