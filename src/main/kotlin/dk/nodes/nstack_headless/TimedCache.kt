package dk.nodes.nstack_headless

internal class TimedCache<out T>(
    private val validityPeriodMilliseconds: ULong,
    private val refresh: suspend () -> T,
) {

    private var cachedValue: T? = null
    private var lastRefreshTimestampMilliseconds: ULong = 0u

    /**
     * Retrieve the cached value, or request it anew if it's out of date.
     *
     * @param referenceTimestampMilliseconds the timestamp acting as the moment against which the last refresh is compared. If enough time has passed between the two, the content will be refreshed.
     */
    suspend fun get(referenceTimestampMilliseconds: ULong = System.currentTimeMillis().toULong()): T {

        val illegalNullCacheException = IllegalStateException("The cached value should never be null at this point!")

        return if (isCacheValid(referenceTimestampMilliseconds)) {
            cachedValue ?: throw illegalNullCacheException
        } else {
            cachedValue = refresh()
            lastRefreshTimestampMilliseconds = referenceTimestampMilliseconds
            cachedValue ?: throw illegalNullCacheException
        }
    }

    private fun isCacheValid(referenceTimestampMilliseconds: ULong): Boolean {
        require(referenceTimestampMilliseconds >= lastRefreshTimestampMilliseconds)

        val cacheAgeMilliseconds = referenceTimestampMilliseconds - lastRefreshTimestampMilliseconds

        return cacheAgeMilliseconds < validityPeriodMilliseconds
    }
}