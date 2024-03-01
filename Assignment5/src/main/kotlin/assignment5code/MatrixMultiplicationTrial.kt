package assignment5code

import kotlin.random.Random
import kotlin.system.measureTimeMillis
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.style.markers.SeriesMarkers
import kotlin.math.pow

fun main() {
    val allTimes = runTrials(8)
    plotResults(allTimes)
}

/**
 * Generates a square matrix of a specified size filled with random integer values.
 *
 * @param size The size of the matrix to be created, resulting in a matrix of dimensions [size] x [size].
 * @return A [SquareMatrix] object filled with randomly generated integers.
 */
fun createRandomMatrix(size: Int): SquareMatrix {
    val result = SquareMatrix(size)
    for (i in 0 until size) {
        for (j in 0 until size) {
            result.set(i, j, Random.nextInt())
        }
    }
    return result
}

/**
 * Measures the execution times of traditional and Strassen matrix multiplication methods.
 *
 * @param m1 The first square matrix involved in the multiplication.
 * @param m2 The second square matrix involved in the multiplication.
 * @return A list containing two [Long] values: the execution time of traditional multiplication followed by Strassen multiplication.
 */
fun measureMultiplicationTimes(m1: SquareMatrix, m2: SquareMatrix): List<Long> {
    val times = mutableListOf<Long>()

    val multiplyTime = measureTimeMillis {
        m1.multiply(m2)
    }
    times.add(multiplyTime)

    val strassenTime = measureTimeMillis {
        m1.strassenMultiply(m2)
    }
    times.add(strassenTime)

    return times
}

/**
 * Runs trials of matrix multiplication across a range of matrix sizes and measures performance.
 *
 * @param startingSize The size of the matrix where trials begin.
 * @param endingSize The size of the matrix where trials end.
 * @param stepSize The increment size between trials.
 * @return A map where keys are matrix sizes and values are lists of execution times for traditional and Strassen multiplication.
 */
fun runTrials(numSteps: Int): Map<Int, List<Long>> {
    val allTimes = mutableMapOf<Int, List<Long>>()

    println("Starting Trials")

    for (i in 2 until (numSteps + 2)) {
        val size = 2.0.pow(i.toDouble()).toInt()
        println("Matrix Size: $size")
        val m1 = createRandomMatrix(size)
        val m2 = createRandomMatrix(size)
        val times = measureMultiplicationTimes(m1, m2)
        allTimes[size] = times
    }
    println("Finished Trails")

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
    val yValuesMultiply = mutableListOf<Long>()
    val yValuesStrassen = mutableListOf<Long>()

    xValues.forEach { size ->
        results[size]?.let { times ->
            yValuesMultiply.add(times[0])
            yValuesStrassen.add(times[1])
        }
    }

    plotGraph(xValues, listOf(yValuesMultiply, yValuesStrassen))
}

/**
 * Plot a graph of list size vs. actual run time for the four sorting algorithms.
 * Requires XChart dependency in the build.gradle file.
 *
 * (ChatGPT assisted with function creation.)
 *
 * @param xValues A list of the list sizes run in runTrials.
 * @param yValuesList A list of two lists where each list represents the run time for one
 * of the multiplication algorithms in the order Traditional Matrix Multiplication, Strassen Matrix Multiplication.
 */
fun plotGraph(xValues: List<Int>, yValuesList: List<List<Long>>) {
    val chart = XYChartBuilder().width(800).height(600).title("Matrix Multiplication Algorithm Performance").xAxisTitle("Matrix Size").yAxisTitle("Time (ms)").build()

    // Customize Chart
    chart.styler.defaultSeriesRenderStyle = org.knowm.xchart.XYSeries.XYSeriesRenderStyle.Line
    chart.styler.isChartTitleVisible = true
    chart.styler.legendPosition = org.knowm.xchart.style.Styler.LegendPosition.InsideNW
    chart.styler.markerSize = 6

    // Convert xValues to DoubleArray as required by XChart
    val xData = xValues.map { it.toDouble() }.toDoubleArray()

    // The order of yValuesList is InsertionSort, SelectionSort, MergeSort, QuickSort
    val algorithmNames = listOf("Traditional Multiply", "Strassen Multiply")

    yValuesList.forEachIndexed { index, yValues ->
        // Convert yValues to DoubleArray
        val yData = yValues.map { it.toDouble() }.toDoubleArray()
        val series = chart.addSeries(algorithmNames[index], xData, yData)
        series.marker = SeriesMarkers.NONE
    }

    // Show it
    SwingWrapper(chart).displayChart()
}