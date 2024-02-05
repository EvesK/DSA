package day6code

fun main() {
    val mygraph = Graph<String>()
    mygraph.addVertex("A")
    mygraph.addVertex("B")
    mygraph.addVertex("C")
    mygraph.addVertex("D")
    mygraph.addVertex("E")
    mygraph.addEdge("A", "B")
    mygraph.addEdge("B", "A")
    mygraph.addEdge("B", "C")
    mygraph.addEdge("C", "B")
    mygraph.addEdge("C", "D")
    mygraph.addEdge("D", "C")
    mygraph.addEdge("D", "E")
    mygraph.addEdge("E", "D")
    print(numberOfConnectedComponents(mygraph))
}

fun <VertexType> numberOfConnectedComponents(graph: Graph<VertexType>): Int {
    val nodesNotInASet = graph.allVertices()
    var numberOfSets = 0

    if (nodesNotInASet != null) {
        while (nodesNotInASet.size > 0) {
            val randomNode = nodesNotInASet.random()
            val connected = connectedNodes(graph, randomNode)
            nodesNotInASet.removeAll(connected)
            numberOfSets++
        }
    }

    return numberOfSets
}

fun <VertexType> connectedNodes(graph: Graph<VertexType>, node: VertexType): MutableSet<VertexType> {
    val markedNodes = mutableSetOf<VertexType>()
    val priorityList = mutableListOf<VertexType>()

    priorityList.add(node)
    markedNodes.add(node)

    while (priorityList.isNotEmpty()) {
        println("Priority List: $priorityList")
        val n = priorityList.removeFirst()
        println("Current Vertex (n): $n")
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
    return markedNodes
}