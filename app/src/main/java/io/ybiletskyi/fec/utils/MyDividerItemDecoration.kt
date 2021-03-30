package io.ybiletskyi.fec.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.common.convertDpToPx

class MyDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val rect = Rect()
    private val paint: Paint = Paint().also {
        it.style = Paint.Style.FILL
        it.color = ResourcesCompat.getColor(context.resources, android.R.color.tertiary_text_dark, context.theme)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)!!
            val holder = parent.getChildViewHolder(child)
            if (holder is ViewHolderItemDecorate) {
                val viewHolderItemDecorate = holder as ViewHolderItemDecorate
                if (viewHolderItemDecorate.isDecorate()) {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + params.bottomMargin
                    val bottom = top + parent.context!!.convertDpToPx(1)
                    rect.set(left, top, right, bottom)
                    c.drawRect(rect, paint)
                }
            }
        }
        c.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = 0
        outRect.right = 0
        outRect.top = 0
        outRect.bottom = parent.context!!.convertDpToPx(1)
    }
}