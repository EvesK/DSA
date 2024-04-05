package assignment7code

fun main() {
    val originalText = "TOBEORNOTTOBEORTOBEORNOT"
    println("Original text: $originalText")

    val compressed = compress(originalText)
    println("Compressed: $compressed")

    val decompressed = decompress(compressed)
    println("Decompressed: $decompressed")
}

/**
 * Compresses a given string input using the Lempel-Ziv-Welch (LZW) algorithm.
 * The function initializes a dictionary with all possible single-character strings,
 * then iteratively processes the input to find longer sequences. Each sequence that
 * does not exist in the dictionary is added with a new code. The function outputs a list
 * of integers representing encoded sequences from the input.
 *
 * @param input The string to be compressed.
 * @param dictionary An optional AssociativeArray instance pre-populated with single-character keys.
 *                   If not provided, a new instance is created. This dictionary maps sequences
 *                   of characters to unique codes.
 * @return A list of integers representing the compressed form of the input string. Each integer
 *         is a code corresponding to a sequence of characters from the input.
 */
fun compress(input: String, dictionary: AssociativeArray<String, Int> = AssociativeArray()): List<Int> {
    var dictSize = 256 // initialize for the 256 possible ASCII characters

    // Set all possible ASCII characters into the dictionary; we may not use all characters in our text but this ensures that all single characters can be recognized and encoded.
    for (i in 0 until dictSize) {
        dictionary.set(i.toChar().toString(), i)
    }

    val result = mutableListOf<Int>() // Initialize list of codes to output
    var w = "" // Initialize w for the current sequence being processed; start with empty string

    // Iterate over each character in the input string
    for (c in input) {
        val wc = w + c // Add the current sequence with the next character
        if (dictionary.contains(wc)) {
            // If the wc sequence is already in the dictionary, set w to wc and continue to the next character
            w = wc
        } else {
            // If the wc sequence is not in the dictionary, add it with the next available code
            result.add(dictionary.get(w)!!) // Get the code for w
            dictionary.set(wc, dictSize++) // Add wc to teh dictionary with a new code
            w = c.toString() // Reset w to the current character
        }
    }

    // After processing all characters, add the code for any characters left in w
    if (w.isNotEmpty()) result.add(dictionary.get(w)!!)
    return result
}

/**
 * Decompresses a list of integers encoded using the Lempel-Ziv-Welch (LZW) algorithm back into
 * a string. The function rebuilds the dictionary used for compression, utilizing the fact that
 * both compression and decompression dictionaries evolve identically during their respective processes.
 * It iterates through the encoded list, translating each code back into its corresponding string
 * sequence and constructing the original input string.
 *
 * @param compressed A list of integers representing the compressed data, where each integer
 *                   corresponds to a sequence of characters in the original data.
 * @param dictionary An optional AssociativeArray instance for decoding the compressed data.
 *                   If not provided, a new instance is created. This dictionary maps unique codes
 *                   back to sequences of characters based on the order they were added during compression.
 * @return A string representing the decompressed original input.
 */
fun decompress(compressed: List<Int>, dictionary: AssociativeArray<Int, String> = AssociativeArray()): String {
    var dictSize = 256 // initialize for the 256 possible ASCII characters

    // Set all possible ASCII characters into the dictionary; we may not use all characters in our text but this ensures that all single characters can be recognized and encoded.
    for (i in 0 until dictSize) {
        dictionary.set(i, i.toChar().toString())
    }

    // Initialize w to the first string from the compressed list
    var w = dictionary.get(compressed.first())!!
    val result = StringBuilder(w) // Initialize the result with this string

    // Move through the compressed list one code at a time
    for (k in compressed.drop(1)) {
        // Get the corresponding string for the current code
        val entry: String = if (dictionary.contains(k)) {
            dictionary.get(k)!!
        } else if (k == dictSize) {
            // If the code corresponds to the next code to be added, use w + the first char of w
            w + w[0]
        } else {
            throw IllegalArgumentException("Bad compressed k: $k")
        }

        result.append(entry) // Add to the result string
        dictionary.set(dictSize++, w + entry[0]) // Add to the dictionary
        w = entry // Set w to the current entry for the next iteration
    }

    return result.toString() // Return the result string
}