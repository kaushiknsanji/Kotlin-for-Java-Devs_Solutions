package games.game2048

/**
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
 *
 * @author Kaushik N. Sanji (solution only)
 */
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> =
        // Filter Not Nulls from the list and window the like-values
        this.filterNotNull().windowWithNextWhenSame()
                .map { list: List<T> ->
                    // From the windowed results
                    if (list.size == 2) {
                        // If there is a twin, then merge them
                        merge(list.first())
                    } else {
                        // If there is a single, then return it as-is
                        list.first()
                    }
                }

/**
 * Extension function on a list of values of type [T] that returns
 * a list of windowed lists for the pairs of equal values found
 * when traversed from start to end of the list.
 *
 * @author Kaushik N. Sanji
 */
fun <T : Any> List<T>.windowWithNextWhenSame(): List<List<T>> {
    // Return empty list when empty
    if (isEmpty()) return emptyList()

    // Iterator of the receiver list
    val iterator = iterator()
    // List to store the windowed lists
    val result = mutableListOf<List<T>>()
    // Get the start value
    var current = iterator.next()
    // Tracks if the last element read was windowed or not
    var lastElementWindowed = false

    // Iterating over the receiver list
    while (iterator.hasNext()) {
        // Get the next value
        val next = iterator.next()

        if (current == next) {
            // If current and next values are same,
            // then prepare a windowed list for the twins and add to list of windowed lists
            result.add(listOf(current, next))

            // Check if we have any adjacent value
            if (iterator.hasNext()) {
                // If there is any next value, then read into current and continue
                current = iterator.next()
                continue
            } else {
                // If there is no next value, then we have read all elements in the receiver
                // Mark last element as windowed
                lastElementWindowed = true
                // Bail out
                break
            }

        } else {
            // If current and next values are different,
            // then add the current single to the list of windowed lists
            result.add(listOf(current))
            // Copy the next into current for next iteration
            current = next
            // As the last one was single, mark the last element as NOT windowed
            lastElementWindowed = false
        }

    }

    if (!lastElementWindowed) {
        // If there was a single left out, add it to the list of windowed lists
        result.add(listOf(current))
    }

    // Return the list of windowed lists
    return result
}