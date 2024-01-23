/*
Find the digits of the next largest palindromic number.
*/

fun main() {
    val currentPalindrome = 1441 // Input any palindromic integer here.
    val nextPalindrome = nextPalindrome(currentPalindrome)
    println("The given palindromic integer is $currentPalindrome. The next palindromic integer is $nextPalindrome.")
}

fun digitList(integer: Int): MutableList<Int> {
    /*
    Create a list of an integer's digits in order.

    Given an integer, return a list of the integer's digits, from most
    significant to least significant. Thus, the number 123 would result in the
    list [1, 2, 3].

    Args:
        integer: An int representing the number whose digits to make a list.

    Returns:
        A list representing the digits of integer in order.
     */
    val digits: MutableList<Int> = mutableListOf()
    for (digit in integer.toString()) {
        digits.add(digit.digitToInt())
    }
    return digits
}

fun nextPalindromeDigits(digits: MutableList<Int>): List<Int> {
    /*
     Given a list of single-digit integers representing a palindromic number,
    return a list of the digits of the next largest palindromic number.

    A palindromic number is one whose digits are the same forwards and
    backwards, such as 33, 1221, or 2468642. Given a list of single digits
    representing such a number, such as [1, 2, 2, 1] for 1221, return the
    digits of the next largest palindromic number. For 1221, this would be
    1331, so return [1, 3, 3, 1].

    Args:
        digits: A list of single-digit integer representing the digits of
            the palindromic number to start from.

    Returns:
        A list of single-digit integers representing the digits of the next
        palindromic number.
     */

    var highMid = digits.size / 2
    var lowMid = (digits.size - 1) / 2
    while (highMid < digits.size && lowMid >= 0) {
        if (digits[highMid] == 9) {
            digits[highMid] = 0
            digits[lowMid] = 0
            highMid++
            lowMid--
        } else {
            digits[highMid]++
            if (lowMid != highMid) {
                digits[lowMid]++
            }
            return digits
        }
    }
    return List(digits.size + 1) { if (it == 0 || it == digits.size) 1 else 0}
}

fun assemble(digits: List<Int>): Int {
    /*
    Assemble a list of digits into an integer.

    Given a list of single-digit integers representing the digits of a possibly
    multi-digit integer, return the integer corresponding to those digits. As an
    example the list [1, 2, 3] would be assembled into the integer 123.

    Args:
        digits: A list of integers representing the digits of the number to
            assemble.

    Returns:
        An int representing the number assembled from digits.
     */

    var integer = 0
    for (digit in digits) {
        integer = integer * 10 + digit
    }
    return integer
}

fun nextPalindrome(palindromicInt: Int): Int {
    /*
     Given a positive integer whose digits are a palindrome, find the next number
    whose digits are a palindrome.

    Given a palindromic integer (whose digits are the same forwards and
    backwards), find the next number (in increasing order) that is a palindromic
    integer. For example, if the starting palindromic integer is 292, the next
    palindromic integer is 303.

    Note that this function does not behave correctly if the integer argument is
    negative or not palindromic.

    Args:
        palindromicInt: An int representing a palindromic integer.

    Returns:
        An int representing the next palindromic integer.
     */

    val digits = digitList(palindromicInt)
    val nextDigits = nextPalindromeDigits(digits)
    return assemble(nextDigits)
}