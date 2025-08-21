package me.jfenn.gitrest.base

import me.jfenn.gitrest.model.License
import me.jfenn.gitrest.model.Repo
import me.jfenn.gitrest.model.User

interface RequestProvider {

    suspend fun getUser(str: String): User?

    suspend fun getRepo(str: String): Repo?

    suspend fun getRepoContributors(str: String): List<User>?

    suspend fun getLicense(str: String): License?

}