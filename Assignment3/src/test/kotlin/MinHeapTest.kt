import MinHeap
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MinHeapTest {

    @Test
    fun insert() {
        val heap = MinHeap<String>()
        heap.insert(data="test", heapNumber=3.2)
        heap.insert(data="booh", heapNumber=5.3)
        heap.insert(data="claw", heapNumber=1.0)
        heap.insert(data="tester", heapNumber=0.2)
        heap.insert(data="qq", heapNumber=0.5)
        heap.insert(data="anotherone", heapNumber=100.0)
        assertFalse(heap.insert(data="qq", heapNumber=40.0))
        assertEquals(heap.getMin(), "tester")
        assertEquals(heap.getMin(), "qq")
        assertEquals(heap.getMin(), "claw")
        assertEquals(heap.getMin(), "test")
        assertEquals(heap.getMin(), "booh")
        heap.insert(data="reentry", heapNumber=1000.0)
        assertEquals(heap.getMin(), "anotherone")
        assertEquals(heap.getMin(), "reentry")
        assertEquals(heap.getMin(), null)
    }

    @Test
    fun adjustPriority() {
        val heap = MinHeap<String>()
        heap.insert(data="test", heapNumber=3.2)
        heap.insert(data="booh", heapNumber=5.3)
        heap.insert(data="claw", heapNumber=1.0)
        heap.insert(data="tester", heapNumber=0.2)
        heap.adjustHeapNumber("booh", 0.0)
        assertEquals(heap.getMin(), "booh")

    }

    @Test
    fun testInfiniteValues() {
        val heap = MinHeap<String>()
        heap.insert("test", 2.0)
        heap.insert("q", Double.POSITIVE_INFINITY)
        assertEquals(heap.getMin(), "test")
        heap.adjustHeapNumber("q", 4.0)
        heap.insert("w", 10.0)
        assertEquals(heap.getMin(), "q")
    }

    @Test
    fun isEmpty() {
        val heap = MinHeap<String>()
        assertTrue(heap.isEmpty())
        heap.insert("test", 30.0)
        assertFalse(heap.isEmpty())
        heap.getMin()
        assertTrue(heap.isEmpty())
    }
}