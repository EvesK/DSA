package finalProjectCode

import javax.swing.*
import java.awt.BorderLayout
import javax.swing.JFrame

import org.knowm.xchart.*
import org.knowm.xchart.XYChart
import org.knowm.xchart.XChartPanel
import org.knowm.xchart.style.markers.SeriesMarkers


var isPaused = true
lateinit var chart: XYChart
lateinit var panel: XChartPanel<XYChart>

fun main() {
    SwingUtilities.invokeLater {
        // Set up the main frame and chart panel
        val chart = XYChartBuilder().width(800).height(600).build().apply {
            styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
            styler.isChartTitleVisible = true
            styler.chartTitlePadding = 10
            styler.isLegendVisible = false
            styler.markerSize = 6
        }
        val panel = XChartPanel(chart)
        val frame = JFrame("K-Means Clustering Demo").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            layout = BorderLayout()
            add(panel, BorderLayout.CENTER)
            setSize(800, 600)
            isVisible = true
        }

        // Input dialog for initial point configuration
        val userInputPanel = JPanel().apply {
            add(JLabel("Number of Points:"))
            val pointsField = JTextField("200", 5)  // Default number of points is 200
            add(pointsField)
            add(JCheckBox("Clustered").apply { isSelected = false })
        }

        val pointsOption = JOptionPane.showConfirmDialog(null, userInputPanel, "Enter Points Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)
        if (pointsOption != JOptionPane.OK_OPTION) {
            frame.dispose()
            return@invokeLater
        }

        val numPoints = (userInputPanel.getComponent(1) as JTextField).text.toInt()
        val clustered = (userInputPanel.getComponent(2) as JCheckBox).isSelected
        val points = generateRandomPoints(numPoints, clustered)

        // Display initial points on the existing chart
        displayPoints(points, chart, panel)

        // Delay the cluster number input to allow initial points display
        SwingUtilities.invokeLater {
            val kValueString = JOptionPane.showInputDialog(frame, "Enter Number of Clusters (K):", "3")  // Default k is 3
            val kValue = kValueString?.toIntOrNull() ?: 3  // Use 3 as default if input is invalid or canceled
            val clusters = initializeCentroids(points, kValue)

            // Setup UI controls and start clustering in the same window
            setupUI(points, clusters, panel, frame, chart)
        }
    }
}

fun displayPoints(points: List<Point>, chart: XYChart, panel: XChartPanel<XYChart>) {
    val xs = points.map { it.coordinates[0] }.toDoubleArray()
    val ys = points.map { it.coordinates[1] }.toDoubleArray()
    chart.addSeries("All Points", xs, ys).apply {
        marker = SeriesMarkers.CIRCLE
        markerColor = java.awt.Color.BLACK
    }
    panel.revalidate()
    panel.repaint()
}


fun setupUI(points: List<Point>, clusters: List<Cluster>, panel: XChartPanel<XYChart>, frame: JFrame, chart: XYChart) {
    val controls = JPanel()
    val actionButton = JButton("Run").apply {
        background = java.awt.Color.GREEN  // Start with a green color indicating "go"
    }

    actionButton.addActionListener {
        when (actionButton.text) {
            "Run" -> {
                actionButton.text = "Pause"  // Change to "Pause" once the clustering starts
                actionButton.background = java.awt.Color.RED  // Red to indicate active running
                isPaused = false
            }
            "Pause" -> {
                actionButton.text = "Continue"
                actionButton.background = java.awt.Color.ORANGE  // Orange to indicate paused but not stopped
                isPaused = true
            }
            "Continue" -> {
                actionButton.text = "Pause"
                actionButton.background = java.awt.Color.RED  // Red to indicate active running
                isPaused = false
            }
            "Finished" -> {
            }
        }

        val timer = Timer(1000) {
            if (!isPaused && actionButton.text != "Finished") {
                val continueRunning = performClusteringStep(points, clusters)
                plotClustersInteractive(clusters, chart, panel)
                if (!continueRunning) {
                    actionButton.text = "Finished"
                    actionButton.background = java.awt.Color.WHITE  // Gray to indicate completion
                    actionButton.isEnabled = false  // Disable the button after finishing
                    (it.source as Timer).stop()  // Stop the timer if centroids have stabilized
                }
            } else {
                (it.source as Timer).stop()  // Stop the timer when paused or finished
            }
        }
        timer.start()
    }

    controls.add(actionButton)
    frame.add(controls, BorderLayout.SOUTH)
    frame.pack()
    frame.revalidate()  // Make sure the frame updates with new button statuses
    frame.repaint()
}


/**
 * Create the initial blank graph.
 */
fun createChart(): XYChart {
    return XYChartBuilder().width(800).height(600).title("K-Means Clustering").build().apply {
        styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
        styler.isChartTitleVisible = true
        styler.chartTitlePadding = 10
        styler.isLegendVisible = false
        styler.markerSize = 6
    }
}

/**
 * Plot the points in black before any clustering begins.
 */
fun plotInitialPoints(points: List<Point>) {
    val xs = points.map { it.coordinates[0] }.toDoubleArray()
    val ys = points.map { it.coordinates[1] }.toDoubleArray()
    chart.addSeries("All Points", xs, ys).apply {
        marker = SeriesMarkers.CIRCLE
        markerColor = java.awt.Color.BLACK
        isShowInLegend = false
    }
    panel.revalidate()
    panel.repaint()
}

/**
 * Draw each step of the k-means algorithm on the UI panel.
 */
fun plotClustersInteractive(clusters: List<Cluster>, chart: XYChart, panel: XChartPanel<XYChart>) {
    SwingUtilities.invokeLater {
        clusters.forEachIndexed { index, cluster ->
            val clusterSeriesName = "Cluster $index"
            val centroidSeriesName = "Centroid $index"

            // Set up x and y values for clusters and centroids.
            val xs = cluster.points.map { it.coordinates[0] }.toDoubleArray()
            val ys = cluster.points.map { it.coordinates[1] }.toDoubleArray()
            val centroidXs = doubleArrayOf(cluster.centroid.coordinates[0])
            val centroidYs = doubleArrayOf(cluster.centroid.coordinates[1])

            // Plot each cluster.
            if (chart.seriesMap.containsKey(clusterSeriesName)) {
                chart.updateXYSeries(clusterSeriesName, xs, ys, null)
            } else {
                val series = chart.addSeries(clusterSeriesName, xs, ys)
                series.marker = SeriesMarkers.CIRCLE
                series.markerColor = XChartSeriesColors[index % XChartSeriesColors.size]
            }

            // Plot each centroid.
            if (chart.seriesMap.containsKey(centroidSeriesName)) {
                chart.updateXYSeries(centroidSeriesName, centroidXs, centroidYs, null)
            } else {
                val centroidSeries = chart.addSeries(centroidSeriesName, centroidXs, centroidYs)
                centroidSeries.marker = SeriesMarkers.CROSS
                centroidSeries.markerColor = XChartSeriesColors[index % XChartSeriesColors.size]
            }
        }

        // Update drawing.
        panel.revalidate()
        panel.repaint()
    }
}
