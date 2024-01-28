package assignment2code

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DoublyLinkedListUnitTests {

    @Test
    fun peakHeadInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushFront(3)
        val peek = linkedList.peekFront()
        assertEquals(3, peek)
    }

    @Test
    fun peakHeadStr() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushFront("three")
        val peek = linkedList.peekFront()
        assertEquals("three", peek)
    }

    @Test
    fun peakBackInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushBack(3)
        val peek = linkedList.peekBack()
        assertEquals(3, peek)
    }

    @Test
    fun peakBackString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushBack("three")
        val peek = linkedList.peekBack()
        assertEquals("three", peek)
    }

    @Test
    fun addHeadInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushFront(3)
        assertEquals(3, linkedList.peekFront())
    }

    @Test
    fun addHeadString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushFront("three")
        assertEquals("three", linkedList.peekFront())
    }

    @Test
    fun addTailInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushBack(5)
        assertEquals(5, linkedList.peekBack())
    }

    @Test
    fun addTailString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushBack("five")
        assertEquals("five", linkedList.peekBack())
    }

    @Test
    fun popHeadInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushFront(3)
        linkedList.pushFront(5)
        linkedList.pushFront(7)

        // list should be setup to be: head -> 7, 5, 3 <- tail

        val head = linkedList.popFront()
        assertEquals(7, head) // check the variable head was set to the previous head
        assertEquals(5, linkedList.peekFront()) // check that the new head is the next node
    }

    @Test
    fun popHeadString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushFront("three")
        linkedList.pushFront("five")
        linkedList.pushFront("seven")

        // list should be setup to be: head -> "three", "five", "seven" <- tail

        val head = linkedList.popFront()
        assertEquals("seven", head) // check the variable head was set to the previous head
        assertEquals("five", linkedList.peekFront()) // check that the new head is the next node
    }

    @Test
    fun popTailInt() {
        val linkedList = DoublyLinkedList<Int>()
        linkedList.pushFront(3)
        linkedList.pushFront(5)
        linkedList.pushFront(7)

        // list should be setup to be: head -> 7, 5, 3 <- tail

        val tail = linkedList.popBack()
        assertEquals(3, tail) // check the variable tail was set to the previous tail
        assertEquals(5, linkedList.peekBack()) // check that the new tail is the prev node
    }

    @Test
    fun popTailString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushFront("three")
        linkedList.pushFront("five")
        linkedList.pushFront("seven")

        // list should be setup to be: head -> "seven", "five", "three <- tail

        val tail = linkedList.popBack()
        assertEquals("three", tail) // check the variable tail was set to the previous tail
        assertEquals("five", linkedList.peekBack()) // check that the new tail is the prev node
    }
    
    @Test
    fun checkIsEmptyTrueInt() {
        val linkedList = DoublyLinkedList<Int>()
        assertEquals(true, linkedList.isEmpty())
    }

    @Test
    fun checkIsEmptyTrueString() {
        val linkedList = DoublyLinkedList<String>()
        assertEquals(true, linkedList.isEmpty())
    }

    @Test
    fun checkIsEmptyFalseString() {
        val linkedList = DoublyLinkedList<String>()
        linkedList.pushFront("three")

        assertEquals(false, linkedList.isEmpty())
    }
}