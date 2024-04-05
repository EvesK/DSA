package assignment7code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LempelZivTest {
    @Test
    fun `test short sequence`() {
        val originalText = "ABC"
        val compressed = compress(originalText)
        val decompressed = decompress(compressed)

        assertEquals(originalText, decompressed, "The decompressed text does not match the original text.")
    }

    @Test
    fun `test long sequence`() {
        // A much longer and more complex string with various characters
        val originalText = """
            This is a longer test sequence to evaluate the compression and decompression capabilities 
            of the Lempel-Ziv algorithm. It includes spaces, punctuation, and a mix of uppercase 
            and lowercase letters. This text is designed to challenge the algorithm with a realistic 
            sample of plain text. Let's see how it handles this!
            """.trimIndent()
        val compressed = compress(originalText)
        val decompressed = decompress(compressed)

        assertEquals(originalText, decompressed, "The decompressed text does not match the original text.")
    }
}