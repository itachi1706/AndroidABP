package com.itachi1706.abp.gitrest.provider.gitea

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import com.itachi1706.abp.gitrest.base.RequestProvider
import com.itachi1706.abp.gitrest.base.ServiceBuilder
import com.itachi1706.abp.gitrest.model.GitrestConfig
import com.itachi1706.abp.gitrest.model.License
import com.itachi1706.abp.gitrest.provider.gitea.model.GiteaRepo
import com.itachi1706.abp.gitrest.provider.gitea.model.GiteaUser

class GiteaProvider(
    val client: HttpClient = HttpClient()
) : RequestProvider {

    override suspend fun getUser(str: String): GiteaUser? = client.get("api/v1/users/${str}").body()

    override suspend fun getRepo(str: String): GiteaRepo? = client.get("api/v1/repos/${str}").body()

    override suspend fun getRepoContributors(str: String): List<GiteaUser>? = client.get("api/v1/repos/${str}/collaborators").body()

    override suspend fun getLicense(str: String): License? = null

    companion object: ServiceBuilder<GiteaProvider> {
        override val key = "gitea"
        override val tokens: MutableMap<String, String> = HashMap()

        override fun create(config: GitrestConfig, hostname: String): GiteaProvider {
            val client = config.ktor {
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = hostname
                    }

                    tokens[hostname]?.let { header("Authorization", "token $it") }
                }
            }

            return GiteaProvider(client)
        }

    }

}