package assignment2code

interface Queue<T> {
    /**
     * Add [data] to the back of the queue.
     */
    fun enqueue(data: T)
    /**
     * Remove the element at the front of the queue.  If the queue is empty, it remains unchanged.
     * @return the value at the front of the queue or nil if none exists
     */
    fun dequeue(): T?
    /**
     * @return the value at the front of the queue or nil if none exists
     */
    fun peek(): T?
    /**
     * @return true if the queue is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

class LinkedListQueue<T> : Queue<T> {
    private val linkedList = DoublyLinkedList<T>()

    override fun enqueue(data: T) {
        linkedList.pushBack(data)
    }

    override fun dequeue(): T? {
        return linkedList.popFront()
    }

    override fun peek(): T? {
        return linkedList.peekFront()
    }

    override fun isEmpty(): Boolean {
        return linkedList.isEmpty()
    }

    fun print() {
        linkedList.printList()
    }
}
