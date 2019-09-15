package rationals

/**
 * Construct for Rational Numbers
 *
 * @author Kaushik N. Sanji
 */

import java.math.BigInteger
import java.math.RoundingMode

/**
 * This class provides logic for constructing and handling Rational Numbers.
 *
 * @param num [BigInteger] for the Numerator of the Rational Number
 * @param den [BigInteger] for the Denominator of the Rational Number.
 * Defaulted to 1. Cannot be 0.
 *
 * @constructor Creates a [Rational] Number. Throws [IllegalArgumentException] when `den` is 0.
 */
class Rational(
        num: BigInteger,
        den: BigInteger = BigInteger.ONE
) : Comparable<Rational> {

    //Numerator of the Rational
    val numerator: BigInteger
    //Denominator of the Rational
    val denominator: BigInteger

    init {
        //Initializer block of the Primary Constructor

        //Throw [IllegalArgumentException] when [denominator] is 0
        require(den != BigInteger.ZERO) {
            "Denominator must be non-zero"
        }

        //Normalize the Rational Number: START
        //Get the sign of the [den]
        val sign = den.signum().toBigInteger()
        //Get the Greatest Common Divisor(GCD) of [num] and [den]
        val gcd = num.gcd(den)
        //Normalize the [num] and [den] by dividing with the GCD found
        //and multiply them by the sign of [den] so that the [denominator] is always positive
        numerator = num / gcd * sign
        denominator = den / gcd * sign
        //Normalize the Rational Number: END
    }

    /**
     * Operator overloading of "+" on [Rational] Numbers
     *
     * @param other The second [Rational] number involved in the operation
     * @return New [Rational] Number resulting from the "+" operation
     */
    operator fun plus(other: Rational): Rational = Rational(
            numerator * other.denominator + denominator * other.numerator,
            denominator * other.denominator
    )

    /**
     * Operator overloading of "-" on [Rational] Numbers
     *
     * @param other The second [Rational] number involved in the operation
     * @return New [Rational] Number resulting from the "-" operation
     */
    operator fun minus(other: Rational): Rational = Rational(
            numerator * other.denominator - denominator * other.numerator,
            denominator * other.denominator
    )

    /**
     * Operator overloading of "*" on [Rational] Numbers
     *
     * @param other The second [Rational] number involved in the operation
     * @return New [Rational] Number resulting from the "*" operation
     */
    operator fun times(other: Rational): Rational = Rational(
            //Multiply [numerator] with the [numerator] of [other]
            numerator * other.numerator,
            //Multiply [denominator] with the [denominator] of [other]
            denominator * other.denominator
    )

    /**
     * Operator overloading of "/" on [Rational] Numbers
     *
     * @param other The second [Rational] number involved in the operation
     * @return New [Rational] Number resulting from the "/" operation
     */
    operator fun div(other: Rational): Rational = Rational(
            //Multiply [numerator] with the [denominator] of [other]
            numerator * other.denominator,
            //Multiply [denominator] with the [numerator] of [other]
            denominator * other.numerator
    )

    /**
     * Operator overloading of unary "-" on [Rational] Number
     *
     * @return New [Rational] Number resulting from the unary "-" operation
     */
    operator fun unaryMinus(): Rational = Rational(
            //Negate the [numerator]
            -numerator,
            //Keep the [denominator] AS-IS
            denominator
    )

    /**
     * Indicates whether some [other] object is "equal to" `this` one.
     *
     * @param other The [other] object involved in the equals comparison
     * @return `true` if the objects are equal; `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    /**
     * Returns a hash code value for `this` object.
     *
     * @return [Int] value of `this` object hashcode.
     */
    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     *
     * @return [Int] value of the comparison which can be -1, 0, or 1 as `this` is numerically
     * less than, equal to, or greater than [other].
     */
    override fun compareTo(other: Rational): Int {
        //Convert the Rational Numbers to BigDecimal values having scale of 4 decimal places
        val thisResult = numerator.toBigDecimal().divide(denominator.toBigDecimal(), 4, RoundingMode.HALF_EVEN)
        val otherResult = other.numerator.toBigDecimal().divide(other.denominator.toBigDecimal(), 4, RoundingMode.HALF_EVEN)
        //Return the result of comparison of BigDecimal values
        return thisResult.compareTo(otherResult)
    }

    /**
     * Returns a string representation of the object.
     *
     * @return [String] representation of the object.
     */
    override fun toString(): String =
            if (denominator == BigInteger.ONE) {
                //Returns only the [numerator] value when [denominator] is 1
                "$numerator"
            } else {
                //Returns the in the representation of "[numerator]/[denominator]" when [denominator] is more than 1
                "$numerator/$denominator"
            }

}

/**
 * Infix Extension function on [Int] to create [Rational] Number.
 *
 * @param denominator [Int] value for the [denominator] of the [Rational] Number
 * @return [Rational] Number of the infix representation
 */
infix fun Int.divBy(denominator: Int): Rational = Rational(this.toBigInteger(), denominator.toBigInteger())

/**
 * Infix Extension function on [Long] to create [Rational] Number.
 *
 * @param denominator [Long] value for the [denominator] of the [Rational] Number
 * @return [Rational] Number of the infix representation
 */
infix fun Long.divBy(denominator: Long): Rational = Rational(this.toBigInteger(), denominator.toBigInteger())

/**
 * Infix Extension function on [BigInteger] to create [Rational] Number.
 *
 * @param denominator [BigInteger] value for the [denominator] of the [Rational] Number
 * @return [Rational] Number of the infix representation
 */
infix fun BigInteger.divBy(denominator: BigInteger): Rational = Rational(this, denominator)

/**
 * Extension function on [String] to convert the String representation into [Rational] Number.
 *
 * @return [Rational] Number of the String representation
 */
fun String.toRational(): Rational {
    /**
     * Local Extension Function on [String] to convert into BigInteger or throw an [IllegalArgumentException] on failure
     */
    fun String.toBigIntegerOrFail() =
            this.toBigIntegerOrNull() ?: throw IllegalArgumentException(
                    "Expected Rational in the form of 'n/d' or 'n', but was ${this@toRational}"
            )
    //Split the representation by the divide symbol to get the Numerator and Denominator Strings separately
    val numbersStrList = this.split("/")
    //Return the Rational Number of the representation
    return if (numbersStrList.size > 1) {
        //When there is a Denominator in the representation
        Rational(numbersStrList[0].toBigIntegerOrFail(), numbersStrList[1].toBigIntegerOrFail())
    } else {
        //When there is NO Denominator in the representation
        Rational(num = numbersStrList[0].toBigIntegerOrFail())
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

}