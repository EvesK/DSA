package assignment7code

import kotlin.math.absoluteValue

/**
 * A customizable implementation of a hash table that maps keys of type [K] to values of type [V].
 * This associative array uses separate chaining for collision resolution and automatically resizes
 * when the load factor exceeds the specified threshold.
 *
 * @param K the type of the keys stored in this associative array.
 * @param V the type of the values stored in this associative array.
 * @param initialCapacity the initial size of the internal hash table. Defaults to 16.
 * @param loadFactor the threshold (as a float) at which the table will resize itself. Defaults to 0.75.
 */
class AssociativeArray<K, V>(initialCapacity: Int = 16, private val loadFactor: Float = 0.75f) {
    private var buckets: Array<MutableList<Pair<K, V>>?> = arrayOfNulls(initialCapacity) // Create an initial buckets array of size initialCapacity filled with nulls
    private var size =  0 // Initialize the size of the entire array to 0
    private val prime = 31 // Set the prime to hash with

    /**
     * Generates a hash code for [key] within the current table size. This function ensures
     * that the hash code is evenly distributed across the buckets to reduce collisions.
     *
     * @param key the key to generate a hash code for.
     * @param numBuckets the current number of buckets, defaulting to the current size of the bucket array.
     * @return the calculated hash code for the specified key, constrained within the bounds of the array.
     */
    private fun hash(key: K, numBuckets: Int = buckets.size): Int {
        val keyStr = key.toString() // Since we don't know how we will receive the key, convert it to a string to work with it
        var hash = 0
        for (char in keyStr) {
            hash = (hash * prime + char.code).absoluteValue // Calculate hash using the prime value
        }
        return hash % numBuckets // Divide by the number of buckets to get the actual value
    }

    /**
     * Resizes the internal bucket array when the current size exceeds the threshold defined by
     * the load factor. This process helps maintain the efficiency of the hash table's operations.
     */
    private fun resize() {
        val newBuckets: Array<MutableList<Pair<K, V>>?> = arrayOfNulls(buckets.size * 2) // Create a new buckets array with the new sizing needed
        buckets.forEach {
            bucket ->
            bucket?.forEach {
                pair ->
                val index = hash(pair.first, newBuckets.size) // Calculate the hash of the key, make sure to pass in newBuckets.size instead of default buckets.size for numBuckets
                if (newBuckets[index] == null) newBuckets[index] = mutableListOf() // If there is nothing in the bucket at that location initialize a list
                newBuckets[index]?.add(pair) // Add the key and value pair to the list for that bucket
            }
        }
        buckets = newBuckets // Update buckets
    }

    /**
     * Inserts or updates a key-value pair in the associative array. If the key already exists,
     * its corresponding value is updated. Otherwise, a new pair is added. This operation may trigger
     * a resize of the internal structure if the load factor threshold is surpassed.
     *
     * @param k the key of the element to insert or update.
     * @param v the value to associate with [k].
     */
    operator fun set(k: K, v: V) {
        if (size >= buckets.size * loadFactor) resize() // Resize if needed
        val index = hash(k) // Hash the key
        if (buckets[index] == null) buckets[index] = mutableListOf() // If buckets at that hash value are empty create a mutableList to hold values
        val existing = buckets[index]?.find {
            it.first == k
        } // Check if there is an existing value at the location we want to hash too
        if (existing != null) {
            buckets[index]?.remove(existing) // If so, remove it before adding the new value
        }
        size++
        buckets[index]?.add(Pair(k, v)) // Add the new pair to the hash location
    }

    /**
     * Checks whether the associative array contains a given key.
     *
     * @param k the key to check for presence in the associative array.
     * @return true if [k] is found, false otherwise.
     */
    operator fun contains(k: K): Boolean {
        val index = hash(k) // Hash the key
        return buckets[index]?.any { it.first == k } ?: false // Check if there is a pair at that location
    }

    /**
     * Retrieves the value associated with a given key, or null if the key is not present.
     *
     * @param k the key whose associated value is to be returned.
     * @return the value associated with [k], or null if no such value exists.
     */
    operator fun get(k: K): V? {
        val index = hash(k) // Hash the key
        return buckets[index]?.find { it.first == k }?.second // Return the value at that location
    }

    /**
     * Removes a key-value pair from the associative array, if it exists.
     *
     * @param k the key of the element to remove.
     * @return true if the element was successfully removed, false if the key was not found.
     */
    fun remove(k: K): Boolean {
        val index = hash(k) // Hash the key
        val toRemove = buckets[index]?.find { it.first == k} ?: return false // Check if there is a pair at that location, if not return false
        buckets[index]?.remove(toRemove) // Remove the pair at that location
        size-- // Decrease the size
        return true
    }


    /**
     * Returns the current number of key-value pairs stored in the associative array.
     * This reflects the actual number of elements managed by the structure, regardless
     * of its internal capacity.
     *
     * @return the number of elements stored in the associative array.
     */
    fun size(): Int {
        return size
    }

    /**
     * Collects and returns all key-value pairs present in the associative array as a list.
     * Each pair in the list represents a unique mapping stored within the array.
     *
     * @return a list of pairs ([K], [V]) representing the associative array's contents.
     */
    fun keyValuePairs(): List<Pair<K, V>> {
        return buckets.filterNotNull().flatten()
    }

    /**
     * Prints the contents of the associative array's buckets to the standard output.
     * Each bucket is displayed along with the key-value pairs it contains. This method
     * is primarily intended for debugging purposes.
     */
    fun printBuckets() {
        println("Buckets:")
        buckets.forEachIndexed { index, bucket ->
            if (!bucket.isNullOrEmpty()) {
                println("Bucket $index: ${bucket.joinToString(separator = ", ") { "(Key: ${it.first}, Value: ${it.second})" }}")
            } else {
                println("Bucket $index: Empty")
            }
        }
    }

}
