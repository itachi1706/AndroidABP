package com.itachi1706.abp.attribouter.dialogs

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.adapters.WedgeAdapter
import com.itachi1706.abp.attribouter.utils.ResourceUtils.getString
import com.itachi1706.abp.attribouter.utils.getThemeAttr
import com.itachi1706.abp.attribouter.wedges.Wedge
import com.itachi1706.abp.utils.autoSystemUiColors
import com.itachi1706.abp.utils.bind

open class OverflowDialog(
        context: Context,
        val title: String,
        val items: List<Wedge<*>>
) : AppCompatDialog(
        context,
        context.getThemeAttr(R.attr.attribouter_overflowDialogTheme, R.style.AttribouterTheme_Dialog_Fullscreen)
) {

    private val toolbar: Toolbar? by bind(R.id.toolbar)
    private val recyclerView: RecyclerView? by bind(R.id.recycler)

    override fun onStart() {
        super.onStart()
        window?.autoSystemUiColors()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attribouter_dialog_overflow)

        toolbar?.apply {
            title = getString(context, this@OverflowDialog.title)
            setNavigationOnClickListener { dismiss() }
        }

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WedgeAdapter(items)
        }
    }

}