package com.tv.shows.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.shows.model.Show
import com.tv.shows.ui.action.FetchShowsAction
import com.tv.shows.ui.action.SearchShowsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_LENGTH = 3

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchShowsAction: FetchShowsAction,
    private val searchShowsAction: SearchShowsAction
) : ViewModel() {

    private val cache: MutableList<Show> = mutableListOf()

    private var _shows = MutableLiveData<List<Show>>()
    val shows: LiveData<List<Show>>
        get() = _shows

    private var _hasInternetConnection = MutableLiveData<Boolean>()
    val hasInternetConnection: LiveData<Boolean>
        get() = _hasInternetConnection

    init {
        fetchShows()
    }

    private fun fetchShows() {
        viewModelScope.launch {
            fetchShowsAction.execute(0)?.let {
                _hasInternetConnection.value = true
                cache.addAll(it)
                _shows.value = it
            } ?: run {
                _hasInternetConnection.value = false
            }
        }
    }

    fun setSearchText(text: String) {
        if (text.isEmpty()) {
            showCachedListIfPossible()
        }
        if (text.length >= MIN_LENGTH) {
            viewModelScope.launch {
                searchShowsAction.execute(text)?.let { shows ->
                    _hasInternetConnection.value = true
                    _shows.value = shows.map { it.show }
                } ?: run {
                    _hasInternetConnection.value = false
                    _shows.value = emptyList()
                }
            }
        }
    }

    private fun showCachedListIfPossible() {
        if (cache.isNotEmpty()) {
            _hasInternetConnection.value = true
            _shows.value = cache
        } else {
            fetchShows()
        }
    }
}
