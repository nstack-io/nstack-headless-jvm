package dk.nodes.nstack_headless


data class Configuration constructor(
     val appIdKey: String,
     val appApiKey: String,
     val environment: String,

     val cacheLifetimeMinutes: UInt,
     val placeholderPrefix: String,
     val placeholderSuffix: String,

     val isDeveloperMode: Boolean,
) {
     constructor(
          appIdKey: String,
          appApiKey: String,
          environment: String,
     ) : this(appIdKey, appApiKey, environment, 60u, "{", "}", true)
}
