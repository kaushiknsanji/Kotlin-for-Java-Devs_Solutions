package games.game2048

import board.Cell
import board.GameBoard
import kotlin.random.Random

interface Game2048Initializer<T> {
    /*
     * Specifies the cell and the value that should be added to this cell.
     */
    fun nextValue(board: GameBoard<T?>): Pair<Cell, T>?
}

object RandomGame2048Initializer : Game2048Initializer<Int> {
    private fun generateRandomStartValue(): Int =
            if (Random.nextInt(10) == 9) 4 else 2

    /**
     * Generate a random value and a random cell among free cells
     * that given value should be added to.
     * The value should be 2 for 90% cases, and 4 for the rest of the cases.
     * Use the 'generateRandomStartValue' function above.
     * If the board is full return null.
     *
     * @author Kaushik N. Sanji (solution only)
     */
    override fun nextValue(board: GameBoard<Int?>): Pair<Cell, Int>? =
            board.filter { it == null } // Filter for empty Cells
                    .run {
                        if (isNotEmpty()) {
                            // If there are empty cells,
                            // then return a random cell with a random start value
                            this.random() to generateRandomStartValue()
                        } else {
                            // If there are no empty cells, then return null
                            null
                        }
                    }

}