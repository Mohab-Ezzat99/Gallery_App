package com.example.galleryapp.ui.fragment

import androidx.lifecycle.ViewModel
import com.example.galleryapp.data.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unSplashRepository: UnSplashRepository
) : ViewModel()