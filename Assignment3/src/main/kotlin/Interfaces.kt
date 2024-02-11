/**
 * This ``Graph`` that represents a directed graph
 * @param VertexType the representation of a vertex in the graph
 */
interface Graph<VertexType> {
    /**
     * Add a vertex [vertex] to the graph
     * @param vertex the vertex to add to the graph
     */
    fun addVertex(vertex: VertexType)

    /**
     * Get the set of all vertices in the graph
     * @return the vertices in the graph as a set
     */
    fun getVertices(): Set<VertexType>

    /**
     * Add an edge to the graph
     * @param from the vertex that the edge originates from
     * @param to the vertex that the edge terminates at
     * @cost the cost of the edge
     */
    fun addEdge(from: VertexType, to: VertexType, cost: Double)

    /**
     *  Get the map of all edges and their cost originating from the [from] vertex
     *  @param from the vertex from which the edges are originating
     *  @return a map of the vertices that the [from] vertex is connected to and the cost of the edge between the [from] vertex and that vertex
     */
    fun getEdges(from: VertexType): Map<VertexType, Double>

    /**
     * Remove all edges and vertices from the graph.
     */
    fun clear()
}

/**
 * ``MinPriorityQueue`` maintains a priority queue where the lower
 *  the priority value, the sooner the element will be removed from
 *  the queue.
 *  @param T the representation of the items in the queue
 */
interface MinPriorityQueue<T> {
    /**
     * @return true if the queue is empty, false otherwise
     */
    fun isEmpty(): Boolean

    /**
     * Add [elem] at level [priority]
     * @param elem to add to the queue
     * @param priority level at which to add the element
     */
    fun addWithPriority(elem: T, priority: Double)

    /**
     * Get the next (lowest priority) element.
     * @return the next element in terms of priority.  If empty, return null.
     */
    fun next(): T?

    /**
     * Adjust the priority of the given element
     * @param elem whose priority should change
     * @param newPriority the priority to use for the element
     *   the lower the priority the earlier the element int
     *   the order.
     */
    fun adjustPriority(elem: T, newPriority: Double)
}