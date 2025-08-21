package me.jfenn.gitrest.model

open class Repo : DelegateResource() {
    open val description: String? = null
    open val url: String? = null
    open val websiteUrl: String? = null
    open val gitUrlHttp: String? = null
    open val gitUrlSsh: String? = null
    open val license: License? = null
    open val defaultBranch: String? = null

    override fun setId(providerString: ProviderString) {
        super.setId(providerString)
        license?.setId(providerString)
    }

    open fun getRawFileUrl(branchName: String = defaultBranch!!, filePath: String) : String? = null
}