package functional_prg

/**
 * Complete the following implementation of 'evaluateGuess()' function.
 *
 * <pre>
 * data class Evaluation(val rightPosition: Int, val wrongPosition: Int)
 *
 * fun evaluateGuess(secret: String, guess: String): Evaluation {
 *
 *     val rightPositions = secret.zip(guess).count { TODO() }
 *
 *     val commonLetters = "ABCDEF".sumBy { ch ->
 *
 *         Math.min(secret.count { TODO() }, guess.count { TODO() })
 *     }
 *     return Evaluation(rightPositions, commonLetters - rightPositions)
 * }
 *
 * fun main(args: Array<String>) {
 *     val result = Evaluation(rightPosition = 1, wrongPosition = 1)
 *     evaluateGuess("BCDF", "ACEB") eq result
 *     evaluateGuess("AAAF", "ABCA") eq result
 *     evaluateGuess("ABCA", "AAAF") eq result
 * }
 * </pre>
 *
 * @author Kaushik N Sanji
 */

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { (c1, c2) -> c1 == c2 }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { it == ch }, guess.count { it == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = Evaluation(rightPosition = 1, wrongPosition = 1)
    evaluateGuess("BCDF", "ACEB") eq result
    evaluateGuess("AAAF", "ABCA") eq result
    evaluateGuess("ABCA", "AAAF") eq result
}

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}