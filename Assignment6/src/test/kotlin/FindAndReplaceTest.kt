package assignment6code

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FindAndReplaceTest {
    // Test replacing a string with a string of the same length
    @Test
    fun replaceSameLength() {
        val testFAR = replaceText("This is a test.", "is", "IS")
        assertEquals("ThIS IS a test.", testFAR)
    }

    // Test replacing a string with a string that is shorter.
    @Test
    fun replaceShorter() {
        val testFAR = replaceText("This is a test.", "test", "T")
        assertEquals("This is a T.", testFAR)
    }

    // Test replacing a string with a string that is longer.
    @Test
    fun replaceLonger() {
        val testFAR = replaceText("This is a test.", "test", "TEST HERE")
        assertEquals("This is a TEST HERE.", testFAR)
    }

    // Test replacing a string with the same string.
    @Test
    fun replaceSame() {
        val testFAR = replaceText("This is a test.", "test", "test")
        assertEquals("This is a test.", testFAR)
    }

    // Test replacing a string with an empty string.
    @Test
    fun replaceEmpty() {
        val testFAR = replaceText("This is a test.", "test", "")
        assertEquals("This is a .", testFAR)
    }

    // Test replacing a string when the string to replace has two overlapping instances in the text.
    @Test
    fun replaceOverlapping() {
        val testFAR = replaceText("aaaabbaa", "aaa", "AAA")
        assertEquals("AAAabbaa", testFAR)
    }

    // Test replacing a string when the string is not present in the text.
    @Test
    fun noInstances() {
        val testFAR = replaceText("This is a test.", "testing", "TESTING")
        assertEquals("This is a test.", testFAR)
    }

    // Test replacing spaces in a text with a different character.
    @Test
    fun replaceSpace() {
        val testFAR = replaceText("This is a test.", " ", "_")
        assertEquals("This_is_a_test.", testFAR)
    }

    // Test replacing a phrase with a new phrase that contains the original phrase as well as other characters.
    @Test
    fun replaceSpaces() {
        val testFAR = replaceText("is a test.", "is", "is not")
        assertEquals("is not a test.", testFAR)
    }

    // Test replacing a phrase surrounded by spaces; this could be used for a naive replacement for standalone words.
    @Test
    fun replaceSurroundSpaces() {
        val testFAR = replaceText("This is a test.", " is ", " IS ")
        assertEquals("This IS a test.", testFAR)
    }

    // Test standalone with spaces, periods, dashes, and at the beginning of a line.
    @Test
    fun testStandaloneSupport() {
        val testFAR = replaceText("This is a test.", "is", "IS", standalone = true)
        assertEquals("This IS a test.", testFAR)
        val testFAR2 = replaceText("This is a test. testing!", "test", "TEST", standalone = true)
        assertEquals("This is a TEST. testing!", testFAR2)
        val testFAR3 = replaceText("a test-a test!", "a", "W", ignoreCase = true)
        assertEquals("W test-W test!", testFAR3)
    }

    // Test ignoreCase with all lower case, all uppercase, and a mix of upper and lowercase.
    @Test
    fun testIgnoreCaseSupport() {
        val testFAR = replaceText("This IS a test. Is it?", "is", "W", ignoreCase = true)
        assertEquals("ThW W a test. W it?", testFAR)
        val testFAR2 = replaceText("This IS a test. Is it?", "Is", "W", ignoreCase = true)
        assertEquals("ThW W a test. W it?", testFAR2)
        val testFAR3 = replaceText("This IS a test. Is it?", "IS", "W", ignoreCase = true)
        assertEquals("ThW W a test. W it?", testFAR3)
    }
}