package day6code

fun main() {
    val graph = Graph<String>()
    graph.addVertex("A")
    graph.addVertex("B")
    graph.addVertex("C")
    graph.addVertex("D")
    graph.addVertex("E")
    graph.addEdge("A", "B")
    graph.addEdge("A", "C")
    graph.addEdge("B", "D")
    graph.addEdge("B", "D")
    graph.addEdge("C", "B")
    graph.addEdge("C", "D")
    graph.addEdge("C", "E")
    println("Breadth First Search, A to E")
    println(breadthFirstSearch(graph, "A", "E"))
    println("Depth First Search, A to E")
    println(depthFirstSearch(graph, "A", "E"))

}

fun <VertexType> breadthFirstSearch(graph: Graph<VertexType>, root: VertexType, target: VertexType): Boolean {
    val markedNodes = mutableSetOf<VertexType>()
    val priorityList = mutableListOf<VertexType>()

    priorityList.add(root)
    markedNodes.add(root)

    while (priorityList.isNotEmpty()) {
        println("Priority List: $priorityList")
        val n = priorityList.removeFirst()
        println("Current Vertex (n): $n")
        if (n == target) {
            return true
        }
        val nodesConnectedToN = graph.getConnectedVertices(n)
        if (nodesConnectedToN != null) {
            for (m in nodesConnectedToN) {
                if (m !in markedNodes) {
                    priorityList.add(m)
                    markedNodes.add(m)
                }
            }
        }
    }
    return false
}

fun <VertexType> depthFirstSearch(graph: Graph<VertexType>, root: VertexType, target: VertexType): Boolean {
    val markedNodes = mutableSetOf<VertexType>()
    val priorityList = mutableListOf<VertexType>()

    priorityList.add(root)
    markedNodes.add(root)

    while (priorityList.isNotEmpty()) {
        println("Priority List: $priorityList")
        val n = priorityList.removeFirst()
        println("Current Vertex (n): $n")
        if (n == target) {
            return true
        }
        val nodesConnectedToN = graph.getConnectedVertices(n)
        if (nodesConnectedToN != null) {
            for (m in nodesConnectedToN) {
                if (m !in markedNodes) {
                    priorityList.add(0, m)
                    markedNodes.add(m)
                }
            }
        }
    }
    return false
}