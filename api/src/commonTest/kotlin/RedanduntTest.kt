import kotlin.test.Test
import kommander.expect

class RedanduntTest {
    @Test
    fun should_pass() {
        expect<Int>(1 + 1).toBe(2)
    }
}