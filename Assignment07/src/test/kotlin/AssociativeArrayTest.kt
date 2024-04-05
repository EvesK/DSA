package assignment7code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AssociativeArrayTest {

    /**
     * Tests the basic functionality of setting and then getting a value using a key.
     * Ensures that a value can be stored and then retrieved accurately.
     */
    @Test
    fun `test set and get`() {
        val associativeArray = AssociativeArray<String, Int>()
        associativeArray.set("key1", 100)
        assertEquals(100, associativeArray.get("key1"))
    }

    /**
     * Verifies the presence of a key within the associative array.
     * Checks both a key that has been inserted and a key that has not, ensuring
     * the method accurately reflects the presence or absence of keys.
     */
    @Test
    fun `test contains key`() {
        val associativeArray = AssociativeArray<String, Int>()
        associativeArray.set("key2", 200)
        assertTrue(associativeArray.contains("key2"))
        assertFalse(associativeArray.contains("key3"))
    }

    /**
     * Tests the removal of a key-value pair from the associative array.
     * Ensures that once a key is removed, it no longer appears in the associative array.
     */
    @Test
    fun `test remove key`() {
        val associativeArray = AssociativeArray<String, Int>()
        associativeArray.set("key4", 400)
        assertTrue(associativeArray.remove("key4"))
        assertFalse(associativeArray.contains("key4"))
    }

    /**
     * Verifies the size method of the associative array.
     * Checks the size before and after adding elements to ensure it accurately counts the number of key-value pairs.
     */
    @Test
    fun `test size`() {
        val associativeArray = AssociativeArray<String, Int>()
        assertEquals(0, associativeArray.size())
        associativeArray.set("key5", 500)
        associativeArray.set("key6", 600)
        assertEquals(2, associativeArray.size())
    }

    /**
     * Tests the associative array's handling of key collisions.
     * Inserts two keys that are expected to hash to the same bucket and verifies they are both retrievable,
     * ensuring the collision is resolved correctly.
     */
    @Test
    fun `test handle collision`() {
        val associativeArray = AssociativeArray<Int, String>(5)

        associativeArray.set(1, "Value1")
        associativeArray.set(6, "Value2")

        assertEquals("Value1", associativeArray.get(1))
        assertEquals("Value2", associativeArray.get(6))

        assertEquals(2, associativeArray.size())
    }

    /**
     * Tests the rehashing process of the associative array.
     * Inserts enough elements to trigger a resize and verifies that all elements remain accessible,
     * ensuring the rehashing process maintains data integrity.
     */
    @Test
    fun `test rehashing`() {
        val initialCapacity = 4 // Small capacity to force rehashing
        val associativeArray = AssociativeArray<Int, String>(initialCapacity)
        for (i in 1..8) { // Insert more items than initialCapacity to trigger rehashing
            associativeArray.set(i, "value$i")
        }

        // Verify that rehashing occurred and all items are still correctly accessible
        for (i in 1..8) {
            assertEquals("value$i", associativeArray.get(i))
        }

        assertTrue(associativeArray.size() > initialCapacity) // Ensure the array was resized
    }

    /**
     * Tests the integrity of data following a resize operation.
     * Specifically ensures that after the internal structure is expanded, all previously inserted
     * elements remain correctly mapped and accessible.
     */
    @Test
    fun `test data integrity after resize`() {
        println("Starting test data integrity after resize")
        val associativeArray = AssociativeArray<Int, String>(2) // Small initial capacity to trigger early resize
        associativeArray.set(1, "One")
        associativeArray.set(2, "Two")
        associativeArray.set(3, "Three") // Insertion that should trigger resize

        assertEquals("One", associativeArray.get(1))
        assertEquals("Two", associativeArray.get(2))
        assertEquals("Three", associativeArray.get(3))
        assertEquals(3, associativeArray.size())
    }
}