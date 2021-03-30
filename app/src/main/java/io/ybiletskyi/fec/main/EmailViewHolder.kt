package io.ybiletskyi.fec.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R

class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val indicatorView = view.findViewById<View>(R.id.indicator)
    private val senderView = view.findViewById<TextView>(R.id.sender)
    private val subjectView = view.findViewById<TextView>(R.id.subject)
    private val dateView = view.findViewById<TextView>(R.id.date)
    private val descriptionView = view.findViewById<TextView>(R.id.description)

    fun onBind(emailShortData: ShortData.EmailShortData) {
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