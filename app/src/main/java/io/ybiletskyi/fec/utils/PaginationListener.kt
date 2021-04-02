package io.ybiletskyi.fec.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener : RecyclerView.OnScrollListener() {

    companion object {
        const val PAGE_LIMIT = 10
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager

        if (!isLoading() && !isLastPage() && layoutManager != null) {
            val visibleItems = layoutManager.childCount
            val totalItems = layoutManager.itemCount
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            val position = visibleItems + firstVisibleItem

            Log.d("PaginationListener", "position = $position, totalItems = $totalItems")
            if (position >= totalItems && firstVisibleItem >= 0 && totalItems >= PAGE_LIMIT) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}