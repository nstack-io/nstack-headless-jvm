package dk.nodes.nstack_headless

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

internal fun<T> future(executor: ExecutorService, function: () -> T): Future<T> {
    return executor.submit(function)
}