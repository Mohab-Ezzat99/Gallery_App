package com.example.galleryapp.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.databinding.UnsplashPhotoLoadStateFooterBinding

class UnSplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnSplashPhotoLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = LoadStateViewHolder(
        UnsplashPhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.HFLayoutBtnRetry.setOnClickListener{
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                HFLayoutPb.isVisible = loadState is LoadState.Loading
                HFLayoutTvNoLoad.isVisible = loadState !is LoadState.Loading
                HFLayoutBtnRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}