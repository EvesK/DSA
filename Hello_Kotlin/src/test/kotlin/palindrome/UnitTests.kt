package palindrome

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UnitTests {
    @Test
    fun test999to1001() {
        val currentPalindrome = 999
        val palindrome = PalindromicIntegers()
        val nextPalindrome = palindrome.nextPalindrome(currentPalindrome)
        assertEquals(1001, nextPalindrome)
    }

    @Test
    fun test0to1() {
        val currentPalindrome = 0
        val palindrome = PalindromicIntegers()
        val nextPalindrome = palindrome.nextPalindrome(currentPalindrome)
        assertEquals(1, nextPalindrome)
    }

    @Test
    fun test111to121() {
        val currentPalindrome = 111
        val palindrome = PalindromicIntegers()
        val nextPalindrome = palindrome.nextPalindrome(currentPalindrome)
        assertEquals(121, nextPalindrome)
    }

    @Test
    fun test1200000021to1200110021() {
        val currentPalindrome = 1200000021
        val palindrome = PalindromicIntegers()
        val nextPalindrome = palindrome.nextPalindrome(currentPalindrome)
        assertEquals(1200110021, nextPalindrome)
    }
}