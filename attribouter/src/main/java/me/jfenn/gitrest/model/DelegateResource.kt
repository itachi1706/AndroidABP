package me.jfenn.gitrest.model

import kotlinx.serialization.Transient

open class DelegateResource {

    @Transient
    open var id: String = ""
    @Transient
    open var hostname: String = ""
    @Transient
    open var provider: String = ""

    @Transient val providerString: ProviderString get() = ProviderString(provider, hostname, id)

    internal open fun setId(providerString: ProviderString) {
        this.hostname = providerString.hostname
        this.provider = providerString.provider

        if (id.isBlank())
            throw RuntimeException("GIT-REST: Malformed response, .id not provided in ${this::class.simpleName}, requested $providerString")
    }

}