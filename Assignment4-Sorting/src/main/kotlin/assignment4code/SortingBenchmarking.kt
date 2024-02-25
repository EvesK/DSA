package assignment4code

import kotlin.random.Random
import kotlin.system.measureTimeMillis
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.style.markers.SeriesMarkers

fun main() {
    val start = 1
    val end = 10000
    val steps = 99

    val results = runTrials(start, end, steps)

    plotResults(results)
}

/**
 * Create a list of random integers of the given size.
 *
 * @param size The number of elements to have in the list.
 * @return A mutable list of size [size] filled with random integers.
 */
fun createRandomList(size: Int): MutableList<Int> {
    val list = mutableListOf<Int>()

    for (i in 0 until size) {
        list.add(Random.nextInt())
    }

    return list
}

/**
 * Run the four sorting algorithms and measure their running time on a given list.
 *
 * @param list The list to run each sorting algorithm on.
 * @return A list with four entries indicating the running time in milliseconds of
 * [Insertion Sort, Selection Sort, Merge Sort, Quick Sort] in that order.
 */
fun measureSortingTimes(list: MutableList<Int>):  List<Long> {
    val times = mutableListOf<Long>()

    val insertionSortList = list.toMutableList()
    val insertionSortTime = measureTimeMillis {
        insertionSort(insertionSortList)
    }
    times.add(insertionSortTime)

    val selectionSortList = list.toMutableList()
    val selectionSortTime = measureTimeMillis {
        selectionSort(selectionSortList)
    }
    times.add(selectionSortTime)

    val mergeSortList = list.toMutableList()
    val mergeSortTime = measureTimeMillis {
        mergeSort(mergeSortList)
    }
    times.add(mergeSortTime)

    val quickSortList = list.toMutableList()
    val quickSortTime = measureTimeMillis {
        quickSort(quickSortList, 0, quickSortList.size - 1)
    }
    times.add(quickSortTime)

    return times
}

/**
 * Run a series of time trials on the four sorting algorithms.
 *
 * @param start The list size to start at.
 * @param end The list size to end at.
 * @param steps The number of steps to calculate lists at between start and end.
 * @return A map where the keys represent the list size and the values are each a list
 * of four elements representing the running time in milliseconds of
 * [Insertion Sort, Selection Sort, Merge Sort, Quick Sort] in that order.
 */
fun runTrials(start: Int, end: Int, steps: Int): Map<Int, List<Long>> {
    val allTimes = mutableMapOf<Int, List<Long>>()

    val stepSize = (end - start) / steps

    for (i in 0..steps) {
        val size = start + (stepSize * i)
        val randomList = createRandomList(size)
        val times = measureSortingTimes(randomList)
        allTimes[size] = times
    }

    return allTimes
}

/**
 * Reformat the results map into the appropriate formats to plot the results
 * and call the plotGraph function to plot the results.
 *
 * (ChatGPT assisted with function creation.)
 *
 * @param results The results map from runTrials.
 */
fun plotResults(results: Map<Int, List<Long>>) {
    val xValues = results.keys.sorted()
    val yValuesInsertionSort = mutableListOf<Long>()
    val yValuesSelectionSort = mutableListOf<Long>()
    val yValuesMergeSort = mutableListOf<Long>()
    val yValuesQuickSort = mutableListOf<Long>()

    xValues.forEach { size ->
        results[size]?.let { times ->
            yValuesInsertionSort.add(times[0])
            yValuesSelectionSort.add(times[1])
            yValuesMergeSort.add(times[2])
            yValuesQuickSort.add(times[3])
        }
    }

    plotGraph(xValues, listOf(yValuesInsertionSort, yValuesSelectionSort, yValuesMergeSort, yValuesQuickSort))
}

/**
 * Plot a graph of list size vs. actual run time for the four sorting algorithms.
 * Requires XChart dependency in the build.gradle file.
 *
 * (ChatGPT assisted with function creation.)
 *
 * @param xValues A list of the list sizes run in runTrials.
 * @param yValuesList A list of four lists where each list represents the run time for one
 * of the sorting algorithms in the order Insertion Sort, Selection Sort, Merge Sort, Quick Sort.
 */
fun plotGraph(xValues: List<Int>, yValuesList: List<List<Long>>) {
    val chart = XYChartBuilder().width(800).height(600).title("Sorting Algorithm Performance").xAxisTitle("List Size").yAxisTitle("Time (ms)").build()

    // Customize Chart
    chart.styler.defaultSeriesRenderStyle = org.knowm.xchart.XYSeries.XYSeriesRenderStyle.Line
    chart.styler.isChartTitleVisible = true
    chart.styler.legendPosition = org.knowm.xchart.style.Styler.LegendPosition.InsideSW
    chart.styler.markerSize = 6

    // Convert xValues to DoubleArray as required by XChart
    val xData = xValues.map { it.toDouble() }.toDoubleArray()

    // The order of yValuesList is InsertionSort, SelectionSort, MergeSort, QuickSort
    val algorithmNames = listOf("Insertion Sort", "Selection Sort", "Merge Sort", "Quick Sort")

    yValuesList.forEachIndexed { index, yValues ->
        // Convert yValues to DoubleArray
        val yData = yValues.map { it.toDouble() }.toDoubleArray()
        val series = chart.addSeries(algorithmNames[index], xData, yData)
        series.marker = SeriesMarkers.NONE
    }

    // Show it
    SwingWrapper(chart).displayChart()
}