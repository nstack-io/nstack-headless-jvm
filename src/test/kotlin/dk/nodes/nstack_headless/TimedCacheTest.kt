package dk.nodes.nstack_headless

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TimedCacheTest {

    @Test
    fun `The value is properly refreshed after the cache validity period is expired`() {
        var changingValue = 0
        val cache = TimedCache(validityPeriodMilliseconds = 1000u, refresh = { ++changingValue })

        runTest {
            val value1 = cache.get(referenceTimestampMilliseconds = 1000u)
            val value2 = cache.get(referenceTimestampMilliseconds = 3000u)

            assertEquals(1, value1)
            assertEquals(2, value2)
        }
    }

    @Test
    fun `The value is not refreshed within the cache validity period`() {
        var changingValue = 0
        val cache = TimedCache(validityPeriodMilliseconds = 1000u, refresh = { ++changingValue })

        runTest {
            val value1 = cache.get(referenceTimestampMilliseconds = 1000u)
            val value2 = cache.get(referenceTimestampMilliseconds = 1500u)

            assertEquals(1, value1)
            assertEquals(1, value2)
        }
    }
}