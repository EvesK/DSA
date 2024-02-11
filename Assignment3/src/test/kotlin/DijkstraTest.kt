import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DijkstraTest {
    @Test
    fun fullyConnectedGraph() {
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
        // check that the shortest path is found from one option
        assertEquals(listOf("A", "B", "C"), Dijkstra(testGraph, "A", "C"))
        // check that the shortest path is found from multiple options
        assertEquals(listOf("A", "B", "D", "E"), Dijkstra(testGraph, "A", "E"))
    }

    @Test
    fun graphWithMultipleComponents() {
        val testGraph = MyGraph<String>()
        testGraph.addVertex("A")
        testGraph.addVertex("B")
        testGraph.addVertex("C")
        testGraph.addVertex("D")
        testGraph.addVertex("E")
        testGraph.addEdge("A", "B", 12.0)
        testGraph.addEdge("D", "C", 4.0)
        testGraph.addEdge("C", "D", 1.0)
        testGraph.addEdge("D", "E", 24.0)
        // check first component
        assertEquals(listOf("A", "B"), Dijkstra(testGraph, "A", "B"))
        // check second component
        assertEquals(listOf("D", "C"), Dijkstra(testGraph, "D", "C"))
        // check that we can't find a path between components because none exists
        assertEquals(null, Dijkstra(testGraph, "A", "E"))
    }
}