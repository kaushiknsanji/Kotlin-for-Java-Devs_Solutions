package nicestring

/**
 * Kotlin file that evaluates a String for "Nice" or "Bad" String, based on the following conditions -
 * 1. It doesn't contain substrings bu, ba or be.
 * 2. It contains at least three vowels (vowels are a, e, i, o and u).
 * 3. It contains a double letter (at least two similar letters following one another),
 * like b in "abba".
 *
 * Minimum of two conditions should be met to qualify a String as "Nice" String.
 *
 * @author Kaushik N Sanji
 */

//Constant for bad substrings following 'b' character
const val BAD_BX_CHARACTERS = "aeu"
//Constant for Minimum number of Vowels to qualify for Nice String
const val MIN_VOWEL_COUNT = 3
//Constant of Vowels to check
const val VOWELS = "aeiou"

/**
 * String extension function that tests for "Nice" String.
 *
 * @return Boolean: Returns `true` when two conditions for "Nice" String were met; `false` otherwise.
 */
fun String.isNice(): Boolean {
    //Make pairs with neighboring characters of the String and test for
    //bad substrings - "ba", "be", "bu"
    val hasBadStrings = this.zipWithNext().any {
        it.first == 'b' && it.second in BAD_BX_CHARACTERS
    }

    //Count the occurrence of Vowels in the String
    val vowelCount = this.count { it in VOWELS }

    //Make pairs with neighboring characters of the String and
    //count the occurrence of same consecutive letters
    val doubleLetterCount = this.zipWithNext().count { it.first == it.second }

    //Return true when minimum of two conditions satisfy
    return (!hasBadStrings && vowelCount >= MIN_VOWEL_COUNT)
            || (!hasBadStrings && doubleLetterCount > 0)
            || (vowelCount >= MIN_VOWEL_COUNT && doubleLetterCount > 0)
}

