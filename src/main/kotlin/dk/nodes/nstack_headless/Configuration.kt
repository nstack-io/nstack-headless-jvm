package dk.nodes.nstack_headless

data class Configuration @JvmOverloads constructor(
    internal val appIdKey: String,
    internal val appApiKey: String,
    internal val environment: String,

    internal val cacheLifetimeMinutes: Int = 60,
    internal val placeholderPrefix: String = "{",
    internal val placeholderSuffix: String = "}",

    internal val enableLogging: Boolean = false,
)
