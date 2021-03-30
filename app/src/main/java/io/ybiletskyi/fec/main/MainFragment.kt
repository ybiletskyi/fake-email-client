package io.ybiletskyi.fec.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.EmailsViewModel
import io.ybiletskyi.fec.FiltersViewModel
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment
import io.ybiletskyi.fec.utils.MyDividerItemDecoration

class MainFragment : BaseFragment(), EmailsAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var filtersViewModel: FiltersViewModel
    private lateinit var emailsViewModel: EmailsViewModel

    private val adapter by lazy { EmailsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = MyDividerItemDecoration(requireContext())

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emailsViewModel = ViewModelProvider(this).get(EmailsViewModel::class.java)
        emailsViewModel.emailList.observe(viewLifecycleOwner, { dataList ->
            adapter.setDataSet(dataList)
        })

        filtersViewModel = ViewModelProvider(requireActivity()).get(FiltersViewModel::class.java)
        filtersViewModel.emailsFilter.observe(viewLifecycleOwner, { filterItem ->
            // update title with according filter name
            getMainActivity().applyScreenSettings(getScreenSettings())
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.itemClickListener = this
        recyclerView.addOnScrollListener(onScrollListener)
    }

    override fun onPause() {
        super.onPause()
        adapter.itemClickListener = null
        recyclerView.removeOnScrollListener(onScrollListener)
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun getScreenSettings(): ScreenSettings = ScreenSettings.Default(
        getString(filtersViewModel.emailsFilter.value?.stringId ?: -1)
    )

    override fun onItemClick(data: ShortData.EmailShortData) {

    }

    override fun onItemLongClick(data: ShortData.EmailShortData) {

    }

    private val onScrollListener by lazy {
        object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                emailsViewModel.loadNextEmailsPage()
            }

            override fun isLastPage(): Boolean {
                return emailsViewModel.isInTheEndOfList
            }

            override fun isLoading(): Boolean {
                return emailsViewModel.isLoading
            }
        }
    }
}