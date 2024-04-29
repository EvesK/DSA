package finalProjectCode

import kotlin.math.sqrt
import kotlin.random.Random

// Data Classes for individual points and for clusters (groups of points with a centriod)
data class Point(val coordinates: List<Double>)
data class Cluster(var centroid: Point, var points: MutableList<Point> = mutableListOf())

/**
 * Calculates the Euclidean distance between two points.
 * @param p1 The first point.
 * @param p2 The second point.
 * @return The Euclidean distance between p1 and p2.
 */
fun euclideanDistance(p1: Point, p2: Point): Double {
    return sqrt(p1.coordinates.zip(p2.coordinates) { a, b -> (a - b) * (a - b)}.sum())
}

/**
 * Initializes clusters with random centroids based on the range of given points.
 * @param points The list of points from which to derive the minimum and maximum bounds for centroid initialization.
 * @param k The number of clusters to initialize, which also determines the number of centroids.
 * @return A list of clusters with randomly initialized centroids.
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
 * Performs one iteration of the clustering step in the k-means algorithm.
 * Assigns points to the nearest cluster based on Euclidean distance and updates centroids.
 * @param points The list of points to be clustered.
 * @param clusters The current list of clusters with centroids.
 * @return true if centroids have not moved significantly, indicating potential stabilization; otherwise, false.
 */
fun performClusteringStep(points: List<Point>, clusters: List<Cluster>): Boolean {
    assignPointsToClusters(clusters, points)  // Assign points to the nearest centroids
    return updateCentroids(clusters)  // Update centroids based on current assignments and return if they moved significantly
}

/**
 * Assigns each point to the closest cluster based on Euclidean distance.
 * @param clusters The clusters to which points will be assigned.
 * @param points The points to be assigned to clusters.
 */
fun assignPointsToClusters(clusters: List<Cluster>, points: List<Point>) {
    clusters.forEach { it.points.clear() }
    points.forEach { point ->
        val nearestCluster = clusters.minByOrNull { euclideanDistance(it.centroid, point) }!!
        nearestCluster.points.add(point)
    }
}

/**
 * Updates the centroid of each cluster based on the points assigned to it.
 * Determines if the centroid position has stabilized (i.e., changed very little).
 * @param clusters The clusters whose centroids need updating.
 * @return true if centroids moved more than a specified threshold, otherwise false.
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
 * Runs the k-means clustering algorithm on a set of points and returns the resulting clusters.
 * Tracks the movement of cluster centroids to determine when the algorithm has stabilized.
 * @param points The points to cluster.
 * @param k The number of clusters to form.
 * @param epsilon The minimum change in centroid position between iterations for the algorithm to continue running.
 * @param stability The number of consecutive iterations with minimal centroid changes required to consider the clustering stabilized.
 * @return A list of clusters with points assigned.
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
 * Calculates the movement of centroids after updating their positions to the average location of assigned points.
 * This method is used to track the changes in centroids during the k-means clustering to determine stabilization.
 * @param clusters The clusters whose centroids are to be updated and tracked.
 * @return A list of double values representing the movement magnitude of each centroid.
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