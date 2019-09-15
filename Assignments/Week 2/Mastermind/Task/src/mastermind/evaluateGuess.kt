package mastermind

/**
 * Kotlin file to evaluate the Guessed String for the Secret String
 *
 * @author Kaushik N Sanji
 */

//Data class defined for storing and verifying the 'Guess' for the 'Secret'
data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

const val CORRECT_GUESS = 1 //For the letter which is guessed correctly along with correct position
const val INCORRECT_GUESS = 0 //For the letter which is incorrectly guessed
const val PARTIAL_GUESS = -1 //For the letter which is guessed correctly but wrong in position

/**
 * Method that evaluates the [guess] for the [secret] and returns the
 * result of [Evaluation] that contains the information for the number of letters guessed correctly,
 * with and without correct position.
 *
 * @param secret A secret of letters in sequence to be guessed
 * @param guess Letters in sequence for the [secret] being guessed
 *
 * @return Returns the [Evaluation] of the number of letters guessed correctly, with and without correct position.
 */
fun evaluateGuess(secret: String, guess: String): Evaluation {
    //Evaluate for a list of letters guessed with correct letter and position
    val correctGuesses = evaluateRightPosition(secret, guess)
    //Take the sum of the correct number of letters with position guessed
    val rightPositionCount = correctGuesses.sum()
    //Evaluate for the number of guesses with correct letters but wrong in position
    val wrongPositionCount = if (rightPositionCount < secret.length) {
        //Evaluate only if the number of letters correctly guessed is less
        //than the length of the secret, to find the incorrectly positioned ones
        evaluateWrongPosition(secret, guess, correctGuesses)
    } else {
        //When all letters are correctly guessed along with position
        //there are no wrong guesses, hence assign to 0
        0
    }
    //Return the Evaluation result
    return Evaluation(rightPositionCount, wrongPositionCount)
}

/**
 * Method that evaluates the [guess] for the [secret] and returns the
 * total count of correctly guessed letters in wrong position.
 *
 * @param secret A secret of letters in sequence to be guessed
 * @param guess Letters in sequence for the [secret] being guessed
 *
 * @return Count of correctly guessed letters in wrong position.
 */
fun evaluateWrongPosition(secret: String, guess: String, correctGuesses: MutableList<Int>): Int {
    //Variable to hold the total count of correctly guessed letters in wrong position
    var wrongPositionCount = 0
    //Checking every letter of the 'guess' String
    for (guessIndex in 0 until guess.length) {
        //Skip to next iteration if the letter at the current position was already guessed correctly with position
        if (correctGuesses[guessIndex] == CORRECT_GUESS) continue
        //Searching for the guessed letter in the entire 'secret' String
        for (secretIndex in 0 until secret.length) {
            if (correctGuesses[secretIndex] == INCORRECT_GUESS && guess[guessIndex] == secret[secretIndex]) {
                //If the letter at the position being looked up was previously assumed to be incorrect,
                //but is present in the 'secret', then it is guessed correctly with incorrect position

                //Increment the count
                wrongPositionCount += 1
                //Mark the letter at the position as partially guessed with PARTIAL_GUESS constant
                correctGuesses[secretIndex] = PARTIAL_GUESS
                //No need to search further if found
                break
            }
        }
    }
    //Return the total count of guessed letters in wrong position
    return wrongPositionCount
}

/**
 * Method that evaluates the [guess] for the [secret] and returns a mutable list
 * containing the CORRECT_GUESS constant values at the corresponding position where the
 * guessed letter matches. For incorrect guesses, respective positions are
 * filled with INCORRECT_GUESS constant.
 *
 * @param secret A secret of letters in sequence to be guessed
 * @param guess Letters in sequence for the [secret] being guessed
 *
 * @return Mutable List containing the CORRECT_GUESS constant values at the positions where the
 * guessed letter matches.
 */
fun evaluateRightPosition(secret: String, guess: String): MutableList<Int> {
    //Create a Mutable list to hold the data for the correctly guessed letters with position
    val correctGuesses = mutableListOf<Int>()
    //Iterating over the secret to verify
    for (index in 0 until secret.length) {
        if (secret[index] == guess[index]) {
            //If the letter at the same index matches, then it is correctly guessed with position
            //Add to the list with CORRECT_GUESS constant
            correctGuesses.add(index, CORRECT_GUESS)
        } else {
            //If the letter at the same index do not match, then lets assume that it is incorrectly guessed
            //Add to the list with INCORRECT_GUESS constant
            correctGuesses.add(index, INCORRECT_GUESS)
        }
    }
    //Return the evaluated list for correctly guessed letters with position
    return correctGuesses
}