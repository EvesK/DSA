package finalProjectCode

import kotlin.math.sqrt
import kotlin.random.Random

// Data Classes for individual points and for clusters (groups of points with a centriod)
data class Point(val coordinates: List<Double>)
data class Cluster(var centroid: Point, var points: MutableList<Point> = mutableListOf())

/**
 * Calculates the Euclidean distance between two points.
 */
fun euclideanDistance(p1: Point, p2: Point): Double {
    return sqrt(p1.coordinates.zip(p2.coordinates) { a, b -> (a - b) * (a - b)}.sum())
}

/**
 * Initializes clusters with random centroids from a list of points.
 */
fun initializeCentroids(points: List<Point>, k: Int): List<Cluster> {
    val centroids = mutableListOf<Point>()
    val random = Random(System.currentTimeMillis())
    val dimensions = points.first().coordinates.size
    for (i in 1..k) {
        val coordinates = List(dimensions) { index ->
            val (min, max) = points.minOf { it.coordinates[index]} to points.maxOf { it.coordinates[index]}
                random.nextDouble(min, max)
        }
        centroids.add(Point(coordinates))
    }
    return centroids.map { Cluster(it) }
}

/**
 * Performs the clustering step that occurs for every iteration of the k-means algorithm.
 * Assigns points to clusters and updates the centroid of each cluster. Returns true if the
 * centroids have stabilized, else false.
 */
fun performClusteringStep(points: List<Point>, clusters: List<Cluster>): Boolean {
    assignPointsToClusters(clusters, points)  // Assign points to the nearest centroids
    return updateCentroids(clusters)  // Update centroids based on current assignments and return if they moved significantly
}

/**
 * Assigns points to clusters based on Euclidean distance between the point and the cluster
 * centroid.
 */
fun assignPointsToClusters(clusters: List<Cluster>, points: List<Point>) {
    clusters.forEach { it.points.clear() }
    points.forEach { point ->
        val nearestCluster = clusters.minByOrNull { euclideanDistance(it.centroid, point) }!!
        nearestCluster.points.add(point)
    }
}

/**
 * Updates the centroid of each cluster by taking the average of all of the points in the cluster.
 * Returns true if the centroids have stabilized, else false.
 */
fun updateCentroids(clusters: List<Cluster>): Boolean {
    var moved = false
    val epsilon = 0.0001  // Movement threshold for stabilization
    clusters.forEach { cluster ->
        val oldCentroid = Point(cluster.centroid.coordinates)
        val newCentroidCoordinates = List(cluster.centroid.coordinates.size) { dim ->
            cluster.points.map { it.coordinates[dim] }.average()
        }
        val newCentroid = Point(newCentroidCoordinates)
        val movement = euclideanDistance(oldCentroid, newCentroid)
        if (movement > epsilon) moved = true
        cluster.centroid = newCentroid
    }
    return moved
}

/**
 * Runs the k-means algorithm. Tracks the movement of cluster centroids to determine
 * when the algorithm has stabilized, i.e. when the cluster centroids have not moved
 * for [stability] iterations.
 */
fun kMeans(points: List<Point>, k: Int, epsilon: Double = 0.01, stability: Int = 10): List<Cluster> {
    val clusters = initializeCentroids(points, k)
    var movements = MutableList(k) { Double.MAX_VALUE }  // Track movements for each centroid
    var stableIterations = 0  // Count of consecutive stable iterations

    while (stableIterations < stability) {
        assignPointsToClusters(clusters, points)
        val newMovements = updateCentroidsAndGetMovements(clusters)

        // Check if all centroids moved less than epsilon
        if (newMovements.all { it < epsilon }) {
            stableIterations++
        } else {
            stableIterations = 0  // Reset if any centroid moved more than epsilon
        }

        movements = newMovements
    }

    return clusters
}

/**
 * Calls the updateCentroid function and tracks the movement of the centroids.
 * This movement is used to determine if the centroids have stabilized in the
 * k-means function.
 */
fun updateCentroidsAndGetMovements(clusters: List<Cluster>): MutableList<Double> {
    return clusters.map { cluster ->
        val oldCentroid = cluster.centroid
        val newCentroidCoordinates = List(cluster.centroid.coordinates.size) { dim ->
            cluster.points.map { it.coordinates[dim] }.average()
        }
        val newCentroid = Point(newCentroidCoordinates)
        val movement = euclideanDistance(oldCentroid, newCentroid)
        cluster.centroid = newCentroid
        movement
    }.toMutableList()
}