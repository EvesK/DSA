import java.io.File // for reading the matrix80 text file

// TODO: See Interfaces.kt for function documentation for MyGraph and MyMinPriorityQueue

fun main() {
    println("Example Graph Class")
    val testGraph = MyGraph<String>()
    testGraph.addVertex("A")
    testGraph.addVertex("B")
    testGraph.addVertex("C")
    testGraph.addVertex("D")
    testGraph.addVertex("E")
    testGraph.addEdge("A", "B", 12.0)
    testGraph.addEdge("B", "C", 13.0)
    testGraph.addEdge("B", "D", 4.0)
    testGraph.addEdge("C", "D", 1.0)
    testGraph.addEdge("D", "E", 24.0)
    println("All vertices in graph: ${testGraph.getVertices()}")
    println("Edges from vertex B in graph: ${testGraph.getEdges("B")}")

    println("Example Queue Class")
    val testQueue = MyMinPriorityQueue<String>()
    testQueue.addWithPriority("A", 32.0)
    testQueue.addWithPriority("B", 12.0)
    testQueue.addWithPriority("C", 2.2)
    testQueue.addWithPriority("D", 100.3)
    println("Next: ${testQueue.next()}")
    println("Next: ${testQueue.next()}")
    testQueue.adjustPriority("D", 1.8)
    println("Next: ${testQueue.next()}")

    println("Example Dijkstra's Function")
    println(Dijkstra(testGraph, "A", "E"))

    println("Actual Problem 81 from Project Euler")
    val matrix80 = readMatrixFromFile("src/main/kotlin/matrix80.txt")
    println("Problem 81 on 80x80 matrix: ${Problem81(matrix80)}")
}

class MyGraph<VertexType>: Graph<VertexType> {
    private var vertices: MutableSet<VertexType> = mutableSetOf()
    private var edges: MutableMap<VertexType, MutableMap<VertexType, Double>> = mutableMapOf()

    override fun addVertex(vertex: VertexType) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex)
        } else {
            throw Exception("Vertex $vertex already exists.")
        }
    }
    override fun getVertices(): Set<VertexType> {
        if (vertices.isNotEmpty()) {
            return vertices
        } else {
            throw Exception("No vertices in the graph.")
        }
    }

    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw Exception("Vertex $from or vertex $to not in graph.")
        }
        edges[from]?.also { currentAdjacent ->
            currentAdjacent[to] = cost
        } ?: run {
            edges[from] = mutableMapOf(to to cost)
        }
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        if (vertices.contains(from)) {
            return edges[from]?.also {a -> a} ?: run {mapOf()}
        } else {
            throw Exception("Vertex $from not in graph.")
        }
    }

    override fun clear() {
        vertices = mutableSetOf()
        edges = mutableMapOf()
    }
}

class MyMinPriorityQueue<T>: MinPriorityQueue<T> {
    private val heap = MinHeap<T>()
    private val elementsSet = mutableSetOf<T>()
    override fun isEmpty(): Boolean {
        return heap.isEmpty()
    }
    override fun addWithPriority(elem: T, priority: Double) {
        heap.insert(elem, priority)
        elementsSet.add(elem)
    }
    override fun next(): T? {
        return heap.getMin()
    }
    override fun adjustPriority(elem: T, newPriority: Double) {
        heap.adjustHeapNumber(elem, newPriority)
    }

    /**
     * Checks if an element is already in the queue.
     *
     * @param elem The element to check
     * @return true if the element is already in the queue, false otherwise
     */
    fun contains(elem: T): Boolean {
        return (elem in elementsSet)
    }
}

/**
 * Given a graph and a starting vertex, apply dijkstra's algorithm to find the distance
 * from the starting vertex to all other vertices in the graph. Function assumes that
 * the graph contains the start and end vertex.
 *
 * @param graph The graph to investigate
 * @param startVertex The vertex to start the path at for the algorithm
 * @return A map of VertexType: Double that maps each vertex connected to the start vertex
 * in the graph to the cost needed to reach that vertex from the start vertex.
 */
fun <VertexType> Dijkstra(graph: MyGraph<VertexType>, startVertex: VertexType, endVertex: VertexType) : List<VertexType>? {
    // initialize priority queue, distances map, and previous map
    val priorityQueue = MyMinPriorityQueue<VertexType>()
    val distances = mutableMapOf<VertexType, Double>().withDefault {Double.MAX_VALUE} // initialize distances with infinity
    val previous = mutableMapOf<VertexType, VertexType?>()
    priorityQueue.addWithPriority(startVertex, 0.0) // add the start vertex to the priority queue with the min priority
    distances[startVertex] = 0.0 // initialize the start vertex to 0 in distances
    previous[startVertex] = null // the start vertex has no previous vertex

    // run through the graph until we run out of vertices to search, i.e. priority queue is empty
    while (!priorityQueue.isEmpty()) {
        // get the current vertex by popping the minimum value from the priority queue. if the queue is empty, break
        val currentVertex = priorityQueue.next() ?: break
        // check if the current vertex is the end vertex we are looking for; if so, break
        if (currentVertex == endVertex) break
        // for each edge connected to the current vertex...
        graph.getEdges(currentVertex).forEach { (neighbor, weight) ->
            val distanceUsingCurrent = distances.getValue(currentVertex) + weight
            // check if the path through the current vertex is better than any previously established path (or better than infinity if
            // no previously established path)
            if (distanceUsingCurrent < distances.getValue(neighbor)) {
                // replace the previous path/infinity with the new path
                distances[neighbor] = distanceUsingCurrent
                previous[neighbor] = currentVertex
                // check if the vertex whose weight we are adjusting is already in the priority queue
                if (priorityQueue.contains(neighbor)) {
                    // if so, i.e. if we had previously found a worse path from the start vertex to this vertex, then adjust the priority
                    // to reflect the new, better path we found
                    priorityQueue.adjustPriority(neighbor, distanceUsingCurrent)
                } else {
                    // if not, i.e. we did not previously have a path to this vertex, then add the vertex to the priority queue with the
                    // path we found
                    priorityQueue.addWithPriority(neighbor, distanceUsingCurrent)
                }
            }
        }
    }

    // reconstruct the shortest path from the previous map by working backwards from the end vertex
    // initialize a path list and a current vertex
    val path = mutableListOf<VertexType>()
    var current: VertexType? = endVertex

    // while the current vertex exists
    while (current != null) {
        // add it to the path
        path.add(current)
        // go to the previous vertex
        current = previous[current]
    }

    // if the reconstructed path does not terminate in the start vertex then there is no path between the
    // start and end vertex, return null
    if (path.last() != startVertex) {
        return null
    }

    // reverse the path so that it reads from the start vertex to the end vertex
    path.reverse()

    // return the path
    return path
}

/**
 * Given a square matrix find the minimal path sum from the top left to the bottom right element
 * by only moving to the right and down through the graph. See https://projecteuler.net/problem=81
 * for more information on the problem.
 *
 * @param matrix The matrix to transverse
 * @return The sum from transversing the shortest path
 */
fun Problem81(matrix: List<List<Int>>): Double {
    val graph = MyGraph<triple>()
    val m = matrix.size - 1// Number of rows = number of columns since assuming a square matrix

    // add all elements of the matrix to the graph
    for (i in 0..m) { // Loop through rows
        for (j in 0..m) { // Loop through columns/elements in a row
            graph.addVertex(triple(i, j, matrix[i][j].toDouble())) // add each element to the graph
        }
    }

    // add edges between each element (i, j) and the elements directly the right (i + 1, j) and down (i, j + 1)
    for (i in 0..(m - 1)) { // Loop through rows to the second to last row
        for (j in 0..(m - 1)) { // Loop through columns/elements in a row to the second to last element
            val currentVertex = triple(i, j, matrix[i][j].toDouble())
            val rightVertex = triple((i+1), j, matrix[i+1][j].toDouble())
            val downVertex = triple(i, (j+1), matrix[i][j+1].toDouble())
            graph.addEdge(currentVertex, rightVertex, (matrix[i+1][j].toDouble()))
            graph.addEdge(currentVertex, downVertex, (matrix[i][j+1].toDouble()))
        }
    }
    // add the edges to the bottom right element of the matrix manually
    graph.addEdge(triple((m - 1), m, matrix[m-1][m].toDouble()), triple(m, m, matrix[m][m].toDouble()), matrix[m][m].toDouble())
    graph.addEdge(triple(m, (m-1), matrix[m][m-1].toDouble()), triple(m, m, matrix[m][m].toDouble()), matrix[m][m].toDouble())


    // initialize start and end vertices of the matrix
    val start = triple(0, 0, matrix[0][0].toDouble())
    val end = triple(m, m, matrix[m][m].toDouble())
    // run dijkstra's to get the shortest path
    val shortestPath = Dijkstra(graph, start, end)

    // initialize the summation to a double equal to 0
    var sum = 0.0
    // check that the shortestPath exists
    if (shortestPath != null) {
        // for each element in the path, add the value of the element to our summation
        for (elem in shortestPath) {
            sum += elem.value
//            println("Elem at ${elem.i}, ${elem.j} with value ${elem.value}. Current summation = ${sum}.")
        }
    } else {
        throw Exception("Something went wrong or graph is empty. No path between top and bottom of matrix.")
    }

    return sum
}

// a data class used in the problem 81 function to store each matrix element as a vertex
data class triple(val i: Int, val j: Int, val value: Double)

/**
 * Convert the matrix from the text file matrix80.txt, retrieved from https://projecteuler.net/problem=81,
 * into a list of lists.
 *
 * @param filePath The location of the text file
 * @return The matrix in the form of a list of lists
 */
fun readMatrixFromFile(filePath: String): List<List<Int>> {
    val matrix = mutableListOf<List<Int>>()
    File(filePath).forEachLine { line ->
        val row = line.split(",").map { it.trim().toInt() } // Split each line by comma and convert to integers
        matrix.add(row)
    }
    return matrix
}