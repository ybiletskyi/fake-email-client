package io.ybiletskyi.fec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ybiletskyi.fec.details.EmailDetail

class EmailDetailViewModel : ViewModel() {

    private val _emailData: MutableLiveData<EmailDetail> = MutableLiveData()
    val emailData: LiveData<EmailDetail> = _emailData

    fun loadEmailDetail(id: Int) {
        _emailData.value = EmailDetail(
                0, "Bro letter", "Bro from Canada", "Hi! How are you?","27.10.1996", false, false
        )
    }
}