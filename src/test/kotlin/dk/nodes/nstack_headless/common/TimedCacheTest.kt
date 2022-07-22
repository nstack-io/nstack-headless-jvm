package dk.nodes.nstack_headless.common

import dk.nodes.nstack_headless.common.TimedCache
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertFailsWith
import kotlin.test.expect

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

    @Test
    fun `Throw exception if the next timestamp is in the past`() {
        assertFailsWith<IllegalArgumentException> {
            var changingValue = 0
            val cache = TimedCache(validityPeriodMilliseconds = 1000u, refresh = { ++changingValue })

            runTest {
                cache.get(referenceTimestampMilliseconds = 1000u)
                cache.get(referenceTimestampMilliseconds = 500u)
            }
        }
    }
}