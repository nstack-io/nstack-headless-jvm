package dk.nodes.nstack_headless.localize

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LocalizationTest {

    @Test
    fun `Return proper translation with valid keys`() {
        val sections = mapOf(
            "default" to mapOf(
                "someKey" to "Some value",
                "someOtherKey" to "Some other value",
            ),
            "otherSection" to mapOf(
                "someKey" to "Some value from another section"
            ),
        )
        val localization = Localization(sections)

        assertEquals("Some value", localization.get(sectionKey = "default", translationKey = "someKey"))
        assertEquals("Some other value", localization.get(sectionKey = "default", translationKey = "someOtherKey"))
        assertEquals("Some value from another section", localization.get(sectionKey = "otherSection", translationKey = "someKey"))
    }

    @Test
    fun `Return null if translation doesn't exist`() {
        val sections = mapOf(
            "default" to mapOf("someKey" to "Some value"),
            "default" to mapOf("someOtherKey" to "Some other value"),
            "otherSection" to mapOf("someKey" to "Some value from another section"),
        )
        val localization = Localization(sections)

        assertNull(localization.get(sectionKey = "default", translationKey = "nonexistentTranslationKey"))
        assertNull(localization.get(sectionKey = "nonexistentSection", translationKey = "nonexistentTranslationKey"))
    }
}