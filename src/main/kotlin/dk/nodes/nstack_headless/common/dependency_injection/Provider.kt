package dk.nodes.nstack_headless.common.dependency_injection

internal interface Provider<T> {
    fun provide(): T
}
