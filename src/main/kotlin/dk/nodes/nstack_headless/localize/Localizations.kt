package dk.nodes.nstack_headless.localize

import dk.nodes.nstack_headless.common.TimedCache

internal class Localizations(

) {

    private val cachedLocalizations: MutableMap<Locale, TimedCache<Localization>> = mutableMapOf()


}