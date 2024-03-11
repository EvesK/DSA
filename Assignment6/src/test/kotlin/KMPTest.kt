package assignment6code

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KMPTest {
    // Test with one instance of the pattern in the text.
    @Test
    fun oneInstance() {
        val testKMP = KMP("AAAABAAB", "ABA")
        assertEquals(mutableListOf(3), testKMP.matches)
    }

    // Test with two instances of the pattern in the text.
    @Test
    fun twoInstances() {
        val testKMP = KMP("AAAABAABA", "ABA")
        assertEquals(mutableListOf(3, 6), testKMP.matches)
    }

    // Test with two overlapping instances of the pattern in the text.
    @Test
    fun overlappingInstances() {
        val testKMP = KMP("AAAABAABA", "AAA")
        assertEquals(mutableListOf(0, 1), testKMP.matches)
    }

    // Test with no instances of the pattern in the text.
    @Test
    fun noInstances() {
        val testKMP = KMP("AAAABAABA", "CC")
        assertEquals(mutableListOf<Int>(), testKMP.matches)
    }

    // Test with incompatible arguments.
    @Test
    fun needleLongerThanHaystack() {
        val testKMP = KMP("AAABA", "AAAABAABA")
        assertEquals(mutableListOf<Int>(), testKMP.matches)
    }

    // Test with a variety of characters.
    @Test
    fun testAllSymbols() {
        val testKMP = KMP(" !#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~'", "AA")
        assertEquals(mutableListOf<Int>(), testKMP.matches)
    }
}