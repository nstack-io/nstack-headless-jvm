package dk.nodes.nstack_headless.localize.data

import dk.nodes.nstack_headless.localize.Localization
import dk.nodes.nstack_headless.localize.Platform
import java.util.Locale

internal interface LocalizationsRepository {
    suspend fun getLocalization(locale: Locale, platform: Platform): Result<Localization>
}