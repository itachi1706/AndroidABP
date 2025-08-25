package com.itachi1706.abp.attribouter.wedges

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.adapters.WedgeAdapter
import com.itachi1706.abp.attribouter.utils.ResourceUtils

open class TextWedge : Wedge<TextWedge.ViewHolder>(R.layout.attribouter_item_text) {

    private val text: String? by Attr("text")
    private val isCentered: Boolean by Attr("centered", false)

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override fun bind(context: Context, viewHolder: ViewHolder) {
        viewHolder.textView?.apply {
            movementMethod = LinkMovementMethod()

            val string = ResourceUtils.getString(context, this@TextWedge.text)
            @Suppress("DEPRECATION") // Deprecation suppression as we need it for older versions
            text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(string, 0) else Html.fromHtml(string)

            textAlignment = if (isCentered) View.TEXT_ALIGNMENT_CENTER else View.TEXT_ALIGNMENT_GRAVITY
        }

        val links = getTypedChildren<LinkWedge>().filter { !it.isHidden }.sorted()
        viewHolder.links?.apply {
            if (links.isNotEmpty()) {
                adapter = WedgeAdapter(links)
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = if (isCentered) JustifyContent.CENTER else JustifyContent.FLEX_START
                }

                visibility = View.VISIBLE
            } else visibility = View.GONE
        }
    }

    open class ViewHolder(v: View) : Wedge.ViewHolder(v) {
        var textView: TextView? = v.findViewById(R.id.text)
        var links: RecyclerView? = v.findViewById(R.id.links)
    }

}
