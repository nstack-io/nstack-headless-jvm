package dk.nodes.nstack_headless.localize.data.parsers

import dk.nodes.nstack_headless.localize.Locale
import org.junit.jupiter.api.Test
import java.net.URL
import kotlin.test.assertEquals

private const val TEST_JSON = """
{
    "data":[
        {
            "url":"https://test.url/resources/1",
            "language":{
                "locale":"de-DE"
            }
        },
        {
            "url":"https://test.url/resources/2",
            "language":{
                "locale":"en-GB"
            }
        }
    ]
}
"""

internal class LocalizationUrlParserTest {

    @Test
    fun `Localization Url parser properly parses provided JSON`() {
        val url = LocalizationUrlParser.parse(Locale("de", "DE"), TEST_JSON)

        assertEquals(URL("https://test.url/resources/1"), url)
    }
}