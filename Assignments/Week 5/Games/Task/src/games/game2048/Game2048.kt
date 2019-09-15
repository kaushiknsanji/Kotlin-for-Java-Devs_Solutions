package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

/**
 * Class that implements the [Game] interface to initialize a [GameBoard] instance for 2048 game.
 *
 * @property initializer Instance of [Game2048Initializer] that seeds a value as the Game progresses.
 * @constructor Creates an Instance of [Game2048]
 *
 * @author Kaushik N. Sanji (comments only)
 */
class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    // Create and save a GameBoard of width 4
    private val board = createGameBoard<Int?>(4)

    /**
     * Method that initializes the [board] with two random values, each at a random [Cell] in the [board]
     * provided by the [initializer]
     */
    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    /**
     * Returns `true` if there is any empty `null` [Cell] in the [board]
     */
    override fun canMove() = board.any { it == null }

    /**
     * Returns `true` if there is any [Cell] in the [board] with `2048` value
     * which indicates that the Game is solved.
     */
    override fun hasWon() = board.any { it == 2048 }

    /**
     * Processes the user input in the [direction] inferred
     */
    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            // When values are moved, load a new value to an empty cell
            board.addNewValue(initializer)
        }
    }

    /**
     * Returns the value of the [Cell] at the location `(i, j)` in the [board]
     */
    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/**
 * Add a new value produced by [initializer] to a specified cell in a board.
 *
 * @author Kaushik N. Sanji (solution only)
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) =
        initializer.nextValue(this)?.let {
            // When the nextValue generated is not null
            pair: Pair<Cell, Int> ->
            // Set the provided Cell with the provided value
            this@addNewValue[pair.first] = pair.second
        }


/**
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 *
 * @author Kaushik N. Sanji (solution only)
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    // Get the Cell values for the List of Cells provided
    val rowOrColumnValues = rowOrColumn.map { cell: Cell -> this[cell] }
    // Do the move and merge equals on the Cell values
    val mergeResult = rowOrColumnValues
            .moveAndMergeEqual { value: Int -> value * 2 }
            .apply {
                // Using the result of move and merge equals,
                rowOrColumn.forEachIndexed { index, cell ->
                    // On each cell, set the new value to the Cell from the result,
                    // and fill with null where there is no value
                    this@moveValuesInRowOrColumn[cell] = this.getOrNull(index)
                }
            }

    // Returning true when there is mergeResult and the new values are different
    return (mergeResult.isNotEmpty() && rowOrColumnValues != mergeResult)
}

/**
 * Update the values stored in a board,
 * so that the values were "moved" to the specified [direction]
 * following the rules of the 2048 game .
 * Use the [moveValuesInRowOrColumn] function above.
 * Return `true` if the values were moved and 'false' otherwise.
 *
 * @author Kaushik N. Sanji (solution only)
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    // Tracks the number of Rows or Columns whose Cell values are moved/merged
    var countOfRowsOrColumnsChanged = 0

    // On Each row/column
    (1..width).forEach { rowOrColumnIndex: Int ->
        // Get the row/column cells based on the direction
        val rowOrColumnCells = directionBasedCells(rowOrColumnIndex, direction)
        // Move the values in the row/column and update the count if values were moved/merged
        if (moveValuesInRowOrColumn(rowOrColumnCells)) countOfRowsOrColumnsChanged++
    }

    // Returning true if any values of any row/column were moved
    return (countOfRowsOrColumnsChanged > 0)
}

/**
 * Extension function on [GameBoard] that returns a list of [Cell]s based on the [direction] requested,
 * such that the manipulation in [moveValuesInRowOrColumn] will be applied in the correct [direction].
 *
 * @param currentRowOrColumnIndex {Int] value of the current iteration in row or column
 * @param direction [Direction] of user action
 *
 * @author Kaushik N. Sanji
 */
private fun <T> GameBoard<T?>.directionBasedCells(currentRowOrColumnIndex: Int, direction: Direction): List<Cell> =
        when (direction) {
            // For RIGHT, row of Cells needs to be fetched starting with the Cell of the LAST column,
            // since values will be moved to the LEFT(opposite direction) always via [moveValuesInRowOrColumn]
            Direction.RIGHT -> this.getRow(currentRowOrColumnIndex, width downTo 1)

            // For LEFT, row of Cells needs to be fetched starting with the Cell of the FIRST column,
            // since values will be moved to the LEFT(same direction) always via [moveValuesInRowOrColumn]
            Direction.LEFT -> this.getRow(currentRowOrColumnIndex, 1..width)

            // For DOWN, column of Cells needs to be fetched starting with the Cell of the LAST row,
            // since values will be moved to the UP(opposite direction) always via [moveValuesInRowOrColumn]
            Direction.DOWN -> this.getColumn(width downTo 1, currentRowOrColumnIndex)

            // For UP, column of Cells needs to be fetched starting with the Cell of the FIRST row,
            // since values will be moved to the UP(same direction) always via [moveValuesInRowOrColumn]
            Direction.UP -> this.getColumn(1..width, currentRowOrColumnIndex)
        }


