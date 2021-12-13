package com.example.galleryapp.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        val adapter = UnSplashPhotoAdapter()

        binding.apply {
            FGalleryRv.setHasFixedSize(true)
            FGalleryRv.itemAnimator = null
            FGalleryRv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnSplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnSplashPhotoLoadStateAdapter { adapter.retry() }
            )

            FGalleryBtnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                FGalleryPb.isVisible = loadState.source.refresh is LoadState.Loading
                FGalleryRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                FGalleryTvNoLoad.isVisible = loadState.source.refresh is LoadState.Error
                FGalleryBtnRetry.isVisible = loadState.source.refresh is LoadState.Error

                //empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    FGalleryRv.isVisible = false
                    FGalleryTvNoResult.isVisible = true
                } else
                    FGalleryTvNoResult.isVisible = false
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)
        val searchView = menu.findItem(R.id.menuGallery_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.FGalleryRv.scrollToPosition(0)
                    viewModel.searchPhoto(it)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}