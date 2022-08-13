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
import java.util.concurrent.TimeUnit
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
        val cacheLifetimeMillis = TimeUnit.MINUTES.toMillis(configuration.cacheLifetimeMinutes.toLong()).toULong()

        if (!localizations.contains(locale)) {
            val cache = TimedCache(
                validityPeriodMilliseconds = cacheLifetimeMillis,
                refresh = { getLocalizationFromRemote(locale, platform) }
            )

            localizations[locale] = cache
        }

        return try {
            localizations[locale]
                .let { it ?: throw IllegalStateException("The localization should never be null here") }
                .get()
                .let { localization -> Result.success(localization) }
        } catch (exception: IOException) {
            Result.failure(exception)
        }
    }

    private suspend fun getLocalizationFromRemote(locale: Locale, platform: Platform): Localization {
        val url = getLocalizationUrlFromRemote(locale, platform, configuration.isDeveloperMode)
        return getLocalizationFromRemote(url)
    }

    private suspend fun getLocalizationFromRemote(localizationUrl: URL): Localization {

        val request = Request
            .Builder()
            .url("$localizationUrl")
            .build()

        return suspendCoroutine {
            okHttpClient
                .newCall(request)
                .execute()
                .use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected response code: $response")
                    } else {
                        it.resume(response
                            .body
                            .let { body -> body ?: throw IOException("No body provided in response: $response") }
                            .string()
                            .let { json -> LocalizationParser.parse(json) })
                    }
                }
        }
    }

    private suspend fun getLocalizationUrlFromRemote(locale: Locale, platform: Platform, isDeveloperMode: Boolean): URL {


        val request = Request
            .Builder()
            .url("${PlatformLocalizationsUrl(platform, isDeveloperMode)}")
            .build()

        return suspendCoroutine {
            okHttpClient
                .newCall(request)
                .execute()
                .use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected response code: $response")
                    } else {
                        it.resume(response
                            .body
                            .let { body -> body ?: throw IOException("No body provided in response: $response") }
                            .string()
                            .let { json -> LocalizationUrlParser.parse(locale, json) })
                    }
                }

        }
    }
}
