package io.ybiletskyi.fec.edit

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.inflate
import io.ybiletskyi.fec.details.MenuButtons

class MenuAdapter(
    private val dataSet: Array<MenuButtons>,
    private val onItemClick: (MenuButtons) -> Unit
) : RecyclerView.Adapter<MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        return MenuItemViewHolder(parent.inflate(R.layout.view_menu_item))
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.onBind(dataSet[position]) { item ->
            if (item != NO_POSITION) {
                onItemClick(dataSet[item])
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}