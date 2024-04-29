package finalProjectCode

import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.XChartPanel
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.markers.SeriesMarkers
import javax.swing.JFrame
import kotlin.random.Random

// Colors for the clusters, up to 7 clusters per graph
val XChartSeriesColors = arrayOf(
    java.awt.Color(255, 100, 100),
    java.awt.Color(100, 255, 100),
    java.awt.Color(100, 100, 255),
    java.awt.Color(200, 200, 100),
    java.awt.Color(100, 200, 200),
    java.awt.Color(200, 100, 200),
    java.awt.Color(90, 90, 90)
)

/**
 * Entry point of the application which initializes and runs the k-means clustering.
 * Adjust `numPoints` and `clustered` to generate different distributions of graphs,
 * and adjust `k` to specify the number of clusters.
 */
fun main() {
    // Adjust numPoints and clustered to generate different graphs.
    val points = generateRandomPoints(200, clustered = true)
    // Adjust k to specify the number of clusters.
    val clusters = kMeans(points, 3)
    plotClusters(clusters)
}

/**
 * Plots the given clusters on a graph, displays the graph in a pop-up window, and saves it to a file.
 * @param clusters The list of clusters to plot.
 * @param fileName The name of the file where the graph is saved. Default is "KMeans_Clustering".
 */
fun plotClusters(clusters: List<Cluster>, fileName: String = "KMeans_Clustering") {
    val chart = XYChartBuilder().width(800).height(600).title("K-Means Clustering").build()

    // Customize Chart Style
    chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    chart.styler.isChartTitleVisible = true
    chart.styler.chartTitlePadding = 10
    chart.styler.isLegendVisible = false  // Hide the legend
    chart.styler.markerSize = 6  // Global marker size for all points

    // Plotting clusters and centroids
    clusters.forEachIndexed { index, cluster ->
        if (cluster.points.isNotEmpty()) {
            // Plot clusters
            val xs = cluster.points.map { it.coordinates[0] }.toDoubleArray()
            val ys = cluster.points.map { it.coordinates[1] }.toDoubleArray()
            val series = chart.addSeries("Cluster $index", xs, ys)
            series.marker = SeriesMarkers.CIRCLE
            series.markerColor = XChartSeriesColors[index % XChartSeriesColors.size]

            // Plot centroids
            val centroidX = cluster.centroid.coordinates[0]
            val centroidY = cluster.centroid.coordinates[1]
            val centroidSeries = chart.addSeries("Centroid $index", doubleArrayOf(centroidX), doubleArrayOf(centroidY))
            centroidSeries.marker = SeriesMarkers.CROSS
            centroidSeries.markerColor = XChartSeriesColors[index % XChartSeriesColors.size]

        } else {
            println("Warning: Cluster $index is empty and will not be plotted.")
        }
    }

    // Save it
    BitmapEncoder.saveBitmap(chart, "./src/main/kotlin/finalProjectCode/$fileName", BitmapEncoder.BitmapFormat.PNG)

    // Display the chart
    val frame = JFrame("K-Means Clustering")
    frame.contentPane = XChartPanel(chart)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.pack()
    frame.isVisible = true
}

/**
 * Generates a set of points either randomly or clustered based on the input.
 * @param numPoints The number of points to generate.
 * @param clustered Whether the points should be generated with intentional clustering.
 * @return A list of Points either randomly distributed or clustered.
 */
fun generateRandomPoints(numPoints: Int, clustered: Boolean): List<Point> {
    val points = mutableListOf<Point>()
    val random = Random(System.currentTimeMillis())

    if (clustered) {
        // Define number of clusters (randomly between 2 and 5 if clustered)
        val numClusters = random.nextInt(2, 6)
        val clusterCenters = List(numClusters) {
            Point(listOf(random.nextDouble(0.0, 50.0), random.nextDouble(0.0, 50.0)))
        }
        val clusterSpread = 5.0  // Spread factor around the cluster center

        for (i in 0 until numPoints) {
            val center = clusterCenters[random.nextInt(numClusters)]
            val coordinates = center.coordinates.map { it + random.nextDouble(-clusterSpread, clusterSpread) }
            points.add(Point(coordinates))
        }
    } else {
        // Uniformly distributed points
        for (i in 0 until numPoints) {
            val coordinates = listOf(random.nextDouble(0.0, 100.0), random.nextDouble(0.0, 100.0))
            points.add(Point(coordinates))
        }
    }

    return points
}