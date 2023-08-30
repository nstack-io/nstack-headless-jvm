package dk.nodes.nstack_headless.localize.data

import dk.nodes.nstack_headless.Configuration
import dk.nodes.nstack_headless.common.TimedCache
import dk.nodes.nstack_headless.common.dependency_injection.Provider
import dk.nodes.nstack_headless.localize.Locale
import dk.nodes.nstack_headless.localize.Localization
import dk.nodes.nstack_headless.localize.Platform
import dk.nodes.nstack_headless.localize.data.parsers.LocalizationParser
import dk.nodes.nstack_headless.localize.data.parsers.LocalizationUrlParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class CachedRemoteLocalizationsRepository(
    okHttpClientProvider: Provider<OkHttpClient>,
    configurationProvider: Provider<Configuration>,
) : LocalizationsRepository {

    private val okHttpClient: OkHttpClient = okHttpClientProvider.provide()
    private val localizations: MutableMap<Locale, TimedCache<Localization>> = mutableMapOf()
    private val configuration: Configuration = configurationProvider.provide()

    override suspend fun getLocalization(locale: Locale, platform: Platform): Result<Localization> {
        return localizations.getOrPut(locale) {
            TimedCache(
                validityPeriodMilliseconds = MINUTES.toMillis(configuration.cacheLifetimeMinutes.toLong()).toULong(),
                refresh = { fetchLocalization(locale, platform) })}
            .let { cache -> Result.success(cache.get()) }
    }

    private suspend fun fetchLocalization(locale: Locale, platform: Platform): Localization {
        return fetchLocalization(fetchUrl(locale, platform, configuration.isDeveloperMode))
    }

    private suspend fun fetchLocalization(localizationUrl: URL): Localization {
        return Request
            .Builder()
            .url("$localizationUrl")
            .build()
            .let { execute(it) { body -> LocalizationParser.parse(body) } }
    }

    private suspend fun fetchUrl(locale: Locale, platform: Platform, isDeveloperMode: Boolean): URL {
        return Request
            .Builder()
            .url("${PlatformLocalizationsUrl(platform, isDeveloperMode)}")
            .build()
            .let { execute(it) { body -> LocalizationUrlParser.parse(locale, body) } }
    }

    private suspend fun <R> execute(request: Request, bodyTransformer: (String) -> R): R {
        return suspendCoroutine { continuation ->
            okHttpClient.newCall(request).execute()
                .body
                .let { body -> body ?: throw IOException("No body provided in response") }
                .string()
                .let(bodyTransformer)
                .let { continuation.resume(it) }
        }
    }
}
