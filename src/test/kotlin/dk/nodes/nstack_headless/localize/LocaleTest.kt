package dk.nodes.nstack_headless.localize

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertFailsWith

internal class LocaleTest {

    @Test
    fun `Locale string is properly generated from valid codes`() {
        val locale = Locale("en", "GB")

        assertEquals("en-GB", "$locale")
    }

    @Test
    fun `Locale string is properly generated from codes with invalid cases`() {
        val locale = Locale("En", "gb")

        assertEquals("en-GB", "$locale")
    }

    @Test
    fun `Invalid codes cause exception`() {
        assertFailsWith<IllegalArgumentException> {
            Locale("ab", "43")
        }
    }

    @Test
    fun testEquals() {
        assertEquals(Locale("en", "GB"), Locale("en", "GB"))
        assertEquals(Locale("en", "gb"), Locale("EN", "GB"))
    }
}