import java.util.LinkedList
import java.util.Queue

enum class Color {
    BLACK,
    RED
}

/**
 * Represents a node in a red-black tree.
 *
 * @property key The unique identifier for the node.
 */
class Node(val key: Int) {
    var parent: Node? = null
    var color: Color = Color.RED
    var left: Node? = null
    var right: Node? = null

    /**
     * Returns a string representation of the node's color.
     *
     * @return "(b)" for black nodes and "(r)" for red nodes.
     */
    fun printColor(): String = if (color == Color.BLACK) "(b)" else "(r)"
}

/**
 * Represents a red-black tree, providing operations to insert elements, look up elements,
 * and verify the tree's adherence to red-black properties.
 */
class RedBlackTree {
    private val nil = Node(-1).apply {
        color = Color.BLACK
        left = null
        right = null
    }
    private var root: Node? = nil

    /**
     * Performs a left rotation on the given node within the red-black tree.
     *
     * @param x The node around which the left rotation is to be performed.
     */
    private fun leftRotate(x: Node) {
        val y = x.right ?: return
        x.right = y.left
        y.left?.parent = x

        y.parent = x.parent
        if (x.parent == null) {
            root = y
        } else if (x == x.parent?.left) {
            x.parent?.left = y
        } else {
            x.parent?.right = y
        }

        y.left = x
        x.parent = y
    }

    /**
     * Performs a right rotation on the given node within the red-black tree.
     *
     * @param x The node around which the right rotation is to be performed.
     */
    private fun rightRotate(x: Node) {
        val y = x.left ?: return
        x.left = y.right
        y.right?.parent = x

        y.parent = x.parent
        if (x.parent == null) {
            root = y
        } else if (x == x.parent?.right) {
            x.parent?.right = y
        } else {
            x.parent?.left = y
        }

        y.right = x
        x.parent = y
    }

    /**
     * Inserts a new node with the given key into the red-black tree, maintaining the tree's properties.
     *
     * @param key The key of the new node to be inserted.
     */
    fun insert(key: Int) {
        val z = Node(key).apply {
            left = nil
            right = nil
        }

        var y: Node? = null
        var x = root
        while (x != nil) {
            y = x
            if (z.key == x!!.key) {
                return // If a duplicate is found, do nothing
            }
            x = if (z.key < x!!.key) x.left else x.right
        }
        z.parent = y
        if (y == null) {
            root = z
        } else if (z.key < y.key) {
            y.left = z
        } else {
            y.right = z
        }

        insertFixup(z)
    }

    /**
     * Fixes up the red-black tree after insertion to ensure that red-black properties are maintained.
     *
     * @param z The newly inserted node that may have caused violations of red-black properties.
     */
    private fun insertFixup(z: Node) {
        var zVar = z
        while (zVar.parent?.color == Color.RED) {
            if (zVar.parent == zVar.parent?.parent?.left) {
                val y = zVar.parent?.parent?.right
                if (y?.color == Color.RED) {
                    zVar.parent?.color = Color.BLACK
                    y.color = Color.BLACK
                    zVar.parent?.parent?.color = Color.RED
                    zVar = zVar.parent?.parent ?: break
                } else {
                    if (zVar == zVar.parent?.right) {
                        zVar = zVar.parent ?: break
                        leftRotate(zVar)
                    }
                    zVar.parent?.color = Color.BLACK
                    zVar.parent?.parent?.color = Color.RED
                    rightRotate(zVar.parent?.parent ?: break)
                }
            } else {
                val y = zVar.parent?.parent?.left
                if (y?.color == Color.RED) {
                    zVar.parent?.color = Color.BLACK
                    y.color = Color.BLACK
                    zVar.parent?.parent?.color = Color.RED
                    zVar = zVar.parent?.parent ?: break
                } else {
                    if (zVar == zVar.parent?.left) {
                        zVar = zVar.parent ?: break
                        rightRotate(zVar)
                    }
                    zVar.parent?.color = Color.BLACK
                    zVar.parent?.parent?.color = Color.RED
                    leftRotate(zVar.parent?.parent ?: break)
                }
            }
        }

        root?.color = Color.BLACK
    }

    /**
     * Looks up a node by its key in the red-black tree.
     *
     * @param k The key of the node to find.
     * @return The node with the given key if it exists, null otherwise.
     */
    fun lookup(k: Int): Node? {
        var x: Node? = root
        while (x != null && x != nil) {
            val currentKey = x.key
            x = if (k < currentKey) {
                x.left
            } else if (k > currentKey) {
                x.right
            } else {
                return x // Found the node with key == k
            }
        }
        return null // Key not found or x is nil
    }

    /**
     * Performs an in-order traversal of the red-black tree, executing a given action on each node's key.
     *
     * @param node The current node being visited (defaults to the root of the tree).
     * @param action The action to be performed on each node's key during the traversal.
     */
    fun inOrderTraversal(node: Node? = root, action: (Int) -> Unit) {
        if (node == null || node == nil) return
        inOrderTraversal(node.left, action)
        action(node.key)
        inOrderTraversal(node.right, action)
    }

    /**
     * Performs a level-order traversal of the red-black tree, visiting each node in breadth-first order.
     * Starting from the root, this traversal visits nodes level by level, from left to right.
     * The provided action is applied to each visited node's key.
     *
     * @param action The action to perform on each node's key during traversal, typically to process or
     * collect the keys.
     */
    fun levelOrderTraversal(action: (Int) -> Unit) {
        val queue: Queue<Node?> = LinkedList()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            if (node == null || node == nil) continue
            action(node.key)
            queue.add(node.left)
            queue.add(node.right)
        }
    }

    /**
     * Verifies that the current red-black tree adheres to the essential properties of red-black trees.
     * The verification covers the following properties:
     *
     * Property 1: Each node is either red or black.
     * - This property is guaranteed by the design of the Node class and the Color enum,
     *   ensuring that every node's color is explicitly set to either RED or BLACK upon creation
     *   or modification. There is no mechanism within the tree operations to assign any color
     *   outside of these two options.
     *
     * Property 2: The root is black, and every leaf (NIL) is black.
     * - The root's color is explicitly checked to be black. The method `verifyRootBlack` ensures
     *   this condition is met. The NIL nodes, representing the leaves, are initialized as black
     *   and never modified, thus inherently satisfying this property without the need for
     *   additional checks.
     *
     * Property 3: If a node is red, then both its children are black.
     * - The method `verifyBlackChildrenForRedNode` recursively checks each node in the tree, starting from the root.
     *   If a red node is encountered, the function verifies that both of its children are black,
     *   thus adhering to Property 3. This method provides comprehensive validation for this property
     *   across the entire tree.
     *
     * Property 4: Every path from the root to a leaf has the same number of black nodes.
     * - To verify this property, the method `verifyPathBlackCount` uses a depth-first search approach
     *   to traverse all paths from the root to the leaves. It counts the number of black nodes
     *   encountered along each path, collecting these counts in a list. After traversing all paths,
     *   it checks that all entries in the list are identical, ensuring uniformity in the number
     *   of black nodes across all paths, as required by Property 4.
     *
     * @return true if the tree satisfies all red-black tree properties, false otherwise.
     */
    fun verifyRedBlackProperties(): Boolean {
        return verifyRootBlack() && verifyBlackChildrenForRedNode() && verifyPathBlackCount()
    }

    /**
     * Verifies that the root of the red-black tree is black.
     *
     * @return true if the root is black, false otherwise.
     */
    private fun verifyRootBlack(): Boolean {
        // Check if the root is black
        if (root?.color != Color.BLACK) {
            return false
        }

        // The tree is set up such that creating a nil node will set it to black.
        return true
    }

    /**
     * Recursively verifies that each red node has only black children.
     *
     * @param node The node to start the verification from (defaults to the root).
     * @return true if all red nodes have black children, false if any red node has a red child.
     */
    private fun verifyBlackChildrenForRedNode(node: Node? = root): Boolean {
        if (node == null || node == nil) return true // Base case: Reached a leaf

        // If the node is red, check if both children are black
        if (node.color == Color.RED) {
            if ((node.left != null && node.left!!.color != Color.BLACK) ||
                (node.right != null && node.right!!.color != Color.BLACK)) {
                println("Red node with key ${node.key} has a non-black child.")
                return false
            }
        }

        // Recursively check for both children
        return verifyBlackChildrenForRedNode(node.left) && verifyBlackChildrenForRedNode(node.right)
    }

    /**
     * Verifies that all paths from the root to a leaf have the same number of black nodes.
     *
     * @return true if all paths have the same number of black nodes, false otherwise.
     */
    private fun verifyPathBlackCount(): Boolean {
        val blackCountList = mutableListOf<Int>()
        checkBlackNodeCount(root, 0, blackCountList)

        // All paths must have the same number of black nodes.
        if (blackCountList.distinct().size != 1) {
            println("Not all paths from the root to a leaf have the same number of black nodes.")
            return false
        }
        return true
    }

    /**
     * Helper function to count black nodes along all paths from the root to leaves.
     *
     * @param node The current node being visited.
     * @param currentCount The count of black nodes encountered so far on the current path.
     * @param blackCountList A list collecting the count of black nodes for each path from the root to a leaf.
     */
    private fun checkBlackNodeCount(node: Node?, currentCount: Int, blackCountList: MutableList<Int>) {
        var count = currentCount
        if (node == null || node == nil) {
            // Reached a leaf (NIL node), add the current count to the list.
            blackCountList.add(count)
            return
        }

        // If the current node is black, increment the count.
        if (node.color == Color.BLACK) count++

        // Recursively check both subtrees.
        checkBlackNodeCount(node.left, count, blackCountList)
        checkBlackNodeCount(node.right, count, blackCountList)
    }
}