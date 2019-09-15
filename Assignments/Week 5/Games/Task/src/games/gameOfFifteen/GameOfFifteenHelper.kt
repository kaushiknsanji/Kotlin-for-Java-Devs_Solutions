package games.gameOfFifteen

/**
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * [https://en.wikipedia.org/wiki/Parity_of_a_permutation]

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 *
 * @author Kaushik N. Sanji (solution only)
 */
fun isEven(permutation: List<Int>): Boolean {
    // For the count of inversions found in the list of numbers
    var countOfInversions = 0

    // Iterate over the complete list
    permutation.forEachIndexed { i, number1 ->
        // Iterate over the sublist after the number picked
        permutation.slice(i + 1 until permutation.size).forEach { number2 ->
            // If the number at the beginning is more than the following number in the list
            // then it is an inversion, hence increment the count
            if (number1 > number2) countOfInversions++
        }
    }

    // Returning true if the count of inversions is even, which determines the Parity of Permutation
    return (countOfInversions % 2 == 0)
}

/**
 * Extension function on a permutation [List] of [Int] that ensures the permutation remains even. If not even,
 * then it performs a transpose of any two determined positions in the permutation where an inversion was found.
 * A transpose on an odd permutation makes it even again.
 *
 * @author Kaushik N. Sanji
 */
fun List<Int>.ensureEvenPermutation() {
    // For the count of inversions found in the list of numbers
    var countOfInversions = 0

    // Positions of the numbers in the list to be swapped
    var swapPosition1 = 0
    var swapPosition2 = 0

    // Iterate over the complete list
    this.forEachIndexed { i, number1 ->
        // Iterate over the sublist after the number picked
        this.slice(i + 1 until this.size).forEachIndexed { j, number2 ->
            if (number1 > number2) {
                // If the number at the beginning is more than the following number in the list
                // then it is an inversion, hence increment the count
                countOfInversions++

                // Save the positions of the numbers in inversion
                swapPosition1 = i
                swapPosition2 = j + i + 1
            }
        }
    }

    if (countOfInversions % 2 != 0) {
        // If the Parity is odd, then swap/transpose the numbers in inversion found in the end
        this.toMutableList().swap(swapPosition1, swapPosition2)
    }
}

/**
 * Extension function on a permutation [List] of [Int] that swaps the numbers in inversion
 * found at the positions [index1] and [index2] of the list.
 *
 * @author Kaushik N. Sanji
 */
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}