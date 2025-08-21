package me.jfenn.gitrest.model

import kotlinx.serialization.json.Json
import me.jfenn.gitrest.base.ServiceBuilder
import me.jfenn.gitrest.provider.gitea.GiteaProvider
import me.jfenn.gitrest.provider.github.GithubProvider
import me.jfenn.gitrest.provider.gitlab.GitlabProvider
import me.jfenn.gitrest.service.Cache
import me.jfenn.gitrest.service.MemoryCache

class GitrestConfig {

    var providers: Array<ServiceBuilder<*>> = arrayOf(
        GithubProvider,
        GitlabProvider,
        GiteaProvider
    )

    var strictMode: Boolean = false

    var logDebug: (String) -> Unit = {} // TODO: implement a more robust log handler + check BuildConfig.DEBUG before logging
    var logError: (String) -> Unit = { println(it) }

    var jsonSerializer = Json {
        ignoreUnknownKeys = true
    }

    var cache : Cache = MemoryCache()

    var platformConfig : PlatformConfig = PlatformConfig()

    fun platform(block: PlatformConfig.() -> Unit) {
        platformConfig.apply { block() }
    }

}