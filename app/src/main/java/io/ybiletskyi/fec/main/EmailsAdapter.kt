package io.ybiletskyi.fec.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.inflate

class EmailsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val EMAIL = 0
    }

    private val dataSet = mutableListOf<ShortData>()

    var itemClickListener: OnItemClickListener? = null
    private val _itemClickListener = object : OnItemClickListener {
        override fun onItemClick(data: ShortData.EmailShortData) {
            itemClickListener?.onItemClick(data)
        }

        override fun onItemLongClick(data: ShortData.EmailShortData) {
            itemClickListener?.onItemLongClick(data)
        }
    }

    fun setDataSet(newDataSet: List<ShortData>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            EMAIL -> EmailViewHolder(parent.inflate(R.layout.view_email_item))
            else -> throw IllegalStateException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataSet[position]
        when (holder) {
            is EmailViewHolder -> (data as? ShortData.EmailShortData)?.let { holder.onBind(data, _itemClickListener) }
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return when(dataSet[position]) {
            is ShortData.EmailShortData -> EMAIL
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: ShortData.EmailShortData)
        fun onItemLongClick(data: ShortData.EmailShortData)
    }
}