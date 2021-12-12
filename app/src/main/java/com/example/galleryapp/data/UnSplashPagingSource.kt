package com.example.galleryapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.galleryapp.api.UnSplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_START_INDEX = 1

class UnSplashPagingSource(
    private val unSplashApi: UnSplashApi,
    private val query: String
) : PagingSource<Int, UnSplashPhotoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashPhotoModel> {
        val position = params.key ?: UNSPLASH_START_INDEX

        return try {
            val response = unSplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_START_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnSplashPhotoModel>): Int? {
        TODO("Not yet implemented")
    }
}