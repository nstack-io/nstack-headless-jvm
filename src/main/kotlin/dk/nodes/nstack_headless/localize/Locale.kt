package dk.nodes.nstack_headless.localize

internal class Locale(languageCode: String, countryCode: String) {

    private val languageCode: String
    private val countryCode: String

    private val codeValidationRegex = Regex("[a-zA-Z]+")

    init {
        require(codeValidationRegex.matches(languageCode))
        require(codeValidationRegex.matches(countryCode))

        this.languageCode = languageCode.lowercase()
        this.countryCode = countryCode.uppercase()
    }

    override fun toString(): String {
        return "$languageCode-$countryCode"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Locale

        if (languageCode != other.languageCode) return false
        if (countryCode != other.countryCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = languageCode.hashCode()
        result = 31 * result + countryCode.hashCode()
        return result
    }
}