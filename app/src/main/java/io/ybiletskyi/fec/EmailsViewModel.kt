package io.ybiletskyi.fec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ybiletskyi.domain.EmailsRepository
import io.ybiletskyi.domain.Result
import io.ybiletskyi.fec.main.EmailsMapper
import io.ybiletskyi.fec.main.ShortData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailsViewModel : ViewModel() {

    private val _emailsList: MutableLiveData<List<ShortData>> = MutableLiveData()
    val emailList: LiveData<List<ShortData>> = _emailsList

    // loaded data
    private val cachedEmails = mutableListOf<ShortData.EmailShortData>()
    private val repository = EmailsRepository.newInstance()
    private val mapper = EmailsMapper()

    // paging params
    private val pageLimit = 10
    private var page = 0

    fun loadNextEmailsPage() {
        viewModelScope.launch {
            // notify UI that data start loading
            _emailsList.value = mutableListOf<ShortData>().apply {
                addAll(cachedEmails)
                add(ShortData.Loading)
            }

            val result: List<ShortData> = withContext(Dispatchers.IO) {
                // load emails from repository
                return@withContext when (val result = repository.emails(page++, pageLimit)) {
                    // if repository returns error show it immediately
                    is Result.Error -> listOf(ShortData.InfoMessage(result.message ?: "Unknown error"))
                    // if repository returns next page -- add data to the memory cache
                    // also return cached data
                    is Result.Success -> {
                        val shortEmails = result.data.map { email -> mapper.mapData(email) }
                        cachedEmails.addAll(shortEmails)
                        cachedEmails
                    }
                }
            }

            if (result.isEmpty()) {
                _emailsList.value = listOf(ShortData.InfoMessage("No data is available"))
            } else {
                _emailsList.value = result
            }
        }
    }
}