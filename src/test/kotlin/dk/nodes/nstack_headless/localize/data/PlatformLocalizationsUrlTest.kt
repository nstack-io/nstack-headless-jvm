package dk.nodes.nstack_headless.localize.data

import dk.nodes.nstack_headless.localize.Platform
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PlatformLocalizationsUrlTest {

    @Test
    fun `LocalizationUrl returns valid URL`() {
        val backendDevUrl = PlatformLocalizationsUrl(platform = Platform.BACKEND, isDeveloperMode = true)
        val mobileNonDevUrl = PlatformLocalizationsUrl(platform = Platform.MOBILE, isDeveloperMode = false)
        val webNonDevUrl = PlatformLocalizationsUrl(platform = Platform.WEB, isDeveloperMode = false)

        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/backend?dev=true", "$backendDevUrl")
        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/mobile?dev=false", "$mobileNonDevUrl")
        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/web?dev=false", "$webNonDevUrl")
    }
}