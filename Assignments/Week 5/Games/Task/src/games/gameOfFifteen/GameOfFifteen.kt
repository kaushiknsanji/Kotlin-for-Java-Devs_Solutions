package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

/**
 * Class that implements the [Game] interface to initialize a [GameBoard] instance for a Game of Fifteen.
 *
 * @property initializer Instance of [GameOfFifteenInitializer] that provides an initial list of values
 * to begin the Game with.
 * @constructor Creates an Instance of [GameOfFifteen]
 *
 * @author Kaushik N. Sanji
 */
class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    // Create and save a GameBoard of width 4
    private val board = createGameBoard<Int?>(4)

    /**
     * Method that initializes the [board] with the list of values provided by the [initializer]
     */
    override fun initialize() {
        board.loadValues(initializer)
    }

    /**
     * Returns `true` if there is any empty `null` [Cell] in the [board].
     * This will be always `true` as there needs to an empty cell always for this Game.
     */
    override fun canMove(): Boolean = board.any { it == null }

    /**
     * Returns `true` if the values stored in the [Cell]s of the [board]
     * from the start to the end cell is 1 to 15 in order.
     */
    override fun hasWon(): Boolean =
            board.filter { it != null }.mapNotNull { cell: Cell -> board[cell] } == (1..15).toList()

    /**
     * Processes the user input in the [direction] inferred
     */
    override fun processMove(direction: Direction) {
        board.moveTile(direction)
    }

    /**
     * Returns the value of the [Cell] at the location `(i, j)` in the [board]
     */
    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

/**
 * Extension function on [GameBoard] that loads it with the initial list of values
 * provided by the [initializer].
 *
 * @author Kaushik N. Sanji
 */
private fun GameBoard<Int?>.loadValues(initializer: GameOfFifteenInitializer) {
    // Split the list provided by initializer by the width of the board and then iterate
    initializer.initialPermutation.chunked(width).forEachIndexed { i, rowList ->
        (0 until width).forEach { j ->
            // At the cell coordinate, load the corresponding value from the initializer list
            // Fill the cell with null when there is no corresponding value available
            this@loadValues[getCell(i + 1, j + 1)] = rowList.getOrNull(j)
        }
    }
}

/**
 * Extension function on [GameBoard] that moves a movable [Cell] of the board in the [direction] provided.
 *
 * @author Kaushik N. Sanji
 */
private fun GameBoard<Int?>.moveTile(direction: Direction) {
    // Get the only empty cell of the GameBoard
    val emptyCell = this.filter { cellValue: Int? -> cellValue == null }.first()
    // Find the Neighbour of the empty cell in the reverse direction
    emptyCell.getNeighbour(direction.reversed())?.let { neighbourCell ->
        // When a Neighbour Cell is found, swap its value with the empty cell
        this[emptyCell] = this[neighbourCell]
        this[neighbourCell] = null
    }
}
