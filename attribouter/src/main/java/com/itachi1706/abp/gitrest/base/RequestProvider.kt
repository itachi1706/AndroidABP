package com.itachi1706.abp.gitrest.base

import com.itachi1706.abp.gitrest.model.License
import com.itachi1706.abp.gitrest.model.Repo
import com.itachi1706.abp.gitrest.model.User

interface RequestProvider {

    suspend fun getUser(str: String): User?

    suspend fun getRepo(str: String): Repo?

    suspend fun getRepoContributors(str: String): List<User>?

    suspend fun getLicense(str: String): License?

}