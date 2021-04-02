package io.ybiletskyi.fec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result
import io.ybiletskyi.fec.Interactor
import io.ybiletskyi.fec.main.EmailsMapper
import io.ybiletskyi.fec.utils.PaginationListener
import io.ybiletskyi.fec.main.ShortData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EmailsViewModel : ViewModel(), Interactor.UpdatesObserver {

    private val _emailsList: MutableLiveData<Collection<ShortData>> = MutableLiveData()
    val emailList: LiveData<Collection<ShortData>> = _emailsList

    // loaded data
    private val cachedMappedEmails = linkedMapOf<Int, ShortData.EmailShortData>()
    private val mapper = EmailsMapper()

    // paging params
    private val pageLimit = PaginationListener.PAGE_LIMIT
    private var isDeleted = false
    private var page = 0

    var isLoading = false
    val isInTheEndOfList
        get() = page == -1

    init {
        // subscribe for observe changes
        Interactor.addObserver(this)
        // load first page of data
        loadNextEmailsPage()
    }

    fun loadNextEmailsPage() {
        // do not try load data if we reached to the end of list
        if (isInTheEndOfList)
            return

        viewModelScope.launch {
            // notify UI that data loading is started
            isLoading = true
            _emailsList.value = mutableListOf<ShortData>().apply {
                addAll(cachedMappedEmails.values)
                add(ShortData.Loading)
            }

            val result: Collection<ShortData> = withContext(Dispatchers.IO) {
                // load emails from repository
                return@withContext when (val result = Interactor.emails(page++, pageLimit, isDeleted)) {
                    // if repository returns error show it immediately
                    is Result.Error -> listOf(ShortData.InfoMessage(result.message ?: "Unknown error"))
                    // if repository returns next page -- add data to the memory cache
                    // also return cached data
                    is Result.Success -> {
                        val shortEmails = result.data.map { email -> updateCache(email)}
                        // view model loaded all emails
                        if (shortEmails.isEmpty())
                            page = -1

                        cachedMappedEmails.values
                    }
                }
            }

            if (result.isEmpty()) {
                _emailsList.value = listOf(ShortData.InfoMessage("No data is available"))
            } else {
                _emailsList.value = result
            }

            // notify UI that data loading is ended
            isLoading = false
        }
    }

    fun applyFilter(isDeleted: Boolean) {
        if (this.isDeleted == isDeleted)
            return
        // update query params
        this.isDeleted = isDeleted
        this.page = 0
        // invalidate cache
        cachedMappedEmails.clear()
        // load new data set
        loadNextEmailsPage()
    }

    fun changeEmail(id: Int, isDeleted: Boolean? = null, isRead: Boolean? = null) {
        viewModelScope.launch {
            val result: Collection<ShortData> = withContext(Dispatchers.IO) {
                val cachedData = (Interactor.email(id) as Result.Success).data
                val updatedEmail = when {
                    isDeleted != null -> cachedData.copy(isDeleted = isDeleted)
                    isRead != null -> cachedData.copy(isRead = isRead)
                    // if nothing modified return cached data
                    else -> return@withContext cachedMappedEmails.values
                }

                return@withContext when (val result = Interactor.updateEmails(listOf(updatedEmail))) {
                    // show error
                    is Result.Error -> listOf(ShortData.InfoMessage(result.message ?: "Unknown error"))
                    // save to memory cache and map to UI data
                    is Result.Success -> {
                        updateCache(updatedEmail)
                        cachedMappedEmails.values
                    }
                }
            }

            _emailsList.value = result
        }
    }

    override fun onDataAdded(data: Collection<Email>) {
        val newData = linkedMapOf<Int, ShortData.EmailShortData>()
        newData.putAll(data.map { email -> email.id to mapper.mapData(email) })
        newData.putAll(cachedMappedEmails)

        cachedMappedEmails.clear()
        cachedMappedEmails.putAll(newData)

        _emailsList.value = cachedMappedEmails.values
    }

    override fun onDataUpdated(data: Collection<Email>) {
        data.forEach(this::updateCache)
    }

    override fun onCleared() {
        Interactor.removeObserver(this)
        super.onCleared()
    }

    private fun updateCache(email: Email): ShortData.EmailShortData? {
        val needToDeleteFromCache = email.isDeleted != isDeleted
        return if (needToDeleteFromCache) {
            cachedMappedEmails.remove(email.id)
            null
        } else {
            val shortData = mapper.mapData(email)
            cachedMappedEmails[email.id] = shortData
            shortData
        }
    }
}