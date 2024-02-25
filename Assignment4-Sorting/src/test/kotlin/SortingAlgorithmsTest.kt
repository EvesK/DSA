package assignment4code

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SortingAlgorithmsTest {
    @Test
    fun insertionSortTest() {
        val testlist = mutableListOf(0, 0, 33, 5, 4, 2, 77, 909, 123, 2)
        insertionSort(testlist)
        assertEquals(mutableListOf(0, 0, 2, 2, 4, 5, 33, 77, 123, 909), testlist)
        val testlistone = mutableListOf(1)
        insertionSort(testlistone)
        assertEquals(mutableListOf(1), testlistone)
    }

    @Test
    fun selectionSortTest() {
        val testlist = mutableListOf(0, 0, 33, 5, 4, 2, 77, 909, 123, 2)
        selectionSort(testlist)
        assertEquals(mutableListOf(0, 0, 2, 2, 4, 5, 33, 77, 123, 909), testlist)
        val testlistone = mutableListOf(1)
        selectionSort(testlistone)
        assertEquals(mutableListOf(1), testlistone)
    }

    @Test
    fun mergeSortTest() {
        val testlist = mutableListOf(0, 0, 33, 5, 4, 2, 77, 909, 123, 2)
        mergeSort(testlist)
        assertEquals(mutableListOf(0, 0, 2, 2, 4, 5, 33, 77, 123, 909), testlist)
        val testlistone = mutableListOf(1)
        mergeSort(testlistone)
        assertEquals(mutableListOf(1), testlistone)
    }

    @Test
    fun quickSortTest() {
        val testlist = mutableListOf(0, 0, 33, 5, 4, 2, 77, 909, 123, 2)
        quickSort(testlist, 0, testlist.size - 1)
        assertEquals(mutableListOf(0, 0, 2, 2, 4, 5, 33, 77, 123, 909), testlist)
        val testlistone = mutableListOf(1)
        quickSort(testlistone, 0, testlistone.size - 1)
        assertEquals(mutableListOf(1), testlistone)
    }
}