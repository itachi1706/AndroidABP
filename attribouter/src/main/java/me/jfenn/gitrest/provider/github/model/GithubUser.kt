package me.jfenn.gitrest.provider.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.jfenn.gitrest.model.User
import me.jfenn.gitrest.util.OptionalStringSerializer

@Serializable
class GithubUser : User() {

    @SerialName("login")
    override var id: String = ""

    @SerialName("name")
    @Serializable(with = OptionalStringSerializer::class)
    override val name: String? = null

    @SerialName("html_url")
    override val url: String? = null

    @SerialName("avatar_url")
    override val avatarUrl: String? = null

    @SerialName("blog")
    @Serializable(with = OptionalStringSerializer::class)
    override val websiteUrl: String? = null

    @SerialName("email")
    @Serializable(with = OptionalStringSerializer::class)
    override val email: String? = null

    @SerialName("bio")
    @Serializable(with = OptionalStringSerializer::class)
    override val bio: String? = null

}