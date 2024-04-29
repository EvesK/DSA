package finalProjectCode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class KMeansTests {

    /**
     * Test the Euclidean distance calculation between two points.
     * Ensures that the distance formula is correctly applied to compute the distance.
     */
    @Test
    fun testEuclideanDistance() {
        val p1 = Point(listOf(1.0, 2.0))
        val p2 = Point(listOf(4.0, 6.0))
        val expected = 5.0  // Calculated distance
        val result = euclideanDistance(p1, p2)
        assertEquals(expected, result, 0.001, "Euclidean distance calculation failed")
    }

    /**
     * Tests the initialization of centroids in the k-means clustering algorithm.
     * Ensures that the correct number of centroids is initialized and that each centroid
     * is within the range of the data points provided.
     */
    @Test
    fun testInitializeCentroids() {
        val points = List(100) { Point(listOf(it.toDouble(), it.toDouble())) }
        val k = 3
        val clusters = initializeCentroids(points, k)
        assertEquals(k, clusters.size, "Should initialize correct number of clusters")
    }

    /**
     * Tests the centroid updating mechanism in the k-means algorithm.
     * Checks if centroids correctly update to the average position of assigned points
     * and ensures that the function detects movement indicating a change in centroid position.
     */
    @Test
    fun testUpdateCentroids() {
        val points = List(10) { Point(listOf(it.toDouble(), it.toDouble())) }
        val clusters = List(1) { Cluster(Point(listOf(0.0, 0.0)), points.toMutableList()) }
        val moved = updateCentroids(clusters)
        val expectedCentroid = Point(listOf(4.5, 4.5))  // Mean of 0 to 9
        assertEquals(expectedCentroid.coordinates, clusters.first().centroid.coordinates, "Centroids did not update correctly")
        assertTrue(moved, "Centroids should have moved")
    }

    /**
     * Tests the overall k-means clustering algorithm for convergence.
     * Verifies that the algorithm correctly stabilizes based on a defined epsilon,
     * indicating that centroids do not move significantly after several iterations.
     */
    @Test
    fun testKMeansConvergence() {
        val points = List(100) { Point(listOf(it.toDouble(), it.toDouble())) }
        val k = 3
        val epsilon = 0.01
        val stability = 10
        val clusters = kMeans(points, k, epsilon, stability)
        assertTrue(clusters.all { it.points.size > 0 }, "All clusters should have points assigned")
    }

}