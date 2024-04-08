import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RedBlackTreesTest {

    /**
     * Verifies that inserting elements into the red-black tree increases its size as expected
     * and that the tree retains all red-black properties after each insertion.
     */
    @Test
    fun `test insert increases tree size and retains properties`() {
        val tree = RedBlackTree()
        tree.insert(5)
        tree.insert(3)
        tree.insert(7)
        tree.insert(6)

        // Verify the tree size indirectly through a traversal
        val result = mutableListOf<Int>()
        tree.inOrderTraversal { result.add(it) }

        assertEquals(listOf(3, 5, 6, 7), result)

        // Verify Red-Black Tree properties
        assertTrue(tree.verifyRedBlackProperties())
    }

    /**
     * Validates that the search function can successfully find an element that exists within the red-black tree.
     */
    @Test
    fun `test search finds existing element`() {
        val tree = RedBlackTree()
        listOf(20, 15, 25).forEach { tree.insert(it) }
        assertNotNull(tree.lookup(15))
    }

    /**
     * Confirms that the search function appropriately fails to find an element that does not exist within the red-black tree.
     */
    @Test
    fun `test search does not find non-existing element`() {
        val tree = RedBlackTree()
        listOf(20, 15, 25).forEach { tree.insert(it) }
        assertNull(tree.lookup(10))
    }

    /**
     * Verifies the behavior of inserting a node into an initially empty red-black tree.
     */
    @Test
    fun `test insert into empty tree`() {
        val tree = RedBlackTree()
        tree.insert(50) // Insert into an initially empty tree

        // Verify the inserted node is now the root and the tree retains properties
        assertTrue(tree.verifyRedBlackProperties())
    }

    /**
     * Tests the handling of duplicate key insertion in the red-black tree.
     */
    @Test
    fun `test insert handles duplicates appropriately`() {
        val tree = RedBlackTree()
        tree.insert(10)
        val originalStructure = mutableListOf<Int>()
        tree.inOrderTraversal { originalStructure.add(it) }
        val originalSize = originalStructure.size
        tree.insert(10) // Attempt to insert a duplicate

        val newStructure = mutableListOf<Int>()
        tree.inOrderTraversal { newStructure.add(it) }

        assertEquals(originalStructure, newStructure, "The tree's structure should remain unchanged after attempting to insert a duplicate.")
        assertEquals(originalSize, newStructure.size, "The tree's size should remain unchanged after attempting to insert a duplicate.")
        assertTrue(tree.verifyRedBlackProperties(), "The tree should maintain red-black properties after attempting to insert a duplicate.")
    }

    /**
     * Validates that the red-black tree retains its properties after inserting a large number of nodes.
     */
    @Test
    fun `test large scale inserts maintain properties`() {
        val tree = RedBlackTree()
        (1..1000).forEach { tree.insert(it) }

        // Verify the tree's properties are intact after many insertions
        assertTrue(tree.verifyRedBlackProperties())
    }

    /**
     * Ensures that appropriate rotations are triggered during node insertion to maintain the red-black tree's balance.
     */
    @Test
    fun `test insert triggers appropriate rotations`() {
        val tree = RedBlackTree()
        // Insert values in a manner that should trigger rotations
        tree.insert(3)
        tree.insert(2)
        tree.insert(1) // This should trigger a rotation

        // Use in-order traversal to verify the tree's sorted order
        val inOrderResult = mutableListOf<Int>()
        tree.inOrderTraversal { inOrderResult.add(it) }
        assertEquals(listOf(1, 2, 3), inOrderResult, "In-order traversal should return sorted keys")

        // Use level-order traversal to infer the tree's structure
        val levelOrderResult = mutableListOf<Int>()
        tree.levelOrderTraversal { levelOrderResult.add(it) }
        assertEquals(listOf(2, 1, 3), levelOrderResult, "Level-order traversal should reflect the tree structure post-rotation")
    }
}