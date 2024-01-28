package assignment2code

/*
P3: How would you reverse the elements in a stack (i.e., put the elements at the
 top of the stack on the bottom and vice-versa)? You can use as many additional
 stacks and queues as temporary storage in your approach.

Strategy:
1. Pop the entire stack onto a new stack.
2. Return the new stack.

Since stacks use a first on first off approach, popping everything from the original stack onto a new stack will create a reversed stack.
*/

fun main() {
    val stackToReverse = LinkedListStack<Int>()
    stackToReverse.push(3)
    stackToReverse.push(4)
    stackToReverse.push(5)
    //stackToReverse head -> 5, 4, 3 <- tail
    println("Stack to Reverse")
    stackToReverse.print()

    val reversedStack = reverseStack(stackToReverse)
    println("Reversed Stack")
    reversedStack.print()
}

fun <T> reverseStack(stack: LinkedListStack<T>): LinkedListStack<T> {
    val newStack = LinkedListStack<T>()
    while (!stack.isEmpty()) {
        // Assuming stack.pop() will be non-null since we already checked that stack is not empty
        val next = stack.pop()!!
        newStack.push(next)
    }

    return newStack
}

/*
P4: Come up with a strategy to solve the valid parentheses problem.
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:
Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.

Strategy:
1. Initialize an empty stack
2. Go through the input string character by character.
    a. If the character is a left bracket, i.e. '(', '{', or '[', then push it to the stack
    b. If the character is a right bracket, i.e. ')' , '}', or ']', then pop the character from the top of the stack.
       Check if the character we just popped is a corresponding left bracket for the current right bracket. If it is, keeping going. If it isn't or the stack is empty, return false.
3. After finishing the entire string, check if the stack is empty. If it is, return true, if not, return false.
 */

/*
P5: Solve the copy stack problem (source: University of Washington CSE122)
Given a stack return a copy of the original stack (i.e., a new stack with the same values as the original, stored in the same order as the original).
Your method should create the new stack and fill it up with the same values that are stored in the original stack.
You may use one queue as auxiliary storage.

(Assuming we can use the original stack as storage as well as the auxiliary queue).
Strategy:
1. Pop the entire stack into the queue.
2. Dequeue the entire queue into the original stack (which will be empty after step 1). Now the stack is flipped.
3. Pop the entire stack into the queue (which will be empty again after step 2).
4. Dequeue the entire queue into both the original stack and the new stack.
5. Return the new stack, which is a copy of the original stack.
 */

// Since you didn't say to write unit tests for the practice problem(s), I didn't write any :)
