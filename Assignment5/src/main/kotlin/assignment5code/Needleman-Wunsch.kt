package assignment5code

fun main() {
    val seq1 = "AATCGGGTA"
    val seq2 = "AACGTTAGA"
    val (alignedSeq1, alignedSeq2) = needlemanWunsch(seq1, seq2)
    printSequences(alignedSeq1, alignedSeq2)
}

/**
 * Initializes the score and direction matrices used for the Needleman-Wunsch algorithm.
 *
 * @param m The length of the first sequence.
 * @param n The length of the second sequence.
 * @param gapPenalty The penalty score for introducing a gap in the alignment.
 * @return A pair of two-dimensional arrays representing the score matrix and the direction matrix.
 */
fun initializeMatrices(m: Int, n: Int, gapPenalty: Int): Pair<Array<IntArray>, Array<IntArray>> {
    val scoreMatrix = Array(m + 1) {
        IntArray(n + 1)
    }
    val directionMatrix = Array(m + 1) {
        IntArray(n + 1)
    }
    for (i in 0..m) {
        scoreMatrix[i][0] = i * gapPenalty
    }

    for (j in 0..n) {
        scoreMatrix[0][j] = j * gapPenalty
    }

    return Pair(scoreMatrix, directionMatrix)
}

/**
 * Fills the score and direction matrices based on the alignment scoring rules.
 * The function iterates through each cell of the matrices, calculating scores for match, mismatch, and gap,
 * and determining the optimal direction for traceback.
 *
 * @param seq1 The first sequence to be aligned.
 * @param seq2 The second sequence to be aligned.
 * @param scoreMatrix The matrix that will hold the scores of alignments.
 * @param directionMatrix The matrix that will hold the directions for traceback.
 * @param matchScore The score for a sequence match.
 * @param mismatchScore The penalty for a sequence mismatch.
 * @param gapPenalty The penalty for introducing a gap in the alignment.
 */
fun fillMatrices(seq1: String, seq2: String, scoreMatrix: Array<IntArray>, directionMatrix: Array<IntArray>, matchScore: Int, mismatchScore: Int, gapPenalty: Int) {
    for (i in 1..seq1.length) {
        for (j in 1..seq2.length) {
            val match = scoreMatrix[i - 1][j - 1] + if (seq1[i - 1] == seq2[j - 1]) matchScore else mismatchScore
            val delete = scoreMatrix[i - 1][j] + gapPenalty
            val insert = scoreMatrix[i][j - 1] + gapPenalty

            scoreMatrix[i][j] = maxOf(match, delete, insert)

            directionMatrix[i][j] = when (scoreMatrix[i][j]) {
                match -> 0
                delete -> 1
                else -> 2
            }
        }
    }
}

/**
 * Performs the traceback operation from the bottom-right corner of the direction matrix to reconstruct the optimal alignment.
 * This function builds the aligned sequences by tracing back through the direction matrix, starting from the bottom-right corner,
 * and using the direction indicators to decide whether to insert a gap or align the characters from the original sequences.
 *
 * @param seq1 The first sequence.
 * @param seq2 The second sequence.
 * @param directionMatrix The matrix containing the directions for traceback.
 * @return A pair of strings representing the optimally aligned sequences.
 */
fun traceback(seq1: String, seq2: String, directionMatrix: Array<IntArray>): Pair<String, String> {
    var alignedSeq1 = ""
    var alignedSeq2 = ""
    var i = seq1.length
    var j = seq2.length

    while (i > 0 && j > 0) {
        when (directionMatrix[i][j]) {
            0 -> {
                alignedSeq1 = seq1[i - 1] + alignedSeq1
                alignedSeq2 = seq2[j - 1] + alignedSeq2
                i--
                j--
            }
            1 -> {
                alignedSeq1 = seq1[i - 1] + alignedSeq1
                alignedSeq2 = "-" + alignedSeq2
                i--
            }
            2 -> {
                alignedSeq1 = "-" + alignedSeq1
                alignedSeq2 = seq2[j - 1] + alignedSeq2
                j--
            }
        }
    }

    while (i > 0) {
        alignedSeq1 = seq1[i - 1] + alignedSeq1
        alignedSeq2 = "-" + alignedSeq2
        i--
    }

    while (j > 0) {
        alignedSeq1 = "-" + alignedSeq1
        alignedSeq2 = seq2[j - 1] + alignedSeq2
        j--
    }

    return Pair(alignedSeq1, alignedSeq2)
}

/**
 * Implements the Needleman-Wunsch algorithm for sequence alignment.
 * This function orchestrates the process of initializing matrices, filling them based on the scoring system,
 * and performing the traceback to find the optimal alignment of the two sequences.
 *
 * @param seq1 The first sequence to be aligned.
 * @param seq2 The second sequence to be aligned.
 * @param matchScore The score for matching characters. Defaults to 1.
 * @param mismatchScore The penalty for mismatching characters. Defaults to -1.
 * @param gapPenalty The penalty for introducing gaps in the alignment. Defaults to -2.
 * @return A pair of strings representing the optimally aligned sequences.
 */
fun needlemanWunsch(seq1: String, seq2: String, matchScore: Int = 1, mismatchScore: Int = -1, gapPenalty: Int = -2): Pair<String, String> {
    val (m, n) = seq1.length to seq2.length
    val (scoreMatrix, directionMatrix) = initializeMatrices(m, n, gapPenalty)
    fillMatrices(seq1, seq2, scoreMatrix, directionMatrix, matchScore, mismatchScore, gapPenalty)
    return traceback(seq1, seq2, directionMatrix)
}

/**
 * Prints the aligned sequences along with a comparison row indicating matches and mismatches.
 * The comparison row uses '|' for matches and ' ' (space) for mismatches or gaps, providing a visual guide
 * to the alignment quality between the two sequences.
 *
 * @param alignedSeq1 The first aligned sequence.
 * @param alignedSeq2 The second aligned sequence.
 */
fun printSequences(alignedSeq1: String, alignedSeq2: String) {
    val comparisonRow = StringBuilder()
    for (i in alignedSeq1.indices) {
        if (alignedSeq1[i] == alignedSeq2[i]) {
            comparisonRow.append("|")
        } else {
            comparisonRow.append(" ")
        }
    }
    println("Aligned Sequences:")
    println(alignedSeq1)
    println(comparisonRow.toString())
    println(alignedSeq2)
}