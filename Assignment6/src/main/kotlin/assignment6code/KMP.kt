package assignment6code

/**
 * A class implementing the Knuth-Morris-Pratt (KMP) string searching algorithm to find occurrences of a substring (pattern) within a string (text).
 *
 * @property txt The text in which the pattern will be searched.
 * @property pat The pattern that needs to be found within the text.
 */
class KMP (val txt: String, val pat: String) {
    // Length of the pattern.
    private val pat_len = pat.length

    // Length of the text.
    private val txt_len = txt.length

    // Longest Prefix Suffix (LPS) array for pattern.
    private var lps = IntArray(pat_len) { 0 }

    // List of starting indices where pattern matches in text.
    var matches = mutableListOf<Int>()

    init {
        // Compute the LPS array to facilitate efficient searching.
        computeLPSArray()

        // Perform the KMP search to find matches of the pattern in the text.
        matches = KMPSearch()
    }

    /**
     * Conducts the KMP search algorithm to find all occurrences of the pattern within the text.
     * It populates and returns a list of start indices where the pattern matches the text.
     *
     * @return A mutable list of integers representing the start indices in the text where the pattern is found.
     */
    private fun KMPSearch(): MutableList<Int> {
        var j = 0 // index for pat[]
        var i = 0 // index for txt[]
        var markers = CharArray(txt_len){ ' ' }
        val match_indices = mutableListOf<Int>()

        // Search through the text for the pattern.
        while ((txt_len - i) >= (pat_len - j)) {
            // If there is a match...
            if (pat[j] == txt[i]) {
//                markers[i] = '*'
//                printSearch(i, j, markers)
                // Move both indices forward to search the next character for both.
                i += 1
                j += 1
            }

            // If we found the pattern in the text...
            if (j == pat_len) {
                match_indices.add(i - j)
                // Print where the pattern was found.
//                println("Found pattern at index ${i - j}.")
//                markers = CharArray(txt_len) { ' ' }
                // Skip forward according to the lps array.
                j = lps[j - 1]
            // else if there is a mismatch
            } else if (i < txt_len && pat[j] != txt[i]){
//                markers[i] = '|'
//                printSearch(i, j, markers)
//                markers = CharArray(txt_len) { ' ' }
                // If we already have a partial match...
                if (j != 0) {
                    // Skip forward according to the lps array.
                    j = lps[j - 1]
                // If there is no partial match...
                } else {
                    // Go to the next character in txt.
                    i += 1
                }
            }
        }

        return match_indices
    }

    /**
     * Computes the Longest Prefix Suffix (LPS) array for the pattern.
     * The LPS array is used to skip characters while matching,
     * enabling the algorithm to avoid redundant comparisons.
     */
    private fun computeLPSArray() {
        // Length of the previous longest prefix suffix
        var len = 0

        // Initialize the LPS of the first char to 0
        lps[0] = 0

        var i = 1

        // Loop calculates lps[i] for i = 1 to pat_len - 1
        while (i < pat_len) {
            // If there is a match...
            if (pat[i] == pat[len]) {
                len += 1
                lps[i] = len
                i += 1
            } else {
                // If there is not a match and the previous character was a match...
                if (len != 0) {
                    len = lps[len - 1]
                // If there is not a match and the previous character was also not a match...
                } else {
                    lps[i] = 0
                    i += 1
                }
            }
        }
    }

    /**
     * Utility function for debugging.
     * Prints the current state of search including the text, a set of markers indicating the current match attempt, and the pattern aligned accordingly.
     *
     * @param i The current index in the text being considered.
     * @param j The current index in the pattern being considered.
     * @param markers A character array representing markers for visualizing the search process.
     */
    private fun printSearch(i: Int, j: Int, markers: CharArray) {
        println(txt)
        println(markers.joinToString(""))
        val leadingSpaces = "-".repeat(Math.max(0, i - j))
        println(leadingSpaces + pat)
    }

    /**
     * Prints the results of the KMP search.
     * For each match found, it marks the matching part in the text with asterisks (*).
     * Finally, it prints the number of matches found and the text with marked matches.
     */
    fun printMatch() {
        val markers = CharArray(txt.length) { ' ' }
        for (index in matches) {
            for (k in 0 until pat_len) {
                markers[index + k] = '*'
            }
        }
        println("${matches.size} matches found.")
        println(txt)
        println(markers)
    }
}