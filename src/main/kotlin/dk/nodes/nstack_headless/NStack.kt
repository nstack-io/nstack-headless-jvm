package dk.nodes.nstack_headless

import dk.nodes.nstack_headless.common.dependency_injection.Provider
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class NStack(
    private val configuration: Configuration,
) : Provider<Configuration> {

    fun getTranslation(
        sectionKey: String,
        translationKey: String,
        language: String,
    ): CompletableFuture<String> {
        return CompletableFuture.completedFuture("Hello")
    }

    override fun provide() = configuration
}