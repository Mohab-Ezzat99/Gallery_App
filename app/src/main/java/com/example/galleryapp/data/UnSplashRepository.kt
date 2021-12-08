package com.example.galleryapp.data

import com.example.galleryapp.api.UnSplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnSplashRepository @Inject constructor(private val unSplashApi: UnSplashApi)