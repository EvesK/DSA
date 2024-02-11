import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MinPriorityQueueTest {
    @Test
    fun isQueueEmpty() {
        val testQueue = MyMinPriorityQueue<String>()
        // test isEmpty on an empty queue
        assertEquals(true, testQueue.isEmpty())
        testQueue.addWithPriority("A", 32.0)
        // test isEmpty on a non-empty queue
        assertEquals(false, testQueue.isEmpty())
    }

    @Test
    fun addAndAdjustToQueue() {
        val testQueue = MyMinPriorityQueue<String>()
        testQueue.addWithPriority("A", 32.0)
        testQueue.addWithPriority("B", 12.0)
        testQueue.addWithPriority("C", 2.2)
        testQueue.addWithPriority("D", 100.3)
        // check that the queue contains element B
        assertEquals(true, testQueue.contains("B"))
        // check that C is the min element with normal add statements
        assertEquals("C", testQueue.next())
        testQueue.adjustPriority("D", 1.8)
        // check that D is now the min element after adjustment
        assertEquals("D", testQueue.next())
    }
}