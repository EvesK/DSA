package assignment2code

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StackUnitTests {
    @Test
    fun peekStackInt() {
        val stack = LinkedListStack<Int>()
        stack.push(5)
        val peek = stack.peek()
        assertEquals(5, peek)
    }

    @Test
    fun addToStackInt() {
        val stack = LinkedListStack<Int>()
        stack.push(5)
        assertEquals(5, stack.peek())
    }

    @Test
    fun removeFromStackInt() {
        val stack = LinkedListStack<Int>()
        stack.push(5)
        stack.push(7)
        val pop = stack.pop()
        assertEquals(7, pop)
    }

    @Test
    fun isEmptyTrueInt() {
        val stack = LinkedListStack<Int>()
        assertEquals(true, stack.isEmpty())
    }

    @Test
    fun isEmptyFalseInt() {
        val stack = LinkedListStack<Int>()
        stack.push(5)
        stack.push(7)
        assertEquals(false, stack.isEmpty())
    }
}