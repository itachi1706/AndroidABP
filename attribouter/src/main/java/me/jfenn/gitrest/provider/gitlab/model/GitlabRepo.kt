package me.jfenn.gitrest.provider.gitlab.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.jfenn.gitrest.model.Repo
import me.jfenn.gitrest.util.OptionalStringSerializer

@Serializable
class GitlabRepo : Repo() {

    @SerialName("path_with_namespace")
    override var id: String = ""

    @SerialName("description")
    @Serializable(with = OptionalStringSerializer::class)
    override val description: String? = null

    @SerialName("web_url")
    override val url: String? = null

    @SerialName("http_url_to_repo")
    override val gitUrlHttp: String? = null

    @SerialName("ssh_url_to_repo")
    override val gitUrlSsh: String? = null

    @SerialName("license")
    override val license: GitlabLicense? = null

    @SerialName("default_branch")
    override val defaultBranch: String? = null

    override fun getRawFileUrl(branchName: String, filePath: String) : String? {
        return "${url}/raw/${branchName}/${filePath}"
    }

}