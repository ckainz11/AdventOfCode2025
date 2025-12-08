package util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class CollectionUtilsTest {

	private val list = listOf(1, 2, 3)
	private val emptyList = emptyList<Int>()

	/*------ pairwise ------*/

	@Test
	fun assertPairwiseWorks() {
		val expected = listOf(1 to 2, 1 to 3, 2 to 3)
		assertEquals(expected, list.pairwise())
	}

	@Test
	fun assertPairwiseWithEmptyListWorks() {
		assertEquals(emptyList, emptyList.pairwise())
	}

	@Test
	fun assertPairwiseWithTransformWorks() {
		val expected = listOf(3, 4, 5)
		assertEquals(expected, list.pairwise { a, b -> a + b })
	}

	/*------ middle ------*/

	@Test
	fun assertMiddleWorks() {
		assertEquals(2, list.middle())
	}

	@Test
	fun assertMiddleWithEmptyListThrowsError() {
		assertThrows<IllegalStateException> { emptyList.middle() }
	}

	@Test
	fun assertMiddleWithEvenSizeListThrowsError() {
		assertThrows<IllegalStateException> { listOf(1, 2).middle() }
	}

    /*----- subsets -----*/

    @Test
    fun assertSubsetsWorks() {
        val expected = listOf(
            listOf(1, 2),
            listOf(1, 3),
            listOf(2, 3)
        )
        assertEquals(expected, list.subsetsOfSize(2))

        val test = listOf(1,2,3,4,5)
        val expected2 = listOf(
            listOf(1,2,3),
            listOf(1,2,4),
            listOf(1,2,5),
            listOf(1,3,4),
            listOf(1,3,5),
            listOf(1,4,5),
            listOf(2,3,4),
            listOf(2,3,5),
            listOf(2,4,5),
            listOf(3,4,5)
        )
        assertEquals(expected2, test.subsetsOfSize(3))

        val test2 = listOf(1,2,3,4,5,6,7,8,9,10)

        assertEquals(210, test2.subsetsOfSize(4).size)
        test2.subsetsOfSize(4).forEach {
            assertEquals(4, it.size)
            assertTrue { it[0] != it[1] && it[1] != it[2] && it[2] != it[3] }
        }
    }
}
