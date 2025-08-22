package me.jfenn.gitrest.provider.github

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import me.jfenn.gitrest.base.RequestProvider
import me.jfenn.gitrest.base.ServiceBuilder
import me.jfenn.gitrest.model.GitrestConfig
import me.jfenn.gitrest.provider.github.model.GithubLicense
import me.jfenn.gitrest.provider.github.model.GithubRepo
import me.jfenn.gitrest.provider.github.model.GithubUser

class GithubProvider(
    val client: HttpClient = HttpClient()
) : RequestProvider {

    override suspend fun getUser(str: String): GithubUser? = client.get("users/${str}").body()

    override suspend fun getRepo(str: String): GithubRepo? = client.get("repos/${str}").body()

    override suspend fun getRepoContributors(str: String): List<GithubUser>? = client.get("repos/${str}/contributors").body()

    override suspend fun getLicense(str: String): GithubLicense? = client.get("licenses/${str}").body()

    companion object: ServiceBuilder<GithubProvider> {
        override val key = "github"
        override val tokens: MutableMap<String, String> = HashMap()

        override fun create(config: GitrestConfig, hostname: String): GithubProvider {
            val client = config.ktor {
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = hostname
                    }

                    header("Accept", "application/vnd.github.v3+json")
                    tokens[hostname]?.let { header("Authorization", "token $it") }
                }
            }

            return GithubProvider(client)
        }

    }

}