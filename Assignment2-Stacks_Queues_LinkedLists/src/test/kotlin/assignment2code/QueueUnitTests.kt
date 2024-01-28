package assignment2code

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class QueueUnitTests {
    @Test
    fun peekQueueString() {
        val queue = LinkedListQueue<String>()
        queue.enqueue("five")
        val peek = queue.peek()
        assertEquals("five", peek)
    }

    @Test
    fun addToQueueString() {
        val queue = LinkedListQueue<String>()
        queue.enqueue("five")
        assertEquals("five", queue.peek())
    }

    @Test
    fun removeFromQueueString() {
        val queue = LinkedListQueue<String>()
        queue.enqueue("five")
        queue.enqueue("seven")
        val dequeue = queue.dequeue()
        assertEquals("five", dequeue)
    }

    @Test
    fun isEmptyTrueString() {
        val queue = LinkedListQueue<String>()
        assertEquals(true, queue.isEmpty())
    }

    @Test
    fun isEmptyFalseString() {
        val queue = LinkedListQueue<String>()
        queue.enqueue("five")
        queue.enqueue("seven")
        assertEquals(false, queue.isEmpty())
    }
}
