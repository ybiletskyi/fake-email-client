package io.ybiletskyi.fec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ybiletskyi.fec.App
import io.ybiletskyi.fec.Interactor
import io.ybiletskyi.fec.drawer.DrawerItem
import io.ybiletskyi.fec.utils.EmailCreator
import kotlinx.coroutines.launch

class FiltersViewModel: ViewModel() {

    private val _emailsFilter: MutableLiveData<DrawerItem> = MutableLiveData()
    val emailsFilter: LiveData<DrawerItem> = _emailsFilter

    // should not exist here, as this view model is not corresponding for manipulation with email
    private val emailCreator = EmailCreator(App.context)

    init {
        val initialValue = DrawerItem.Inbox
        _emailsFilter.value = initialValue
    }

    fun applyFilter(item: DrawerItem) {
        if (_emailsFilter.value != item)
            _emailsFilter.value = item
    }

    // should not exist here, as this view model is not corresponding for manipulation with email
    fun emulateNewEmail() {
        viewModelScope.launch {
            val email = emailCreator.generateRandomEmail()
            Interactor.saveEmails(listOf(email))
            emailCreator.createNotification(email)
        }
    }
}