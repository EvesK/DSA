package assignment5code

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SquareMatrixTest {
    /**
     * Tests creating a square matrix and setting values.
     * This test verifies if the set and get functions of the SquareMatrix class work as expected
     * by setting values at specific positions and then retrieving them.
     */
    @Test
    fun createAndFill() {
        val mySquareMatrix = SquareMatrix(4)
        mySquareMatrix.set(0, 0, 1)
        mySquareMatrix.set(0, 1, 3)
        assertEquals(1, mySquareMatrix.get(0, 0))
        assertEquals(3, mySquareMatrix.get(0, 1))
    }

    /**
     * Tests the behavior of the SquareMatrix class when accessing out-of-bounds indices.
     * This test ensures that attempting to set or get values at invalid indices
     * throws the expected IndexOutOfBoundsException with the correct message.
     */
    @Test
    fun testExceptions() {
        val mySquareMatrix = SquareMatrix(4)
        val setException = assertThrows<IndexOutOfBoundsException> {
            mySquareMatrix.set(0, 6, 10) // Trying to set a value in an invalid column
        }
        assertEquals("Invalid row or column index.", setException.message)

        val getException = assertThrows<IndexOutOfBoundsException> {
            mySquareMatrix.get(0, 6)
        }
        assertEquals("Invalid row or column index.", getException.message)
    }

    /**
     * Tests traditional matrix multiplication to verify correctness.
     * This test checks if the traditional matrix multiplication method correctly computes
     * the product of two matrices by comparing the result with a manually calculated matrix.
     */
    @Test
    fun traditionalMultiplication() {
        val m1 = SquareMatrix(2)
        m1.set(0, 0, 1)
        m1.set(0, 1, 2)
        m1.set(1, 0, 3)
        m1.set(1, 1, 2)

        val m2 = SquareMatrix(2)
        m2.set(0, 0, 4)
        m2.set(0, 1, 5)
        m2.set(1, 0, 6)
        m2.set(1, 1, 5)

        val m3 = SquareMatrix(2)
        m3.set(0, 0, 16)
        m3.set(0, 1, 16)
        m3.set(1, 0, 24)
        m3.set(1, 1, 25)

        val mm = m1.multiply(m2)
        assertEquals(m3.printMatrix(), mm!!.printMatrix())
    }

    /**
     * Tests the Strassen matrix multiplication algorithm for correctness.
     * Similar to traditionalMultiplication, but instead verifies the correctness
     * of the Strassen's algorithm implementation by comparing the result with a manually
     * calculated matrix.
     */
    @Test
    fun strassenMultiplication() {
        val m1 = SquareMatrix(2)
        m1.set(0, 0, 1)
        m1.set(0, 1, 2)
        m1.set(1, 0, 3)
        m1.set(1, 1, 2)

        val m2 = SquareMatrix(2)
        m2.set(0, 0, 4)
        m2.set(0, 1, 5)
        m2.set(1, 0, 6)
        m2.set(1, 1, 5)

        val m3 = SquareMatrix(2)
        m3.set(0, 0, 16)
        m3.set(0, 1, 16)
        m3.set(1, 0, 24)
        m3.set(1, 1, 25)

        val mm = m1.strassenMultiply(m2)
        assertEquals(m3.printMatrix(), mm!!.printMatrix())
    }

    /**
     * Compares the results of traditional and Strassen's matrix multiplication methods.
     * This test ensures both multiplication methods produce the same result when applied to
     * the same pair of matrices, verifying the consistency between the two implementations.
     */
    @Test
    fun compareTraditionalStrassen() {
        val m1 = createRandomMatrix(4)
        val m2 = createRandomMatrix(4)
        val rt = m1.multiply(m2)
        val rs = m1.strassenMultiply(m2)
        assertEquals(rt!!.printMatrix(), rs!!.printMatrix())
    }
}