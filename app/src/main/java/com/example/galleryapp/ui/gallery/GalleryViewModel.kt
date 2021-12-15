package com.example.galleryapp.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.galleryapp.data.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unSplashRepository: UnSplashRepository,
    state: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
        private const val CURRENT_QUERY = "current_query"
    }

    private val mldCurrentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    //setter for mld
    fun searchPhoto(query: String) {
        mldCurrentQuery.value = query
    }

    val photos = mldCurrentQuery.switchMap { queryString ->
        unSplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }
}