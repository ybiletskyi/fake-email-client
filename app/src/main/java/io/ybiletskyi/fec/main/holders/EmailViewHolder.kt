package io.ybiletskyi.fec.main.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.main.EmailsAdapter
import io.ybiletskyi.fec.main.ShortData

class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val indicatorView = view.findViewById<View>(R.id.indicator)
    private val senderView = view.findViewById<TextView>(R.id.sender)
    private val subjectView = view.findViewById<TextView>(R.id.subject)
    private val dateView = view.findViewById<TextView>(R.id.date)
    private val descriptionView = view.findViewById<TextView>(R.id.description)

    private var emailData: ShortData.EmailShortData? = null
    private var itemClickListener: EmailsAdapter.OnItemClickListener? = null

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                emailData?.let { itemClickListener?.onItemClick(it) }
            }
        }

        itemView.setOnLongClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                emailData?.let { itemClickListener?.onItemLongClick(it) }
            }
            return@setOnLongClickListener true
        }
    }

    fun onBind(emailShortData: ShortData.EmailShortData, listener: EmailsAdapter.OnItemClickListener) {
        itemClickListener = listener
        emailData = emailShortData

        senderView.text = emailShortData.sender
        subjectView.text = emailShortData.subject
        dateView.text = emailShortData.date
        descriptionView.text = emailShortData.description

        if (emailShortData.isActive) {
            indicatorView.visibility = View.VISIBLE
        } else {
            indicatorView.visibility = View.INVISIBLE
        }
    }
}