package me.jfenn.gitrest.model

import android.util.Log
import me.jfenn.gitrest.util.DEFAULT_PROVIDERS

class ProviderString {

    val provider: String
    val hostname : String
    val id: String

    /**
     * Internal-only constructor; used only for DEFAULT_PROVIDERS
     */
    internal constructor(provider: String, hostname: String) {
        this.provider = provider
        this.hostname = hostname
        this.id = ""
    }

    constructor(provider: String, hostname: String, id: String) : this("$provider@$hostname:$id")

    constructor(str: String) {
        if (str.isEmpty()) throw RuntimeException("GIT-REST: Empty ProviderString")

        // TODO: parse URL as provider-string (split into context:id with no known provider) - partial support for HTTP and SSH uris wouldn't be bad (ignore `git` provider, strip off `*.git` from end of ids, etc...)

        val providerRegex = """(?:(\w*)(?:@([\w.]*))?:)?([\w-_./+]*)""".toRegex()
        val hostnameRegex = """(\w+\.\w+)$""".toRegex()

        val (provider, hostname, id) = providerRegex.matchEntire(str)?.destructured ?: throw RuntimeException("GIT-REST: Unable to parse ProviderString '$str'")

        if (provider.isEmpty() && hostname.isEmpty()) {
            // neither a provider or hostname were specified; just use the first default
            val default = DEFAULT_PROVIDERS.first()
            this.provider = default.provider
            this.hostname = default.hostname
        } else if (provider.isEmpty() || provider == "git") {
            // missing provider: find the first provider with a matching hostname
            val default = DEFAULT_PROVIDERS.find {
                hostnameRegex.find(it.hostname)?.value == hostnameRegex.find(hostname)?.value
            } ?: throw RuntimeException("GIT-REST: Unable to find a default provider for '$str'")

            this.provider = default.provider
            this.hostname = default.hostname
        } else if (hostname.isEmpty()) {
            // missing context: find the first hostname that provides the interface
            val default = DEFAULT_PROVIDERS.find {
                it.provider == provider
            } ?: throw RuntimeException("GIT-REST: Unable to find a default context for '$str'")

            this.provider = provider
            this.hostname = default.hostname
        } else {
            // if a matching default provider exists, use its hostname; this way, "api.root.tld" and "root.tld" can be resolved
            val default = DEFAULT_PROVIDERS.find {
                it.provider == provider && hostnameRegex.find(it.hostname)?.value == hostnameRegex.find(hostname)?.value
            }

            this.provider = provider
            this.hostname = default?.hostname ?: hostname
        }

        this.id = id
    }

    /**
     * Returns the inferred URL from the given context/id, if possible.
     *
     * If the hostname starts with "api.", it is excluded from the generated
     * URL. In other words, "github@api.github.com:fennifith/Attribouter" will
     * turn into "https://github.com/fennifith/Attribouter".
     *
     * While this should be a _decently_ reliable estimate, it will not always
     * be correct, so fetching a Repo or User object and checking its url should
     * always be preferred over this!
     */
    fun inferUrl(type: UrlType = UrlType.HTTPS) : String {
        return type.getUrl(hostname.removePrefix("api."), id)
    }

    override fun toString(): String {
        return "$provider@$hostname:$id"
    }

    override fun equals(other: Any?): Boolean {
        return (other as? ProviderString)?.let {
            provider == it.provider && hostname == other.hostname && id == it.id
        } ?: super.equals(other)
    }

    override fun hashCode(): Int {
        var result = provider.hashCode()
        result = 31 * result + hostname.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}