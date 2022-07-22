package dk.nodes.nstack_headless.localize

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LocalizationUrlTest {

    @Test
    fun `LocalizationUrl returns valid URL`() {
        val backendDevUrl = LocalizationUrl(platform = Platform.BACKEND, isDeveloperMode = true)
        val mobileNonDevUrl = LocalizationUrl(platform = Platform.MOBILE, isDeveloperMode = false)
        val webNonDevUrl = LocalizationUrl(platform = Platform.WEB, isDeveloperMode = false)

        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/backend?dev=true", "$backendDevUrl")
        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/mobile?dev=false", "$mobileNonDevUrl")
        assertEquals("https://nstack.io/api/v2/content/localize/resources/platforms/web?dev=false", "$webNonDevUrl")
    }
}