package dk.nodes.nstack_headless.localize.data.parsers

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val TEST_JSON = """
{    
    "data": {
        "default": {
            "hello_world": "Hello, world!",
            "test": "Test from default"
        },
        "first_section": {
            "test": "Test from the first section"
        },
        "second_section": {
            "test": "Test from the second section",
            "another_test": "Another test from the second section"
        }
    }
}
"""

internal class LocalizationParserTest {

    @Test
    fun `Localization parser properly parses provided JSON`() {
        val parsedLocalization = LocalizationParser.parse(TEST_JSON)

        assertEquals("Hello, world!", parsedLocalization.get("default", "hello_world"))
        assertEquals("Test from default", parsedLocalization.get("default", "test"))
        assertEquals("Test from the first section", parsedLocalization.get("first_section", "test"))
        assertEquals("Test from the second section", parsedLocalization.get("second_section", "test"))
        assertEquals("Another test from the second section", parsedLocalization.get("second_section", "another_test"))
    }
}