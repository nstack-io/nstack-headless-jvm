package dk.nodes.nstack_headless.localize.data.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import dk.nodes.nstack_headless.localize.Locale
import java.net.URL
import java.util.NoSuchElementException


internal object LocalizationUrlParser {
    fun parse(locale: Locale, json: String): URL {
       return ObjectMapper().readTree(json).get("data").elements().asSequence()
           .filter { locale.toString() == it.path("language").path("locale").asText() }
           .map { URL(it.path("url").textValue()) }
           .ifEmpty { throw NoSuchElementException("$locale URL not found") }
           .first()

    }
}
