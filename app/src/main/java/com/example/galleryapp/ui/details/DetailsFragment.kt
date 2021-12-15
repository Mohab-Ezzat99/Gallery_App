package com.example.galleryapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.galleryapp.R
import com.example.galleryapp.data.UnSplashPhotoModel
import com.example.galleryapp.databinding.FragmentDetailsBinding
import java.net.URI

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        val photoModel = args.photoModel

        binding.apply {
            Glide.with(this@DetailsFragment)
                .load(UnSplashPhotoModel.PHOTO_URL)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        FDetailsPb.isVisible = false
                        FDetailsTvFailed.isVisible = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        FDetailsPb.isVisible = false
                        FDetailsTvCreator.isVisible = true
                        FDetailsTvDescription.isVisible = photoModel.description != null
                        return false
                    }

                })
                .into(binding.FDetailsIv)

            FDetailsTvDescription.text = photoModel.description

            val uri = Uri.parse(photoModel.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            FDetailsTvCreator.apply {
                text = "photo by ${photoModel.user.username} on unSplash"
                setOnClickListener { context.startActivity(intent) }
                paint.isUnderlineText = true
            }

        }
    }

}