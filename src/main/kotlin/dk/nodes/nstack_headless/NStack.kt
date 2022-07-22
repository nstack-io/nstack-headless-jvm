package dk.nodes.nstack_headless

import dk.nodes.nstack.kotlin.models.Language
import dk.nodes.nstack.kotlin.models.Result
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class NStack @JvmOverloads constructor(
    private val configuration: Configuration,
    private val executor: ExecutorService = Executors.newCachedThreadPool(),
) {

    fun getTranslation(
        section: String,
        key: String,
        language: Language
    ): Future<Result<String>> {
        return future(executor) {
            Result.Success("Hello")
        }
    }
}