package dk.nodes.nstack_headless.localize.data

import dk.nodes.nstack_headless.common.BaseUrl
import dk.nodes.nstack_headless.localize.Platform
import java.net.URL


internal data class LocalizeResourcesUrl(
    private val platform: Platform = Platform.BACKEND,
    private val isDeveloperMode: Boolean = false,
) {

    private val url: URL by lazy {
        BaseUrl
            .createBuilder()
            .addPathSegment("content")
            .addPathSegment("localize")
            .addPathSegment("resources")
            .addEncodedPathSegment(platform.platformId)
            .addQueryParameter("dev", "$isDeveloperMode")
            .build()
            .toUrl()
    }

    override fun toString() = url.toString()
}