package assignment2code

class Node<T>(
    var value: T,
    var nextNode: Node<T>? = null,
    var prevNode: Node<T>? = null
)

class DoublyLinkedList<T> {
    private var headNode: Node<T>? = null
    private var tailNode: Node<T>? = null

    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T) {
        // the new head will be the data we entered
        val newHead = Node(data)
        // create a copy of the head node to store as current head
        val currentHead = headNode

        // modify the current head so that its previous node (which before was null since it was the head), is the new head
        currentHead?.prevNode = newHead
        // modify the new head so that its next node (which previously was null since we didn't set one above) is the old head
        newHead.nextNode = currentHead

        // set the new head to the head node
        headNode = newHead

        // if the list was previously empty, the new node will be both the head and the tail, so set it to the tail node as well
        if (tailNode == null) {
            tailNode = newHead
        }
    }

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T) {
        // the new tail will be the data we entered
        val newTail = Node(data)
        // create a copy of the tail node to store as current tail
        val currentTail = tailNode

        // modify the current tail so that its next node (which before was null since it was the tail), is the new tail
        currentTail?.nextNode = newTail
        // modify the new tail so that its previous node (which previously was null since we didn't set one above) is the old tail
        newTail.prevNode = currentTail

        // set the new tail to the tail node
        tailNode = newTail

        // if the list was previously empty, the new node will be both the head and the tail, so set it to the head node as well
        if (headNode == null) {
            headNode = newTail
        }
    }

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popFront(): T? {
        // check if the list has a head; if so, set the head to removedHead, else return null and end the function
        val removedHead = headNode ?: return null

        // set the new head to be the next node after the removed head
        headNode = removedHead.nextNode
        // set the previous node of the new head to be null, since it is now the head
        headNode?.prevNode = null

        // check if the list was only one item long, i.e. the head and tail were the same node; if so, also remove the tail node
        if (removedHead == tailNode) {
            popBack()
        }

        // return the value of the removed head
        return removedHead.value
    }

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popBack(): T? {
        // check if the list has a tail; if so, set the tail to removedTail, else return null and end the function
        val removedTail = tailNode ?: return null

        // set the new tail to be the previous node before the removed tail
        tailNode = removedTail.prevNode
        // set the next node of the new tail to be null, since it is now the tail
        tailNode?.nextNode = null

        // check if the list was only one item long, i.e. the head and tail were the same node; if so, also remove the head node
        if (removedTail == headNode) {
            popFront()
        }

        // return the value of the removed tail
        return removedTail.value
    }

    /**
     * @return the value at the front of the list or nil if none exists
     */
    fun peekFront(): T? {
        // return the value of the head node without modifying or removing it
        return headNode?.value
    }

    /**
     * @return the value at the back of the list or nil if none exists
     */
    fun peekBack(): T? {
        // return the value of the tail node without modifying or removing it
        return tailNode?.value
    }

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean {
        // returns the boolean value of the comparison does the head node exist? If the list is empty then it will return true, since the head node will equal null, if the list is not empty it will return false, since the head node will equal somethign other than null
        return headNode == null
    }

    fun printList() {
        var currentNode = headNode
        println("Head")
        while (currentNode != null) {
            println(currentNode.value)
            currentNode = currentNode.nextNode
        }
        println("Tail")
    }
}