package com.itachi1706.abp.gitrest.provider.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.itachi1706.abp.gitrest.model.Repo
import com.itachi1706.abp.gitrest.util.OptionalStringSerializer

@Serializable
class GithubRepo : Repo() {

    @SerialName("full_name")
    override var id: String = ""

    @SerialName("description")
    @Serializable(with = OptionalStringSerializer::class)
    override val description: String? = null

    @SerialName("html_url")
    override val url: String? = null

    @SerialName("homepage")
    @Serializable(with = OptionalStringSerializer::class)
    override val websiteUrl: String? = null

    @SerialName("clone_url")
    override val gitUrlHttp: String? = null

    @SerialName("ssh_url")
    override val gitUrlSsh: String? = null

    @SerialName("license")
    override val license: GithubLicense? = null

    @SerialName("default_branch")
    override val defaultBranch: String? = null

    override fun getRawFileUrl(branchName: String, filePath: String) : String? {
        return "https://raw.githubusercontent.com/${id}/${branchName}/${filePath}"
    }

}