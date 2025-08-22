package com.itachi1706.abp.gitrest.provider.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.itachi1706.abp.gitrest.model.License

@Serializable
class GithubLicense : License() {
    @SerialName("key")
    override var id: String = ""

    @SerialName("name")
    override val name: String? = null

    @SerialName("description")
    override val description: String? = null

    @SerialName("body")
    override val body: String? = null

    @SerialName("html_url")
    override val infoUrl: String? = null

    @SerialName("permissions")
    override val permissions: Array<String>? = null

    @SerialName("conditions")
    override val conditions: Array<String>? = null

    @SerialName("limitations")
    override val limitations: Array<String>? = null
}