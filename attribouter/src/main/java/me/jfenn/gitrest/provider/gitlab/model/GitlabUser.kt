package me.jfenn.gitrest.provider.gitlab.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.jfenn.gitrest.model.User
import me.jfenn.gitrest.util.OptionalStringSerializer

@Serializable
class GitlabUser : User() {

    @SerialName("username")
    override var id: String = ""

    @SerialName("name")
    @Serializable(with = OptionalStringSerializer::class)
    override val name: String? = null

    @SerialName("web_url")
    override val url: String? = null

    @SerialName("avatar_url")
    override val avatarUrl: String? = null

    @SerialName("website_url")
    @Serializable(with = OptionalStringSerializer::class)
    override val websiteUrl: String? = null

    @SerialName("public_email")
    @Serializable(with = OptionalStringSerializer::class)
    override val email: String? = null

    @SerialName("bio")
    @Serializable(with = OptionalStringSerializer::class)
    override val bio: String? = null

}