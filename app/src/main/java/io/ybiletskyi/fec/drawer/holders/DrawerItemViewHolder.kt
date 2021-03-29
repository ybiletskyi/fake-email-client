package io.ybiletskyi.fec.drawer.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.drawer.DrawerHelper

class DrawerItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val indicator = view.findViewById<View>(R.id.indicator)
    private val title = view.findViewById<TextView>(R.id.title)
    private var itemClickListener: ((Int) -> Unit)? = null

    init {
        view.setOnClickListener {
            itemClickListener?.invoke(adapterPosition)
        }
    }

    fun onBind(folder: DrawerHelper.DrawerData.Folder, itemClickListener: ((Int) -> Unit)?) {
        this.itemClickListener = itemClickListener
        title.text = itemView.context.getString(folder.item.stringId)

        if (folder.isActivated) {
            indicator.visibility = View.VISIBLE
        } else {
            indicator.visibility = View.INVISIBLE
        }
    }
}