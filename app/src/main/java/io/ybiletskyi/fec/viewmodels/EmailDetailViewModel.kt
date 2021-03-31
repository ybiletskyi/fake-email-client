package io.ybiletskyi.fec.viewmodels

import androidx.lifecycle.*
import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result
import io.ybiletskyi.fec.Interactor
import io.ybiletskyi.fec.details.DetailData
import io.ybiletskyi.fec.details.DetailsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailDetailViewModel(val id: Int) : ViewModel() {

    private val _emailData: MutableLiveData<DetailData> = MutableLiveData()
    val emailData: LiveData<DetailData> = _emailData

    private lateinit var cachedData: Email
    private val mapper = DetailsMapper()

    init {
        // notify UI about data loading
        _emailData.value = DetailData.Loading
        // load email details
        loadEmailDetail()
    }

    private fun loadEmailDetail() {
        viewModelScope.launch {
            val result: DetailData = withContext(Dispatchers.IO) {
                return@withContext when (val result = Interactor.email(id)) {
                    // show error
                    is Result.Error -> DetailData.InfoMessage(result.message ?: "Unknown error")
                    // save to memory cache and map to UI data
                    is Result.Success -> {
                        cachedData = result.data
                        mapper.mapData(result.data)
                    }
                }
            }

            _emailData.value = result
        }
    }

    fun changeFolder(isDeleted: Boolean) {
        viewModelScope.launch {
            val result: DetailData = withContext(Dispatchers.IO) {
                val updatedEmail = cachedData.copy(isDeleted = isDeleted)
                val updates = listOf(updatedEmail)

                return@withContext when (val result = Interactor.updateEmails(updates)) {
                    // show error
                    is Result.Error -> DetailData.InfoMessage(result.message ?: "Unknown error")
                    // save to memory cache and map to UI data
                    is Result.Success -> {
                        cachedData = updatedEmail
                        mapper.mapData(updatedEmail)
                    }
                }
            }

            _emailData.value = result
        }
    }

    fun changeState(isRead: Boolean) {
        viewModelScope.launch {
            val result: DetailData = withContext(Dispatchers.IO) {
                val updatedEmail = cachedData.copy(isViewed = !isRead)
                val updates = listOf(updatedEmail)

                return@withContext when (val result = Interactor.updateEmails(updates)) {
                    // show error
                    is Result.Error -> DetailData.InfoMessage(result.message ?: "Unknown error")
                    // save to memory cache and map to UI data
                    is Result.Success -> {
                        cachedData = updatedEmail
                        mapper.mapData(updatedEmail)
                    }
                }
            }

            _emailData.value = result
        }
    }
}

class EmailDetailViewModelFactory(val id: Int): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmailDetailViewModel(id) as T
    }
}