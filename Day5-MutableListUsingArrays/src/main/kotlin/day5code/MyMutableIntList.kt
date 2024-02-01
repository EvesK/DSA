package day5code

import kotlin.time.measureTime

fun main() {
//    val mutableList = MyMutableIntList()
//    mutableList.printArray()
//    mutableList.add(4)
//    mutableList.printArray()
//    mutableList.add(5)
//    mutableList.printArray()
//    mutableList.add(6)
//    mutableList.printArray()
////    mutableList.add(7)
////    mutableList.printArray()
//    println(mutableList[2])
////    mutableList.clear()
//    mutableList.set(1, 8)
//    mutableList.printArray()
    val arraySizes = listOf(100, 1000, 10000, 100000, 1000000, 10000000, 100000000)
    println("numberOfElements totalTime timePerElement")
    for (arraySize in arraySizes) {
        val myList = MyMutableIntList()
        val timeTaken = measureTime {
            for (i in 0..<arraySize) {
                myList.add(i)
            }
        }
        println("$arraySize $timeTaken ${timeTaken/arraySize}")
    }
}

class MyMutableIntList {
    private var myArray = IntArray(1)
    private var nextSlot = 0

    /**
     * Add [element] to the end of the list
     */
    fun add(element: Int) {
        if (myArray.size > size()) {
            myArray[nextSlot] = element
            nextSlot++
        } else {
            val currentArray = myArray.copyOf()
            myArray = IntArray((size() * 2))
            for (i in 0..<size()) {
                myArray[i] = currentArray[i]
            }
            myArray[nextSlot] = element
            nextSlot++
        }
    }

    /**
     * Remove all elements from the list
     */
    fun clear() {
        myArray = IntArray(1)
        nextSlot = 0
    }

    /*
     * @return the size of the list
     */
    fun size(): Int {
        return nextSlot
    }

    /**
     * @param index the index to return
     * @return the element at [index]
     */
    operator fun get(index: Int): Int {
        if (index < size()) {
            return myArray[index]
        } else {
            throw Exception("Index $index out of bounds for length $nextSlot")
        }
    }

    /**
     * Store [value] at position [index]
     * @param index the index to set
     * @param value to store at [index]
     */
    operator fun set(index: Int, value: Int) {
        if (index < size()) {
            myArray[index] = value
        } else {
            throw Exception("Index $index out of bounds for length $nextSlot")
        }
    }

    /**
     * Print the elements of the array.
     */
    fun printArray() {
        val printArray = myArray.copyOfRange(0, size())
        println(printArray.joinToString(","))
    }
}