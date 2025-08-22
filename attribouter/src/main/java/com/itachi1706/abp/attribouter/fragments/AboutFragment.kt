package com.itachi1706.abp.attribouter.fragments

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itachi1706.abp.attribouter.adapters.WedgeAdapter
import com.itachi1706.abp.attribouter.Attribouter
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.interfaces.Notifiable
import com.itachi1706.abp.attribouter.provider.LifecycleInstance
import com.itachi1706.abp.attribouter.provider.wedge.XMLWedgeProvider
import com.itachi1706.abp.attribouter.wedges.Wedge
import com.itachi1706.abp.gitrest.provider.gitea.GiteaProvider
import com.itachi1706.abp.gitrest.provider.github.GithubProvider
import com.itachi1706.abp.gitrest.provider.gitlab.GitlabProvider

class AboutFragment : Fragment(), Notifiable {

    private var recycler: RecyclerView? = null
    private var adapter: WedgeAdapter? = null

    private var wedges: List<Wedge<*>>? = null
    private var tokens = HashMap<String, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = arguments
        var fileRes = R.xml.attribouter
        var themeRes = R.style.AttribouterTheme_DayNight
        if (args != null) {
            themeRes = args.getInt(Attribouter.Companion.EXTRA_THEME_RES, themeRes)
            fileRes = args.getInt(Attribouter.Companion.EXTRA_FILE_RES, fileRes)

            // parse hostname args
            args.keySet().filter { it.startsWith(Attribouter.Companion.EXTRA_TOKEN) }.forEach { key ->
                val hostname = key.substring(Attribouter.Companion.EXTRA_TOKEN.length)
                val token = args.getString(key, "")
                if (token.isNotBlank())
                    tokens[hostname] = token
            }
        }

        val layoutInflater = inflater.cloneInContext(
            ContextThemeWrapper(
                inflater.context,
                themeRes
            )
        )
        recycler = layoutInflater.inflate(R.layout.attribouter_fragment_about, container, false) as RecyclerView

        val parser = resources.getXml(fileRes)
        val provider = XMLWedgeProvider(parser)
        val lifecycle = LifecycleInstance(
            requireContext(),
            providers = listOf(
                GithubProvider,
                GitlabProvider,
                GiteaProvider
            ).map {
                it.apply {
                    tokens.putAll(this@AboutFragment.tokens)
                }
            },
            scope = viewLifecycleOwner.lifecycleScope,
            notifiable = this
        )

        wedges = provider.map { _, wedge ->
            wedge.withWedgeProvider(provider).create(lifecycle)
        }.getAllWedges().also { wedges ->
            adapter = WedgeAdapter(wedges)
            recycler?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@AboutFragment.adapter
            }
        }

        parser.close()
        return recycler
    }

    override fun onItemChanged(changed: Wedge<*>) {
        wedges?.indexOf(changed)?.let {
            recycler?.post { adapter?.notifyItemChanged(it) }
        }
    }
}