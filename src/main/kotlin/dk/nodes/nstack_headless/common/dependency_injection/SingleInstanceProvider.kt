package dk.nodes.nstack_headless.common.dependency_injection

internal abstract class SingleInstanceProvider<T> : Provider<T> {

    private var instance: T? = null

    abstract fun createInstance(): T

    override fun provide(): T {
        if (instance == null) {
            this.instance = createInstance()
        }

        return this.instance!!
    }
}