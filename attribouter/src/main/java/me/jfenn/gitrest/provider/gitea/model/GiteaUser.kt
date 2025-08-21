package me.jfenn.gitrest.provider.gitea.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.jfenn.gitrest.model.User
import me.jfenn.gitrest.util.OptionalStringSerializer

@Serializable
class GiteaUser : User() {

    @SerialName("login")
    override var id: String = ""

    @SerialName("full_name")
    @Serializable(with = OptionalStringSerializer::class)
    override val name: String? = null

    // hack to provide user URLs - Gitea's API doesn't provide an html_url field
    override val url: String?
        get() = "https://${hostname}/${id}"

    @SerialName("avatar_url")
    override val avatarUrl: String? = null

    @SerialName("email")
    @Serializable(with = OptionalStringSerializer::class)
    override val email: String? = null

}