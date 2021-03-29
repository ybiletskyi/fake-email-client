package io.ybiletskyi.fec.drawer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.drawer.holders.DrawerHeaderViewHolder
import io.ybiletskyi.fec.drawer.holders.DrawerItemViewHolder
import io.ybiletskyi.fec.common.inflate

class DrawerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER = 0
        private const val ITEM_FOLDER = 1
    }

    private val dataSet = mutableListOf<DrawerHelper.DrawerData>()
    // external listener
    var itemClickListener: ItemClickListener? = null
    // internal listener for avoiding binding nullable listener
    private val itemSelectionListener: (Int) -> Unit = { position ->
        if (position != RecyclerView.NO_POSITION) {
            val data = dataSet[position] as? DrawerHelper.DrawerData.Folder
            data?.let { itemClickListener?.onItemClick(it.item) }
        }
    }

    fun setData(items: List<DrawerHelper.DrawerData>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            HEADER -> DrawerHeaderViewHolder(parent.inflate(R.layout.drawer_header))
            ITEM_FOLDER -> DrawerItemViewHolder(parent.inflate(R.layout.drawer_folder_item))
            else -> throw IllegalStateException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        when (holder) {
            is DrawerItemViewHolder -> (data as? DrawerHelper.DrawerData.Folder)?.let { holder.onBind(it, itemSelectionListener) }
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is DrawerHelper.DrawerData.Header -> HEADER
            is DrawerHelper.DrawerData.Folder -> ITEM_FOLDER
        }
    }
}