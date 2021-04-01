package io.ybiletskyi.fec.edit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.details.MenuButtons

class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val iconView = view.findViewById<ImageView>(R.id.image)
    private val titleView = view.findViewById<TextView>(R.id.title)
    private var onItemClick: (Int) -> Unit = {}

    init {
        itemView.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    fun onBind(data: MenuButtons, callback: (Int) -> Unit) {
        onItemClick = callback
        iconView.setImageResource(data.icRes)
        titleView.text = itemView.context.getString(data.titleRes)
    }
}