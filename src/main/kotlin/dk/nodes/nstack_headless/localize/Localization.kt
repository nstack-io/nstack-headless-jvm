package dk.nodes.nstack_headless.localize

typealias SectionKey = String
typealias TranslationKey = String
typealias SectionMap = Map<SectionKey, Map<TranslationKey, String>>

internal data class Localization(
    private val sections: SectionMap
) {
    fun get(sectionKey: String, translationKey: String): String? {
        return sections[sectionKey]?.get(translationKey)
    }
}