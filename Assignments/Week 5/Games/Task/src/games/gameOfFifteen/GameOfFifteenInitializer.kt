package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /**
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     *
     * @author Kaushik N. Sanji (solution only)
     */
    override val initialPermutation by lazy {
        // Shuffle numbers 1 to 15 and ensure the Parity remains even
        (1..15).toList().shuffled().apply {
            ensureEvenPermutation()
        }
    }

}

