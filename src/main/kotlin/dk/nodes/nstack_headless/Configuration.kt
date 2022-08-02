package dk.nodes.nstack_headless


data class Configuration constructor(
    internal val appIdKey: String,
    internal val appApiKey: String,
    internal val environment: String,

    internal val cacheLifetimeMinutes: UInt = 60u,
    internal val placeholderPrefix: String = "{",
    internal val placeholderSuffix: String = "}",

    internal val isDeveloperMode: Boolean = false,
)
