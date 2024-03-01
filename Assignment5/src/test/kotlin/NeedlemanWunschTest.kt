package assignment5code

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NeedlemanWunschTest {
    /**
     * Tests the Needleman-Wunsch algorithm with two identical sequences.
     * Expectation: Both aligned sequences should be identical to the input sequences, indicating a perfect match.
     */
    @Test
    fun exactMatch() {
        val seq1 = "AAATA"
        val seq2 = "AAATA"
        val (alignedSeq1, alignedSeq2) = needlemanWunsch(seq1, seq2)
        assertEquals("AAATA", alignedSeq1)
        assertEquals("AAATA", alignedSeq2)
    }

    /**
     * Tests the Needleman-Wunsch algorithm with two sequences that are similar but not identical,
     * whose best match does not include any gaps in the alignment.
     * Expectation: The aligned sequences should be equal to the original sequences as no gaps are introduced.
     */
    @Test
    fun noGapMatch() {
        val seq1 = "AACAA"
        val seq2 = "AATAG"
        val (alignedSeq1, alignedSeq2) = needlemanWunsch(seq1, seq2)
        assertEquals("AACAA", alignedSeq1)
        assertEquals("AATAG", alignedSeq2)
    }

    /**
     * Tests the Needleman-Wunsch algorithm with sequences that require gaps in the alignment
     * to maximize the match between them.
     * Expectation: The alignment should introduce gaps in the appropriate positions to align the sequences optimally.
     */
    @Test
    fun gapMatch() {
        val seq1 = "AACCAA"
        val seq2 = "AATAG"
        val (alignedSeq1, alignedSeq2) = needlemanWunsch(seq1, seq2)
        assertEquals("AACCAA", alignedSeq1)
        assertEquals("AA-TAG", alignedSeq2)
    }

    /**
     * Tests the Needleman-Wunsch algorithm with two sequences that have no matches.
     * Expectation: The aligned sequences should be equal to the original sequences as they have no matching characters
     * and are of the same length.
     */
    @Test
    fun noMatch() {
        val seq1 = "GGTG"
        val seq2 = "AACA"
        val (alignedSeq1, alignedSeq2) = needlemanWunsch(seq1, seq2)
        assertEquals("GGTG", alignedSeq1)
        assertEquals("AACA", alignedSeq2)
    }
}