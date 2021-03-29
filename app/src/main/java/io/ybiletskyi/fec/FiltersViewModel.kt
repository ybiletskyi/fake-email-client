package io.ybiletskyi.fec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ybiletskyi.fec.drawer.DrawerItem

class FiltersViewModel: ViewModel() {

    private val _emailsFilter: MutableLiveData<DrawerItem> = MutableLiveData()
    val emailsFilter: LiveData<DrawerItem> = _emailsFilter

    init {
        val initialValue = DrawerItem.Inbox
        _emailsFilter.value = initialValue
    }

    fun applyFilter(item: DrawerItem) {
        if (_emailsFilter.value != item)
            _emailsFilter.value = item
    }
}