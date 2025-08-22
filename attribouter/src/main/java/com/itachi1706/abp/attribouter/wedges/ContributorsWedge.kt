package com.itachi1706.abp.attribouter.wedges

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.itachi1706.abp.attribouter.addDefaults
import com.itachi1706.abp.gitrest.model.User

open class ContributorsWedge : ListWedge("@string/attribouter_title_contributors", false) {

    var repo: String? by attr("repo")
    var showDefaults: Boolean by attr("showDefaults", false)

    override fun onCreate() {
        if (showDefaults)
            addDefaults()

        repo?.let { requestContributors(it) }
    }

    fun requestContributors(repo: String) {
        lifecycle?.launch {
            withContext(Dispatchers.IO) {
                lifecycle?.client?.getRepoContributors(repo)
            }?.forEach { contributor ->
                onContributor(contributor)
            }
        }
    }

    fun onContributor(data: User) {
        val contributor = ContributorWedge(
                task = if (repo?.startsWith(data.id) == true) "Owner" else "Contributor"
        ).apply { onContributor(data) }

        addChild(0, contributor.create(lifecycle))
        notifyItemChanged()
    }

    override fun getListItems(): List<Wedge<*>> {
        return getTypedChildren<ContributorWedge>().filter { !it.isHidden }.sorted()
    }

}
