package me.jfenn.gitrest.util

import me.jfenn.gitrest.model.ProviderString

/**
 * Provides a set of "default" hardcoded provider/context mappings.
 *
 * Should be trusted only as fallback behavior if no other information
 * is provided.
 */
val DEFAULT_PROVIDERS = arrayOf(
    ProviderString("github", "api.github.com"),
    ProviderString("gitlab", "gitlab.com"),
    ProviderString("gitea", "codeberg.org")
)