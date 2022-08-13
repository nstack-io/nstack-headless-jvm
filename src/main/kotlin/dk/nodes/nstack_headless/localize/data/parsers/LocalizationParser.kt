package dk.nodes.nstack_headless.localize.data.parsers

import com.fasterxml.jackson.databind.ObjectMapper
import dk.nodes.nstack_headless.localize.Localization

internal object LocalizationParser {
    fun parse(json: String): Localization {
        val translationTree = ObjectMapper().readTree(json).get("data")
        val localization = Localization()

        translationTree.fieldNames().forEach { sectionKey ->
            translationTree.get(sectionKey).fieldNames().forEach { translationKey ->
                val translation = translationTree
                    .path("$sectionKey").path("$translationKey")
                    .textValue()

                localization.add(sectionKey, translationKey, translation)
            }
        }

        return localization
    }
}
