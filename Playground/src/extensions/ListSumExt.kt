package extensions

/**
 * Problem Statement:
 * Change the 'sum' function so that it was declared as an extension to List<Int>.
 *
 * <pre>
 *      fun sum(list: List<Int>): Int {
 *          var result = 0
 *          for (i in list) {
 *              result += i
 *          }
 *          return result
 *      }
 *
 *      fun main(args: Array<String>) {
 *          val sum = sum(listOf(1, 2, 3))
 *          println(sum)    // 6
 *      }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

fun List<Int>.sum(): Int {
    var result = 0
    for (i in this) {
        result += i
    }
    return result
}

fun main(args: Array<String>) {
    val sum = listOf(1, 2, 3).sum()
    println(sum)    // 6
}