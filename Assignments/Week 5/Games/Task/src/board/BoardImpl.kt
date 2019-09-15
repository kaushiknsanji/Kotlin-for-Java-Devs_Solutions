package board

/**
 * Kotlin file for top level functions
 * that provides instances of [SquareBoard] and [GameBoard] Interfaces.
 *
 * @author Kaushik N. Sanji
 */

// Compile Time constant for the starting number in a [Cell] coordinate
const val START_CELL_NUMBER = 1

/**
 * Top level function that returns an Instance of [SquareBoard] interface
 * for the given board [width]. Instance is constructed by an Anonymous class.
 *
 * @param width [Int] value of the Board width to generate a [SquareBoard]
 * @return Instance of [SquareBoard]
 */
fun createSquareBoard(width: Int): SquareBoard = object : SquareBoard {
    // Width of SquareBoard
    override val width: Int = width

    // Map of Row Id and its List of Cells
    private val gridMap: Map<Int, List<Cell>>

    // Initializer block
    init {
        gridMap = mutableListOf<Cell>().apply {
            // Constructing a List of Cells for each 'i' and 'j'
            // upto the 'width'
            (START_CELL_NUMBER..width).forEach { i ->
                (START_CELL_NUMBER..width).forEach { j ->
                    this.add(Cell(i, j))
                }
            }
        }.groupBy {
            // Grouping the List of Cells in a Map by the Row Id 'i'
            cell: Cell ->
            cell.i
        }
    }

    /**
     * Returns a [Cell] if found for the given
     * Row Id [i] and Column Id [j] coordinates. If not found, or the
     * given coordinates are invalid, then it returns `null`.
     */
    override fun getCellOrNull(i: Int, j: Int): Cell? = try {
        // Return the Cell if found; otherwise Null
        gridMap[i]?.get(j - START_CELL_NUMBER)
    } catch (e: IndexOutOfBoundsException) {
        // Return Null if the coordinates are invalid
        null
    }

    /**
     * Returns a [Cell] if found for the given
     * Row Id [i] and Column Id [j] coordinates. If not found, or the
     * given coordinates are invalid, then it throws [IllegalArgumentException].
     */
    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j) ?: throw IllegalArgumentException(
            "Board does not have any Cell with coordinates ($i, $j)")

    /**
     * Returns an entire [Collection] of [Cell]s constructed
     * for the given [width] of the [SquareBoard].
     */
    override fun getAllCells(): Collection<Cell> = gridMap.values.flatten()

    /**
     * Returns a [List] of [Cell]s corresponding to the
     * given coordinates ([i], [jRange]). Result will contain only the [Cell]s
     * of the coordinates that exist.
     *
     * @param i [Int] value of the Row Id required.
     * @param jRange [IntProgression] range of values of the Column Id required.
     * @return A [List] of [Cell]s of the given row and columns
     */
    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = mutableListOf<Cell?>().apply {
        // Creating a Mutable List of Cells
        // Iterating over the range of Columns
        jRange.forEach {
            // Adding the Cell found, to the List. May contain Nulls
            j ->
            this.add(getCellOrNull(i, j))
        }
    }.filterNotNull() // Returning only Non Null values

    /**
     * Returns a [List] of [Cell]s corresponding to the
     * given coordinates ([iRange], [j]). Result will contain only the [Cell]s
     * of the coordinates that exist.
     *
     * @param iRange [IntProgression] range of values of the Row Id required.
     * @param j [Int] value of the Column Id required.
     * @return A [List] of [Cell]s of the given column and rows.
     */
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = mutableListOf<Cell?>().apply {
        // Creating a Mutable List of Cells
        // Iterating over the range of Rows
        iRange.forEach {
            // Adding the Cell found, to the List. May contain Nulls
            i ->
            this.add(getCellOrNull(i, j))
        }
    }.filterNotNull() // Returning only Non Null values

    /**
     * Returns a Neighbouring [Cell] of the
     * given [Cell] in the given [direction]. Can be `null` if there
     * is no Neighbouring [Cell] in the given [direction]
     */
    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        // Neighbouring Row Cell Down
        Direction.DOWN -> getCellOrNull(this.i + 1, this.j)
        // Neighbouring Row Cell Up
        Direction.UP -> getCellOrNull(this.i - 1, this.j)
        // Neighbouring Column Cell Right
        Direction.RIGHT -> getCellOrNull(this.i, this.j + 1)
        // Neighbouring Column Cell Left
        Direction.LEFT -> getCellOrNull(this.i, this.j - 1)
    }
}

/**
 * Top level function that returns an Instance of [GameBoard] interface
 * for the given board [width]. Instance is constructed by an Anonymous class, partly delegating the
 * [SquareBoard] implementation to its instance.
 *
 * @param T The Type of the value stored for each [Cell] of [SquareBoard]
 * @param width [Int] value of the Board width to generate a [SquareBoard]
 * @return Instance of [GameBoard]
 */
fun <T> createGameBoard(width: Int): GameBoard<T> = object : SquareBoard by createSquareBoard(width), GameBoard<T> {
    // Mutable Map of Cell and its Value
    private val gridValueMap: MutableMap<Cell, T?>

    // Initializer block
    init {
        gridValueMap = mutableMapOf<Cell, T?>().apply {
            // Constructing a Mutable Map of Cells and its Value for each 'i' and 'j'
            // upto the 'width'
            (START_CELL_NUMBER..width).forEach { i ->
                (START_CELL_NUMBER..width).forEach { j ->
                    this[getCell(i, j)] = null // Mapping 'null' value to each SquareBoard Cell key
                }
            }
        }
    }

    /**
     * Returns a nullable value of Type [T]
     * stored for the given [cell]
     */
    override fun get(cell: Cell): T? = gridValueMap[cell]

    /**
     * Saves a nullable value of Type [T]
     * for the given [cell]
     */
    override fun set(cell: Cell, value: T?) {
        gridValueMap[cell] = value
    }

    /**
     * Returns a filtered [Collection] of [Cell]s that
     * satisfies the given [predicate] applied on the nullable values of Type [T]
     * stored in the [Cell]s
     */
    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = gridValueMap.filterValues(predicate).keys

    /**
     * Returns the first [Cell] in the [GameBoard] that
     * satisfies the given [predicate] applied on the nullable values of Type [T]
     * stored in the [Cell]s; or `null` if the [Cell] was not found.
     */
    override fun find(predicate: (T?) -> Boolean): Cell? = gridValueMap.filterValues(predicate).keys.takeIf(Set<Cell>::isNotEmpty)?.first()

    /**
     * Returns `true` if any [Cell] in the [GameBoard] was found
     * to satisfy the given [predicate] applied on the nullable values of Type [T]
     * stored in the [Cell]s. Otherwise, returns `false`
     */
    override fun any(predicate: (T?) -> Boolean): Boolean = gridValueMap.filterValues(predicate).isNotEmpty()

    /**
     * Returns `true` if all [Cell]s in the [GameBoard]
     * satisfies the given [predicate] applied on the nullable values of Type [T]
     * stored in the [Cell]s. Otherwise, returns `false`
     */
    override fun all(predicate: (T?) -> Boolean): Boolean = gridValueMap.filterValues(predicate).size == gridValueMap.size

}

