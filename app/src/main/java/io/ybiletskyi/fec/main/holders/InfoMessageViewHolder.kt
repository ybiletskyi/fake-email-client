package io.ybiletskyi.fec.main.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.main.ShortData

class InfoMessageViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val infoMessageView = view.findViewById<TextView>(R.id.info_message)

    fun onBind(data: ShortData.InfoMessage) {
        infoMessageView.text = data.message
    }
}