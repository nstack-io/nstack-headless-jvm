package dk.nodes.nstack_headless.localize

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LocalizationTest {

    @Test
    fun `Return proper translation with valid keys`() {
        val sections = mutableMapOf(
            "default" to mutableMapOf(
                "someKey" to "Some value",
                "someOtherKey" to "Some other value",
            ),
            "otherSection" to mutableMapOf(
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
        val sections = mutableMapOf(
            "default" to mutableMapOf("someKey" to "Some value"),
            "default" to mutableMapOf("someOtherKey" to "Some other value"),
            "otherSection" to mutableMapOf("someKey" to "Some value from another section"),
        )
        val localization = Localization(sections)

        assertNull(localization.get(sectionKey = "default", translationKey = "nonexistentTranslationKey"))
        assertNull(localization.get(sectionKey = "nonexistentSection", translationKey = "nonexistentTranslationKey"))
    }
}