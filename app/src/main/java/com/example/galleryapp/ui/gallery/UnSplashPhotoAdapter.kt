package com.example.galleryapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.galleryapp.R
import com.example.galleryapp.data.UnSplashPhotoModel
import com.example.galleryapp.databinding.ItemUnsplashPhotoBinding

//because web issue
private const val PHOTO_URL =
    "https://i.picsum.photos/id/1005/5760/3840.jpg?hmac=2acSJCOwz9q_dKtDZdSB-OIK1HUcwBeXco_RMMTUgfY"

class UnSplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnSplashPhotoModel, UnSplashPhotoAdapter.UnSplashPhotoViewHolder>(
        PHOTO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UnSplashPhotoViewHolder(
        ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UnSplashPhotoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UnSplashPhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(bindingAdapterPosition)
                    item?.let {
                        listener.onClick(it)
                    }
                }
            }
        }

        fun bind(model: UnSplashPhotoModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(PHOTO_URL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textView.text = model.user.username
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(photoModel: UnSplashPhotoModel)
    }

    //check difference
    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnSplashPhotoModel>() {
            override fun areItemsTheSame(oldItem: UnSplashPhotoModel, newItem: UnSplashPhotoModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnSplashPhotoModel,
                newItem: UnSplashPhotoModel
            ) = oldItem == newItem
        }
    }
}