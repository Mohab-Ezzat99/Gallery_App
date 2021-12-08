package com.example.galleryapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnSplashPhotoModel(
    val id: String,
    val description: String?,
    val url: UnSplashPhotoUrl,
    val user: UnSplashUser
) : Parcelable {

    @Parcelize
    data class UnSplashPhotoUrl(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnSplashUser(
        val id: String,
        val username: String,
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source-ImageSearchApp&utm_medium-referral"
    }
}