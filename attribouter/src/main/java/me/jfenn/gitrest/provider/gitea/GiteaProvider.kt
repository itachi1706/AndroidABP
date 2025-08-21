package me.jfenn.gitrest.provider.gitea

import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import me.jfenn.gitrest.base.RequestProvider
import me.jfenn.gitrest.base.ServiceBuilder
import me.jfenn.gitrest.model.GitrestConfig
import me.jfenn.gitrest.model.License
import me.jfenn.gitrest.provider.gitea.model.GiteaRepo
import me.jfenn.gitrest.provider.gitea.model.GiteaUser

class GiteaProvider(
    val client: HttpClient = HttpClient()
) : RequestProvider {

    override suspend fun getUser(str: String): GiteaUser? = client.get("api/v1/users/${str}")

    override suspend fun getRepo(str: String): GiteaRepo? = client.get("api/v1/repos/${str}")

    override suspend fun getRepoContributors(str: String): List<GiteaUser>? = client.get("api/v1/repos/${str}/collaborators")

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