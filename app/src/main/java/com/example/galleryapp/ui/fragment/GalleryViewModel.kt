package com.example.galleryapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.galleryapp.data.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unSplashRepository: UnSplashRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }

    private val mldCurrentQuery = MutableLiveData(DEFAULT_QUERY)

    //setter for mld
    fun searchPhoto(query: String) {
        mldCurrentQuery.value = query
    }

    val photos = mldCurrentQuery.switchMap { queryString ->
        unSplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }
}