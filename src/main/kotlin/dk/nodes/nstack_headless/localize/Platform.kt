package dk.nodes.nstack_headless.localize

internal enum class Platform(val platformId: String) {
    MOBILE("mobile"),
    WEB("web"),
    BACKEND("backend");

    override fun toString(): String {
        return platformId
    }
}