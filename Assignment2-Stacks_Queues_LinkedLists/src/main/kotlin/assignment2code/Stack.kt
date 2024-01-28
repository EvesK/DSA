package assignment2code

interface Stack<T> {
    /**
     * Add [data] to the top of the stack
     */
    fun push(data: T)

    /**
     * Remove the element at the top of the stack.  If the stack is empty, it remains unchanged.
     * @return the value at the top of the stack or nil if none exists
     */
    fun pop(): T?

    /**
     * @return the value on the top of the stack or nil if none exists
     */
    fun peek(): T?

    /**
     * @return true if the stack is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

class LinkedListStack<T> : Stack<T> {
    private val linkedList = DoublyLinkedList<T>()

    override fun push(data: T) {
        linkedList.pushFront(data)
    }

    override fun pop(): T? {
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
