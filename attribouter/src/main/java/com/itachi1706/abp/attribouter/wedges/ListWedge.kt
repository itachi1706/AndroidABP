package com.itachi1706.abp.attribouter.wedges

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itachi1706.abp.attribouter.R
import com.itachi1706.abp.attribouter.adapters.WedgeAdapter
import com.itachi1706.abp.attribouter.dialogs.OverflowDialog
import com.itachi1706.abp.attribouter.utils.ResourceUtils

abstract class ListWedge(
        defaultTitle: String,
        isCardLayout: Boolean = true
) : Wedge<ListWedge.ViewHolder>(
        if (isCardLayout) R.layout.attribouter_item_list_card else R.layout.attribouter_item_list
) {

    var title: String by Attr("title", defaultTitle)
    var overflow: Int by Attr("overflow", Int.MAX_VALUE)

    val displayItems = ArrayList<Wedge<*>>()
    val itemsAdapter = WedgeAdapter(displayItems)

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    open fun getListItems(): List<Wedge<*>> {
        return getChildren()
    }

    override fun bind(context: Context, viewHolder: ViewHolder) {
        viewHolder.titleView?.apply {
            visibility = if (overflow > 0) {
                text = ResourceUtils.getString(context, title)
                View.VISIBLE
            } else View.GONE
        }

        val items = getListItems()
        displayItems.clear()
        displayItems.addAll(items.take(overflow))

        viewHolder.recycler?.apply {
            visibility = if (displayItems.isNotEmpty()) {
                layoutManager = LinearLayoutManager(context)
                adapter = itemsAdapter
                View.VISIBLE
            } else View.GONE
        }

        viewHolder.expand?.apply {
            visibility = if (overflow != 0 && displayItems.size < items.size) {
                setOnClickListener { v ->
                    OverflowDialog(
                            v.context,
                            title,
                            items
                    ).show()
                }

                View.VISIBLE
            } else View.GONE
        }

        viewHolder.overflow?.apply {
            visibility = if (overflow == 0) {
                text = ResourceUtils.getString(context, title)
                viewHolder.itemView.setOnClickListener { v ->
                    OverflowDialog(v.context, title, items).show()
                }
                View.VISIBLE
            } else {
                viewHolder.itemView.setOnClickListener(null)
                View.GONE
            }
        }
    }

    override fun onItemChanged(changed: Wedge<*>) {
        itemsAdapter.notifyItemChanged(displayItems.indexOf(changed))
    }

    open class ViewHolder(v: View) : Wedge.ViewHolder(v) {
        var titleView: TextView? = v.findViewById(R.id.header)
        var expand: View? = v.findViewById(R.id.expand)
        var overflow: TextView? = v.findViewById(R.id.overflow)
        var recycler: RecyclerView? = v.findViewById(R.id.recycler)
    }

}
