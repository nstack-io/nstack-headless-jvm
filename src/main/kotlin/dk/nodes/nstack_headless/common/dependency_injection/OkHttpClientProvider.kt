package dk.nodes.nstack_headless.common.dependency_injection

import dk.nodes.nstack_headless.Configuration
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

internal class OkHttpClientProvider(
    configurationProvider: Provider<Configuration>
) : SingleInstanceProvider<OkHttpClient>() {

    private val configuration: Configuration = configurationProvider.provide()

    override fun createInstance(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(createHeaderInterceptor("accept", "application/json"))
            .addInterceptor(createHeaderInterceptor("X-APPLICATION-ID", configuration.appIdKey))
            .addInterceptor(createHeaderInterceptor("X-REST-API-KEY", configuration.appApiKey))
            .build()
    }

    private fun createHeaderInterceptor(name: String, value: String): Interceptor {
        return Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestWithUserAgent: Request = originalRequest
                .newBuilder()
                .header(name, value)
                .build()

            chain.proceed(requestWithUserAgent)
        }
    }
}