package assignment5code

import kotlin.math.log2
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Represents a square matrix with a fixed size, providing operations for matrix manipulation
 * such as getting and setting elements, printing the matrix, and performing matrix multiplication.
 *
 * @property size The dimension of the square matrix.
 */
class SquareMatrix(private val size: Int) {
    private val matrix: MutableList<MutableList<Int>> = MutableList(size) { MutableList(size) { 0 } }

    /**
     * Retrieves the element at the specified row and column of the matrix.
     *
     * @param row The row index of the element to retrieve.
     * @param col The column index of the element to retrieve.
     * @return The value at the specified row and column.
     * @throws IndexOutOfBoundsException If the specified row or column index is out of bounds.
     */
    fun get(row: Int, col: Int): Int {
        if (row > size || col > size) {
            throw IndexOutOfBoundsException("Invalid row or column index.")
        }
        return matrix[row][col]
    }


    /**
     * Sets the value of the element at the specified row and column of the matrix.
     *
     * @param row The row index where the element is to be set.
     * @param col The column index where the element is to be set.
     * @param value The value to set at the specified row and column.
     * @throws IndexOutOfBoundsException If the specified row or column index is out of bounds.
     */
    fun set(row: Int, col: Int, value: Int) {
        if (row > size || col > size) {
            throw IndexOutOfBoundsException("Invalid row or column index.")
        }
        matrix[row][col] = value
    }

    /**
     * Prints the matrix to the standard output, row by row, with each element separated by a space.
     */
    fun printMatrix() {
        for (row in matrix) {
            for (value in row) {
                print("$value ")
            }
            println()
        }
        println()
    }

    /**
     * Multiplies [this] matrix with another square matrix using traditional matrix multiplication.
     *
     * @param other The matrix to multiply with.
     * @return The product of [this] matrix and the [other] matrix, or null if the matrices are not of the same size.
     */
    fun multiply(other: SquareMatrix): SquareMatrix? {
        if (this.size != other.size) {
            return null
        }
        val result = SquareMatrix(this.size)
        for (i in 0 until this.size) {
            for (j in 0 until other.size) {
                for (k in 0 until this.size) {
                    val previousResult = result.get(i, j)
                    result.set(i, j, (previousResult + (this.get(i, k) * other.get(k, j))))
                }
            }
        }
        return result
    }

    /**
     * Multiplies [this] matrix with another square matrix using Strassen's algorithm for matrices of size 2^n.
     *
     * @param other The matrix to multiply with.
     * @return The product of [this] matrix and the [other] matrix using Strassen's algorithm, or null if the matrices are not of the same size or are not of size 2^n.
     */
    fun strassenMultiply(other: SquareMatrix): SquareMatrix? {
        if (this.size != other.size) {
            return null
        }

        if (!isPowerOfTwo(size)) {
            println("Matrix size $size is not a power of two.")
            return null
        }

        // Base Case
        if (this.size == 1) {
            return SquareMatrix(1).apply {
                set(0, 0, this@SquareMatrix.get(0, 0) * other.get(0, 0))
            }
        }

        // Split both matrices into 4 sub-matrices
        val newSize = size / 2
        val (a, b, c, d) = listOf(
            split(this, 0, 0, newSize),
            split(this, 0, newSize, newSize),
            split(this, newSize, 0, newSize),
            split(this, newSize, newSize, newSize)
        )
        val (e, f, g, h) = listOf(
            split(other, 0, 0, newSize),
            split(other, 0, newSize, newSize),
            split(other, newSize, 0, newSize),
            split(other, newSize, newSize, newSize)
        )

        // Recursively calculate the seven products
        val p1 = a.strassenMultiply(f.subtract(h))
        val p2 = (a.add(b)).strassenMultiply(h)
        val p3 = (c.add(d)).strassenMultiply(e)
        val p4 = d.strassenMultiply(g.subtract(e))
        val p5 = (a.add(d)).strassenMultiply(e.add(h))
        val p6 = (b.subtract(d)).strassenMultiply(g.add(h))
        val p7 = (a.subtract(c)).strassenMultiply(e.add(f))

        // Calculate the resulting sub-matrices
        val m11 = p5!!.add(p4!!).subtract(p2!!).add(p6!!)
        val m12 = p1!!.add(p2)
        val m21 = p3!!.add(p4)
        val m22 = p1.add(p5).subtract(p3).subtract(p7!!)

        return combine(m11, m12, m21, m22, newSize)
    }

    /**
     * Checks if a number is a power of two, used to validate matrix sizes for Strassen's algorithm.
     *
     * @param n The number to check.
     * @return True if n is a power of two, otherwise false.
     */
    private fun isPowerOfTwo(n: Int): Boolean {
        if (n == 0) return false
        return ceil(log2(n.toDouble())) == floor(log2(n.toDouble()))
    }

    /**
     * Splits the current matrix into a smaller square matrix, used for Strassen's multiplication.
     *
     * @param matrix The matrix to split.
     * @param rowStart The starting row index for the split.
     * @param colStart The starting column index for the split.
     * @param size The size of the resulting square matrix.
     * @return A new SquareMatrix instance representing the split portion of the original matrix.
     */
    private fun split(matrix: SquareMatrix, rowStart: Int, colStart: Int, size: Int): SquareMatrix {
        val result = SquareMatrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                result.set(i, j, (matrix.get((rowStart + i), (colStart + j))))
            }
        }
        return result
    }

    /**
     * Adds another matrix to [this] matrix and returns the resulting matrix.
     *
     * @param other The matrix to add to this one.
     * @return A new SquareMatrix representing the sum of [this] matrix and the [other] matrix.
     */
    private fun add(other: SquareMatrix): SquareMatrix {
        val result = SquareMatrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                result.set(i, j, (this.get(i, j) + other.get(i, j)))
            }
        }
        return result
    }

    /**
     * Subtracts another matrix from [this] matrix and returns the resulting matrix.
     *
     * @param other The matrix to be subtracted from this one.
     * @return A new SquareMatrix instance representing the difference between [this] matrix and the [other] matrix.
     */
    private fun subtract(other: SquareMatrix): SquareMatrix {
        val result = SquareMatrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                result.set(i, j, (this.get(i, j) - other.get(i, j)))
            }
        }
        return result
    }

    /**
     * Combines four matrices into one larger matrix.
     *
     * @param m11 The matrix representing the top-left quadrant of the final matrix.
     * @param m12 The matrix representing the top-right quadrant of the final matrix.
     * @param m21 The matrix representing the bottom-left quadrant of the final matrix.
     * @param m22 The matrix representing the bottom-right quadrant of the final matrix.
     * @param newSize The size of each of the smaller matrices, which is half the size of the final matrix.
     * @return A new SquareMatrix instance that combines the four input matrices into one.
     */
    private fun combine(m11: SquareMatrix, m12: SquareMatrix, m21: SquareMatrix, m22: SquareMatrix, newSize: Int): SquareMatrix {
        val result = SquareMatrix(newSize * 2)
        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                result.set(i, j, m11.get(i, j)) // Top-left quadrant
                result.set(i, j + newSize, m12.get(i, j)) // Top-right quadrant
                result.set(i + newSize, j, m21.get(i, j)) // Bottom-left quadrant
                result.set(i + newSize, j + newSize, m22.get(i, j)) // Bottom-right quadrant
            }
        }
        return result
    }
}

