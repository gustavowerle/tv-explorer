package com.tv.shows.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tv.shows.model.Show

class DetailViewModel : ViewModel() {

    private var _show = MutableLiveData<Show>()
    val show: LiveData<Show>
        get() = _show

    fun setShow(show: Show) {
        _show.value = show
    }
}
