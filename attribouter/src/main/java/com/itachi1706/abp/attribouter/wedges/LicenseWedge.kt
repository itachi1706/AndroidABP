package com.itachi1706.abp.attribouter.wedges

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.adapters.WedgeAdapter
import com.itachi1706.abp.attribouter.utils.ResourceUtils
import com.itachi1706.abp.attribouter.utils.equalsProvider
import com.itachi1706.abp.attribouter.utils.toTitleString
import com.itachi1706.abp.gitrest.model.License
import com.itachi1706.abp.gitrest.model.ProviderString
import com.itachi1706.abp.gitrest.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class LicenseWedge(
    repo: String? = null,
    title: String? = null,
    description: String? = null,
    licenseName: String? = null,
    repoUrl: String? = null,
    websiteUrl: String? = null,
    licenseUrl: String? = null,
    var licensePermissions: Array<String>? = null,
    var licenseConditions: Array<String>? = null,
    var licenseLimitations: Array<String>? = null,
    var licenseDescription: String? = null,
    licenseBody: String? = null,
    licenseKey: String? = null
) : Wedge<LicenseWedge.ViewHolder>(R.layout.attribouter_item_license) {

    var repo: String? by Attr("repo", repo)
    var title: String? by Attr("title", title)
    var description: String? by Attr("description", description)
    var licenseName: String? by Attr("licenseName", licenseName)
    var repoUrl: String? by Attr("repoUrl", repoUrl)
    var websiteUrl: String? by Attr("websiteUrl", websiteUrl)
    var licenseUrl: String? by Attr("licenseUrl", licenseUrl)
    var licenseBody: String? by Attr("licenseBody", licenseBody)
    var licenseKey: String? by Attr("license", licenseKey)

    private var token: String? = null

    override fun onCreate() {
        token = repo ?: title
        initChildren()

        if (!hasAllGeneric()) repo?.let {
            lifecycle?.launch {
                withContext(Dispatchers.IO) {
                    lifecycle?.client?.getRepo(it)
                }?.let { data -> onRepository(data) }
            }
        }

        licenseKey?.let { key ->
            lifecycle?.launch {
                withContext(Dispatchers.IO) {
                    lifecycle?.client?.getLicense("github:$key")
                }?.let { onLicense(it) }
            }
        }
    }

    fun initChildren() {
        // try to guess repository URL from id
        (repoUrl ?: repo?.let { ProviderString(it).inferUrl() })?.let { url ->
            repo?.let { repoId ->
                val id = ProviderString(repoId)
                addChild(
                    RepoLinkWedge(
                        name = id.provider.toTitleString(),
                        url = url,
                        icon = "@drawable/attribouter_ic_${id.provider}",
                        priority = 0
                    ).create(lifecycle)
                )
            } ?: addChild(RepoLinkWedge(url = url, priority = 1).create(lifecycle))
        }

        websiteUrl?.let { addChild(WebsiteLinkWedge(it, 2)) }
        if (!licenseBody.isNullOrEmpty() && !licenseUrl.isNullOrEmpty())
            addChild(LicenseLinkWedge(this, 0))
    }

    private fun onRepository(data: Repo) {
        description = data.description
        licenseName = data.license?.name ?: data.license?.id
        repoUrl = data.url
        websiteUrl = data.websiteUrl

        data.license?.id.let { id ->
            if (!hasAllLicense()) lifecycle?.launch {
                withContext(Dispatchers.IO) {
                    lifecycle?.client?.getLicense("github:$id")
                }?.let { onLicense(it) }
            }
        }

        initChildren()
        notifyItemChanged()
    }

    private fun onLicense(data: License) {
        licenseName = data.name
        licenseDescription = data.description
        licenseUrl = data.infoUrl

        licensePermissions = data.permissions
        licenseConditions = data.conditions
        licenseLimitations = data.limitations

        licenseBody = data.body

        initChildren()
        notifyItemChanged()
    }

    fun hasAllGeneric(): Boolean {
        return (description?.startsWith("^") ?: false)
                && (websiteUrl?.startsWith("^") ?: false)
                && (licenseName?.startsWith("^") ?: false)
    }

    fun hasAllLicense(): Boolean {
        return (licenseName?.startsWith("^") ?: false)
                && (licenseUrl?.startsWith("^") ?: false)
                && (licenseBody?.startsWith("^") ?: false)
    }

    override fun equals(other: Any?): Boolean {
        return (other as? LicenseWedge)?.let {
            repo.equalsProvider(other.repo)
                    || repo?.equals(it.title, ignoreCase = true) ?: false
                    || title?.equals(it.repo, ignoreCase = true) ?: false
                    || title?.equals(it.title, ignoreCase = true) ?: false
        } ?: super.equals(other)
    }

    override fun hashCode(): Int {
        return if (repo == null) super.hashCode() else ProviderString(repo!!).hashCode()
    }

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override fun bind(context: Context, viewHolder: ViewHolder) {
        viewHolder.titleView?.apply {
            (title ?: repo?.toTitleString())?.let { text = ResourceUtils.getString(context, it) }
        }

        viewHolder.descriptionView?.apply {
            text = ResourceUtils.getString(context, description)?.replace("\n", "")
        }

        viewHolder.licenseView?.apply {
            licenseName?.let {
                text = ResourceUtils.getString(context, licenseName)
                visibility = View.VISIBLE
            } ?: run {
                visibility = View.GONE
            }
        }

        val links = getTypedChildren<LinkWedge>().filter { !it.isHidden }.sorted()

        viewHolder.links?.apply {
            if (links.isNotEmpty()) {
                visibility = View.VISIBLE

                if (adapter != null) {
                    // fix for google/flexbox-layout#349 - don't re-apply the layout manager to a recycled view
                    swapAdapter(WedgeAdapter(links), true)
                    return@apply
                }

                adapter = WedgeAdapter(links)
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.FLEX_START
                }
            } else visibility = View.GONE
        }

        viewHolder.itemView.apply {
            var importantLink: LinkWedge? = null
            var clickListener: View.OnClickListener? = null
            for (link in links) {
                if (importantLink == null || link.priority > importantLink.priority) {
                    val listener = link.getListener(context)
                    if (listener != null) {
                        clickListener = listener
                        importantLink = link
                    }
                }
            }

            setOnClickListener(clickListener)
        }
    }

    open class ViewHolder(v: View) : Wedge.ViewHolder(v) {
        var titleView: TextView? = v.findViewById(R.id.title)
        var descriptionView: TextView? = v.findViewById(R.id.description)
        var licenseView: TextView? = v.findViewById(R.id.license)
        var links: RecyclerView? = v.findViewById(R.id.projectLinks)
    }

}
