package com.itachi1706.abp.gitrest.model

import kotlinx.serialization.json.Json
import com.itachi1706.abp.gitrest.base.ServiceBuilder
import com.itachi1706.abp.gitrest.provider.gitea.GiteaProvider
import com.itachi1706.abp.gitrest.provider.github.GithubProvider
import com.itachi1706.abp.gitrest.provider.gitlab.GitlabProvider
import com.itachi1706.abp.gitrest.service.Cache
import com.itachi1706.abp.gitrest.service.MemoryCache

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