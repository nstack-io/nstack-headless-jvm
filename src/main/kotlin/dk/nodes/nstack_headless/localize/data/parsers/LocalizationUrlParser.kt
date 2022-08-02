package dk.nodes.nstack_headless.localize.data.parsers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import dk.nodes.nstack_headless.localize.Localization
import okhttp3.Response
import java.io.IOException
import java.net.URL

internal object LocalizationUrlParser {
    fun parse(json: String): URL {
        return JsonFactory()
            .createParser(json)
            .getValueAsString("url")
            .let { URL(it) }
    }
}