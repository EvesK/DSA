package assignment6code

// NOTE
// This find and replace program is based on the KMP Algorithm as implemented in the KMP.kt file,
// but to increase efficiency I created a separate KMP implementation in the findNextMatch function.
// Unlike the implementation in KMP.kt, which looks for all instances of the pattern in the text,
// the implementation in findNextMatch returns once it finds the first instance; this integrates with the
// replaceText function.

/**
 * The entry point of the program that provides a command-line interface for text replacement functionality.
 *
 * This function prompts the user to input a series of parameters including the original text,
 * the substring to find within this text, the replacement substring, and optionally, flags to modify
 * the behavior of the search and replacement operation. The program supports case-insensitive search
 * and can be instructed to match only standalone instances of the find string. Upon receiving the input,
 * the function processes it according to the specified parameters and outputs the modified text.
 *
 * The program continues to solicit input from the user in a loop, allowing multiple operations without
 * restarting. The loop can be exited by typing 'quit'.
 *
 * Input Format:
 * The user is expected to enter the input in the following format:
 * "text" "find" "replace" [-ignoreCase] [-standalone]
 * where "text" is the original text, "find" is the substring to find, "replace" is the substring to replace it with,
 * and the optional flags [-ignoreCase] and [-standalone] toggle case-insensitive search and standalone word replacement, respectively.
 *
 * Flags:
 * -ignoreCase: Makes the search case-insensitive. If not specified, the search is case-sensitive.
 * -standalone: Restricts matches to standalone instances of the find string. If not specified, all instances are considered.
 *
 * Examples:
 * "This is a test. Testing!" "test" "demo" : Outputs "This is a demo. Testing!"
 * "This is a test. Testing!" "test" "demo" -ignoreCase : Outputs "This is a demo. demoing!"
 * "This is a test. Testing!" "test" "demo" -standalone : Outputs "This is a demo. Testing!"
 * "This is a test. Testing!" "test" "demo" -ignoreCase -standalone : Outputs "This is a demo. Testing!"
 *
 * Error Handling:
 * If the input does not meet the expected format or does not include the required "text", "find", and "replace" parameters,
 * the user is prompted to re-enter the input correctly. To allow the user to ensure the input was parsed as expected,
 * "text", "find", and "replace", along with the status of the two flags, are printed to the console along with the replaced
 * string.
 */
fun main() {
    println("Starting program.")
    println("Enter inputs as \"text\" \"find\" \"replace\" [-ignoreCase] [-standalone]")
    println("Type 'quit' to exit.")

    while (true) {
        val input = readLine() ?: return

        // Check if the user wants to quit
        if (input.equals("quit", ignoreCase = true)) {
            println("Exiting program.")
            break
        }

        // Split the input into parts for quoted and unquoted sections
        val regex = """"(.*?)"|\s*(-\w+)""".toRegex()
        val parts = regex.findAll(input).map {
            it.groupValues[1].ifEmpty { it.groupValues[2] }
        }.toList()

        // Filter out the optional flags to ensure we have the three required parts
        val requiredParts = parts.filterNot { it.startsWith("-") }

        if (requiredParts.size < 3) {
            println("Invalid input. Please ensure you've entered \"text\" \"find\" \"replace\" as well as any optional flags.")
            continue
        }

        // Extract the text, find, and replace parameters
        val txt = parts[0]
        val find = parts[1]
        val replace = parts[2]

        // Determine the presence of flags
        val ignoreCase = parts.contains("-ignoreCase")
        val standalone = parts.contains("-standalone")

        // Call replaceText with the parsed input
        val replacedText = replaceText(txt, find, replace, ignoreCase, standalone)

        // Output the result back to the command line
        println("Text: $txt")
        println("Find: $find")
        println("Replace: $replace")
        println("Ignore Case: $ignoreCase")
        println("Standalone Instances Only: $standalone")
        println("Replaced Text: $replacedText")
    }
}

/**
 * Replaces all occurrences of a substring (find) within a string (txt) with another substring (replace),
 * with options for case insensitivity and matching standalone words only.
 *
 * @param txt The original text where replacements are to be made.
 * @param find The substring to find in the original text.
 * @param replace The substring to replace the found substring with.
 * @param ignoreCase If true, the search is case-insensitive. Default is false.
 * @param standalone If true, only standalone occurrences of the find string are replaced. Default is false.
 * @return A new string with all occurrences of the [find] substring replaced with the [replace] substring, considering specified options.
 */
fun replaceText(txt: String, find: String, replace: String, ignoreCase: Boolean = false, standalone: Boolean = false): String {
    // If the find string is equal to the replace string, return the original text.
    if (ignoreCase && find.equals(replace, ignoreCase = true) || !ignoreCase && find == replace) {
        return txt
    }

    // Initialize the replaced text and the current match.
    var replacedTxt = txt
    var currentMatch = findNextMatch(replacedTxt, find, 0, ignoreCase, standalone)

    // Until all matches have been found and replaced in the text.
    while (currentMatch != null) {
        // Isolate the already checked text with the current match replaced and the unchecked text.
        val checkedTxt = replacedTxt.substring(0, currentMatch) + replace
        val uncheckedTxt = replacedTxt.substring(currentMatch + find.length)

        // Combine to create the next replaced text.
        replacedTxt = checkedTxt + uncheckedTxt

        // Calculate where the unchecked text starts in the replaced text string.
        val nextSearchStart = currentMatch + replace.length

        // Find the next match in the unchecked text section of the replaced text string.
        currentMatch = findNextMatch(replacedTxt, find, nextSearchStart, ignoreCase, standalone)
    }

    // Once all matches have been found and replaced, return the replaced text.
    return replacedTxt
}

/**
 * Finds the next match of a pattern within a text starting from a given index, using the KMP algorithm,
 * and considering options for case sensitivity and standalone matches.
 *
 * @param txt The text in which to search for the pattern.
 * @param find The pattern to search for.
 * @param start The index to start the search from.
 * @param ignoreCase If true, the search is case-insensitive.
 * @param standalone If true, only searches for standalone occurrences of the pattern.
 * @return The index of the start of the next match, or null if no further matches are found.
 */
fun findNextMatch(txt: String, find: String, start: Int, ignoreCase: Boolean, standalone: Boolean): Int? {
    // Initialize the modified txt and modified find, which take into account whether to ignore case.
    val modifiedTxt = if (ignoreCase) txt.lowercase() else txt
    val modifiedFind = if (ignoreCase) find.lowercase() else find
    // Initialize the lps array.
    val lps = computeLPSArray(modifiedFind)

    var i = start // index for txt[]
    var j = 0 // index for pat[]

    // Search through the text for the pattern.
    while (i < modifiedTxt.length) {
        // If there is a character match...
        if (modifiedFind[j] == modifiedTxt[i]) {
            // Move both indices forward to search the next character for both.
            j++
            i++
        }
        // Check if we found the pattern in the text...
        if (j == modifiedFind.length) {
            // If so, store the matchStart.
            val matchStart = i - j
            // Before returning matchStart, check if we need to find a standalone and if so, whether the pattern match we found is standalone or not.
            if (!standalone || isStandaloneMatch(modifiedTxt, matchStart, modifiedFind.length)) {
                return matchStart
            }
            // If the pattern is not standalone, and we are looking for a standalone then continue to search the string.
            j = lps[j - 1] // Look for next possible match
        // If not, and if we aren't at the end of the string yet...
        } else if (i < modifiedTxt.length && modifiedFind[j] != modifiedTxt[i]) {
            // If we already have a partial match...
            if (j != 0) {
                // Skip forward according to the lps array.
                j = lps[j - 1]
            // If there is no match whatsoever...
            } else {
                // Go to the next character in txt.
                i++
            }
        }
    }

    // If the pattern was not found anywhere in the text return null.
    return null
}

/**
 * Checks if a match is standalone, i.e., not part of a larger word.
 *
 * @param txt The complete text.
 * @param index The starting index of the match in the text.
 * @param length The length of the matching pattern.
 * @return True if the match is standalone, false otherwise.
 */
fun isStandaloneMatch(txt: String, index: Int, length: Int): Boolean {
    val beforeIndexValid = index == 0 || !txt[index - 1].isLetterOrDigit()
    val afterIndexValid = index + length == txt.length || !txt[index + length].isLetterOrDigit()

    return beforeIndexValid && afterIndexValid
}

/**
 * Computes the Longest Prefix Suffix (LPS) array for a given pattern.
 *
 * The LPS array is used in the Knuth-Morris-Pratt (KMP) algorithm to enable skipping characters when a mismatch occurs,
 * based on the knowledge of the pattern itself. The LPS array stores the lengths of the longest proper prefix which is
 * also a suffix for all prefixes of the pattern. This pre-processing step enhances the efficiency of the pattern search.
 *
 * @param pat The pattern for which to compute the LPS array.
 * @return An integer array representing the LPS array for the given pattern.
 */
fun computeLPSArray(pat: String): IntArray {
    // Initialize the LPS array
    val lps = IntArray(pat.length) { 0 }

    // Length of the previous longest prefix suffix
    var len = 0

    // Initialize the LPS of the first char to 0
    lps[0] = 0

    // Initialize i to 1.
    var i = 1

    // Loop calculates lps[i] for i = 1 to pat_len - 1
    while (i < pat.length) {
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

    return lps
}