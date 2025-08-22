package com.itachi1706.abp.gitrest.provider.gitlab.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.itachi1706.abp.gitrest.model.User
import com.itachi1706.abp.gitrest.util.OptionalStringSerializer

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