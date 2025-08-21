package me.jfenn.gitrest.provider.gitlab

import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import me.jfenn.gitrest.base.RequestProvider
import me.jfenn.gitrest.base.ServiceBuilder
import me.jfenn.gitrest.model.GitrestConfig
import me.jfenn.gitrest.provider.gitlab.model.GitlabLicense
import me.jfenn.gitrest.provider.gitlab.model.GitlabRepo
import me.jfenn.gitrest.provider.gitlab.model.GitlabUser

class GitlabProvider(
    val client: HttpClient
) : RequestProvider {

    override suspend fun getUser(str: String): GitlabUser? {
        val userList = client.get<List<GitlabUser>>("api/v4/users?username=${str}")
        return userList.firstOrNull()
    }

    // TODO: create proper url-encode utility function instead of String.replace
    override suspend fun getRepo(str: String): GitlabRepo? = client.get("api/v4/projects/${str.replace("/", "%2F")}?license=true")

    override suspend fun getRepoContributors(str: String): List<GitlabUser>? = client.get("api/v4/projects/${str.replace("/", "%2F")}/users")

    override suspend fun getLicense(str: String): GitlabLicense? = client.get("api/v4/templates/licenses/${str}")

    companion object: ServiceBuilder<GitlabProvider> {
        override val key = "gitlab"
        override val tokens: MutableMap<String, String> = HashMap()

        override fun create(config: GitrestConfig, hostname: String): GitlabProvider {
            val client = config.ktor {
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = hostname
                    }

                    tokens[hostname]?.let { header("Authorization", "Bearer $it") }
                }
            }

            return GitlabProvider(client)
        }

    }

}