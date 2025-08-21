package me.jfenn.gitrest.provider.gitlab.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.jfenn.gitrest.model.License

@Serializable
class GitlabLicense : License() {

    @SerialName("key")
    override var id: String = ""

    @SerialName("nickname")
    override val name: String? = null

    @SerialName("name")
    val fullName: String? = null

    @SerialName("description")
    override val description: String? = null

    @SerialName("content")
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