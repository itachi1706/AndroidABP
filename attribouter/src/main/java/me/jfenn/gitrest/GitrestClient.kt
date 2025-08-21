package me.jfenn.gitrest

import me.jfenn.gitrest.base.RequestProvider
import me.jfenn.gitrest.model.DelegateResource
import me.jfenn.gitrest.model.GitrestConfig
import me.jfenn.gitrest.model.License
import me.jfenn.gitrest.model.ProviderString
import me.jfenn.gitrest.model.Repo
import me.jfenn.gitrest.model.User

fun gitrest(block: GitrestConfig.() -> Unit) : GitrestClient {
    val config = GitrestConfig().apply { block() }
    return GitrestClient(config)
}

class GitrestClient(
    private val config : GitrestConfig
) : RequestProvider {

    private val providers: MutableMap<String, RequestProvider> = HashMap()

    private suspend fun <T> get(str: String, endpointKey: String, block: suspend (RequestProvider, String) -> T?) : T? {
        val providerStr = ProviderString(str)
        val requestKey = "$providerStr:$endpointKey"

        config.cache.get<T>(requestKey)?.let {
            config.logDebug("GIT-REST: Returning cached entry for $providerStr")
            return it
        }

        val providerId = "${providerStr.provider}@${providerStr.hostname}"
        val provider = providers[providerId] ?: run {
            config.providers.firstOrNull { it.key == providerStr.provider }?.create(config, providerStr.hostname)?.also { it ->
                providers[providerId] = it
            } ?: throw RuntimeException("Provider not found: $providerId")
            // TODO: query random endpoints to determine provider
            // - /status for GitHub - { "message": "GitHub**" }
            // - /version might be a giveaway for gitea/gitlab (GitLab's contains a "revision" property)...
            // - alternatively, determine from header info?
        }

        return try {
            block(provider, providerStr.id)?.also { resource ->
                (resource as? DelegateResource)?.setId(providerStr) ?: (resource as? List<DelegateResource>)?.forEach {
                    it.setId(providerStr)
                }
            }?.also {
                config.cache.set(requestKey, it as Any)
            }
        } catch (e: Exception) {
            if (config.strictMode) throw e
            else config.logError("GIT-REST: Error fetching ${str}: ${e::class.simpleName} - ${e.message}")
            null
        }
    }

    override suspend fun getUser(str: String): User? = get(str, "getUser") { it, id -> it.getUser(id) }
    override suspend fun getRepo(str: String): Repo? = get(str, "getRepo") { it, id -> it.getRepo(id) }
    override suspend fun getRepoContributors(str: String): List<User>? = get(str, "getRepoContributors") { it, id -> it.getRepoContributors(id) }
    override suspend fun getLicense(str: String): License? = get(str, "getLicense") { it, id -> it.getLicense(id) }

}