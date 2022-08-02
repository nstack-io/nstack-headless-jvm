package dk.nodes.nstack_headless.common

import okhttp3.HttpUrl
import java.net.URL


internal object BaseUrl {

    private const val scheme = "https"
    private const val host = "nstack.io"
    private const val pathSegments = "api/v2"

    @Suppress("MemberVisibilityCanBePrivate")
    val url: URL by lazy {
        createBuilder()
            .build()
            .toUrl()
    }

    fun createBuilder(): HttpUrl.Builder {
        return HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .addPathSegments(pathSegments)
    }

    override fun toString() = url.toString()
}