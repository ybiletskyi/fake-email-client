package io.ybiletskyi.fec.main

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(
        private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    companion object {
        const val PAGE_LIMIT = 10
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!isLoading() && !isLastPage()) {
            val visibleItems = layoutManager.childCount
            val totalItems = layoutManager.itemCount
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            val position = visibleItems + firstVisibleItem

            if (position >= totalItems && firstVisibleItem >= 0 && totalItems >= PAGE_LIMIT) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}