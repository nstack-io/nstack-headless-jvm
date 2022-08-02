package dk.nodes.nstack_headless.localize

typealias SectionKey = String
typealias TranslationKey = String
typealias SectionMap = MutableMap<SectionKey, MutableMap<TranslationKey, String>>

internal data class Localization(
    private val sections: SectionMap = mutableMapOf()
) {

    internal fun add(
        sectionKey: SectionKey,
        translationKey: TranslationKey,
        localizedText: String
    ) {
        sections[sectionKey] = mutableMapOf(translationKey to localizedText)
    }

    fun get(sectionKey: SectionKey, translationKey: TranslationKey): String? {
        return sections[sectionKey]?.get(translationKey)
    }
}