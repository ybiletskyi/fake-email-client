package io.ybiletskyi.fec.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.viewmodels.EmailsViewModel
import io.ybiletskyi.fec.viewmodels.FiltersViewModel
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment
import io.ybiletskyi.fec.details.MenuButtons
import io.ybiletskyi.fec.drawer.DrawerItem
import io.ybiletskyi.fec.edit.EmailEditBottomSheet
import io.ybiletskyi.fec.utils.MyDividerItemDecoration

class MainFragment : BaseFragment(), EmailsAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var filtersViewModel: FiltersViewModel
    private lateinit var emailsViewModel: EmailsViewModel

    private val adapter by lazy { EmailsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
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
            // update recycler view on the next frame
            recyclerView.post { adapter.setDataSet(dataList) }
        })

        filtersViewModel = ViewModelProvider(requireActivity()).get(FiltersViewModel::class.java)
        filtersViewModel.emailsFilter.observe(viewLifecycleOwner, { filterItem ->
            // update title with according filter name
            getMainActivity().applyScreenSettings(getScreenSettings())
            // apply filter to data set
            emailsViewModel.applyFilter(filterItem == DrawerItem.Trash)
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
        getAppRouter().openDetails(data.id)
    }

    override fun onItemLongClick(data: ShortData.EmailShortData) {
        val sheet = EmailEditBottomSheet.newInstance(data)
        sheet.show(childFragmentManager, "BottomSheet")
        sheet.onItemClickListener = { selectedItem ->
            when (selectedItem) {
                MenuButtons.DELETE -> emailsViewModel.changeEmail(data.id, isDeleted = true)
                MenuButtons.RESTORE -> emailsViewModel.changeEmail(data.id, isDeleted = false)
                MenuButtons.MARK_READ -> emailsViewModel.changeEmail(data.id, isRead = true)
                MenuButtons.MARK_UNREAD -> emailsViewModel.changeEmail(data.id, isRead = false)
            }
        }
    }

    private val onScrollListener = object : PaginationListener() {
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