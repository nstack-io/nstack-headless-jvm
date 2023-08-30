package dk.nodes.nstack_headless

import dk.nodes.nstack_headless.common.dependency_injection.OkHttpClientProvider
import dk.nodes.nstack_headless.common.dependency_injection.Provider
import dk.nodes.nstack_headless.localize.Locale
import dk.nodes.nstack_headless.localize.Platform
import dk.nodes.nstack_headless.localize.data.CachedRemoteLocalizationsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class NStack(
    private val configuration: Configuration,
) : Provider<Configuration> {

    private var localizationsRepository =
        CachedRemoteLocalizationsRepository(OkHttpClientProvider(this),this)

    fun getTranslation(
        sectionKey: String,
        translationKey: String,
        languageCode: String,
        countryCode: String,
    ): CompletableFuture<String> {
        return GlobalScope.future {
            localizationsRepository.getLocalization(Locale(languageCode, countryCode), Platform.BACKEND)
                .getOrThrow()
                .get(sectionKey, translationKey)
                .orEmpty()
        }
    }

    override fun provide() = configuration
}