import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class GraphTest {
    @Test
    fun addVerticesAndGetVertices() {
        val testGraph = MyGraph<String>()
        testGraph.addVertex("A")
        // create one vertex
        assertEquals(setOf("A"), testGraph.getVertices())
        testGraph.addVertex("B")
        // create two vertices
        assertEquals(setOf("A", "B"), testGraph.getVertices())
        // check that trying to add a vertex that already exists throws the appropriate exception
        val exception = assertThrows(Exception::class.java) {
            testGraph.addVertex("A")
        }
        assertEquals("Vertex A already exists.", exception.message) // Assert the message of the exception
        testGraph.clear()
        // check that trying to get vertices from an empty graph throws the appropriate exception
        val exception2 = assertThrows(Exception::class.java) {
            testGraph.getVertices()
        }
        assertEquals("No vertices in the graph.", exception2.message) // Assert the message of the exception
    }

    @Test
    fun addEdgesAndGetEdges() {
        val testGraph = MyGraph<String>()
        testGraph.addVertex("A")
        testGraph.addVertex("B")
        testGraph.addVertex("C")
        // create one edge
        testGraph.addEdge("A", "B", 10.0)
        assertEquals(mapOf("B" to 10.0), testGraph.getEdges("A"))
        // change value of edge cost
        testGraph.addEdge("A", "B", 12.0)
        assertEquals(mapOf("B" to 12.0), testGraph.getEdges("A"))
        // create two edges
        testGraph.addEdge("A", "C", 2.4)
        assertEquals(mapOf("B" to 12.0, "C" to 2.4), testGraph.getEdges("A"))
        // check that trying to get edges from an existing vertex with no edges returns an empty map
        assertEquals(mapOf(), testGraph.getEdges("B"))
        // check that trying to add an edge when one vertex doesn't exist throws the appropriate exception
        val exception = assertThrows(Exception::class.java) {
            testGraph.addEdge("A", "X", 1.0)
        }
        assertEquals("Vertex A or vertex X not in graph.", exception.message) // Assert the message of the exception
        // check that trying to get edges from a vertex not in the graph throws the appropriate exception
        val exception2 = assertThrows(Exception::class.java) {
            testGraph.getEdges("X")
        }
        assertEquals("Vertex X not in graph.", exception2.message) // Assert the message of the exception
    }
}