package assignment4code

fun main() {
    val examplelist1 = mutableListOf(10, 4, 5, 99, 3, 65, 0, 1)
    println("Insertion Sort")
    insertionSort(examplelist1)
    println(examplelist1)
    val examplelist2 = mutableListOf(10, 4, 5, 99, 3, 65, 0, 1)
    println("Selection Sort")
    selectionSort(examplelist2)
    println(examplelist2)
    val examplelist3 = mutableListOf(10, 4, 5, 99, 3, 65, 0, 1)
    println("Merge Sort")
    mergeSort(examplelist3)
    println(examplelist3)
    val examplelist4 = mutableListOf(10, 4, 5, 99, 3, 65, 0, 1)
    println("Quick Sort")
    quickSort(examplelist4, 0, examplelist4.size - 1)
    println(examplelist4)
}

/**
 * Sort a list of integers in place using the insertion sort algorithm.
 *
 * Computational Complexity
 * Best Case: O(n) when the array is already sorted, since it will only
 * need to compare each element once to find its correct position.
 * Worst Case: O(n^2) when the array is sorted in reverse order, which would
 * require the algorithm to compare each element with all other elements
 * already sorted.
 *
 * @param list The mutable list of integers to sort.
 */
fun insertionSort(list: MutableList<Int>) {
    for (i in 1 until list.size) {
        val key = list[i]
        var pos = i - 1

        while (pos >= 0 && list[pos] > key) {
            list[pos + 1] = list [pos]
            pos--
        }

        list[pos + 1] = key
    }
}

/**
 * Sort a list of integers in place using the selection sort algorithm.
 *
 * Computational Complexity
 * Average and Worst Case: O(n^2) because it scans the entire list to find
 * the minimum/maximum for each position in the sorted portion, so it will
 * have quadratic time complexity regardless of the initial order of elements.
 *
 * @param list The mutable list of integers to sort.
 */
fun selectionSort(list: MutableList<Int>) {
    for (i in list.indices) {
        var min_idx = i
        for (j in i+1 until list.size) {
            if (list[min_idx] > list[j]) {
                min_idx = j
            }
        }
        val temp = list[min_idx]
        list[min_idx] = list[i]
        list[i] = temp
    }
}

/**
 * Sort a list of integers in place using the merge sort algorithm.
 *
 * Computational Complexity
 * All Cases: O(n log n) since merge sort divides the list into two
 * halves and recursively sorts each half, then merge the two together,
 * its best, average, and worst case complexity is logarithmic. This makes
 * merge sort a good general option for sorting.
 *
 * @param list The mutable list of integers to sort.
 */
fun mergeSort(list: MutableList<Int>) {
    if (list.size > 1) {
        val mid = list.size.floorDiv(2)
        val left = list.subList(0, mid).toMutableList()
        val right = list.subList(mid, list.size).toMutableList()

        mergeSort(left)
        mergeSort(right)

        var i = 0
        var j = 0
        var k = 0

        while (i < left.size && j < right.size) {
            if (left[i] <= right[j]) {
                list[k] = left[i]
                i++
            } else {
                list[k] = right[j]
                j++
            }
            k++
        }

        while (i < left.size) {
            list[k] = left[i]
            i++
            k++
        }

        while (j < right.size) {
            list[k] = right[j]
            j++
            k++
        }
    }
}

/**
 * Sorts a list of integers in place using the quick sort algorithm.
 *
 * Computational Complexity
 * Average Case: O(n log n) due to the divide and conquer strategy, where
 * the array is partitioned around a pivot and then recursively sorted.
 * Worst Case: O(n^2) when the pivot element chosen is one of the extremes
 * of the list (either the smallest or largest element), which leads to severly
 * unbalanced partitions.
 *
 * @param list The mutable list of integers to sort.
 * @param low The starting index of the list segment.
 * @param high The ending index of the list segment.
 */
fun quickSort(list: MutableList<Int>, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = partition(list, low, high)
        quickSort(list, low, pivotIndex - 1)
        quickSort(list, pivotIndex + 1, high)
    }
}

/**
 * Partitions a segment of the list around a pivot element.
 *
 * @param list The list of integers to be partitioned.
 * @param low The starting index of the segment to be partitioned.
 * @param high The ending index of the segment to be partitioned.
 *
 * @return The index of the pivot element after partitioning.
 */
fun partition(list: MutableList<Int>, low: Int, high: Int): Int {
    val pivot = list[high]
    var i = low - 1

    for (j in low until high) {
        if (list[j] <= pivot) {
            i++

            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }

    val temp = list[i + 1]
    list[i + 1] = list[high]
    list[high] = temp

    return i + 1
}