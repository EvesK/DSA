import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Problem81Test {
    @Test
    fun fiveByFive() {
        val testMatrix = listOf(
            listOf(131, 673, 234, 103, 18),
            listOf(201, 96, 342, 965, 150),
            listOf(630, 803, 746, 422, 111),
            listOf(537, 699, 497, 121, 956),
            listOf(805, 732, 524, 37, 331)
        )
        assertEquals(2427.0, Problem81(testMatrix))
    }
}