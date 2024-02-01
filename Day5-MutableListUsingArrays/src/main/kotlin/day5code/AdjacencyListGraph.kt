package day5code

fun main() {
    val myAJ = AdjacencyListGraph()
    myAJ.addVertex("A")
    myAJ.addVertex("B")
    myAJ.addEdge("A", "B")
    myAJ.printGraph()
    myAJ.addEdge("B", "A")
    myAJ.printGraph()
    myAJ.addVertex("C")
    println(myAJ.getConnections("C"))
    myAJ.printGraph()
}

class AdjacencyListGraph {
    private var adjacencyList = mutableMapOf<String, MutableList<String>>()

    fun addVertex(vertex: String) {
        adjacencyList[vertex] = mutableListOf<String>()
    }

    fun addEdge(from: String, to: String) {
        if (from in adjacencyList.keys && to in adjacencyList.keys) {
            adjacencyList[from]?.also { a ->
                a.add(to)
            } ?: run {
                adjacencyList[from] = mutableListOf<String>(to)
            }
        } else {
            if (from in adjacencyList.keys && to !in adjacencyList.keys) {
                throw Exception("$to not found in adjacency list")
            } else if (from !in adjacencyList.keys && to in adjacencyList.keys) {
                throw Exception("$from not found in adjacency list")
            } else {
                throw Exception("$from and $to not found in adjacency list")
            }
        }
    }

    fun getConnections(vertex: String): MutableList<String>? {
        return adjacencyList[vertex]
    }

    fun printGraph() {
        for (vertex in adjacencyList.keys) {
            val connections = adjacencyList[vertex]?.joinToString(",")
            if (connections == "") {
                println("$vertex not connected to any vertices")
            } else {
                println("$vertex connected to $connections")
            }
        }
    }
}