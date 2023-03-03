package com.example.gifsearcher.fragments.home.presentation.viewholder

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gifsearcher.R
import com.example.gifsearcher.core.data.model.Gif
import com.example.gifsearcher.databinding.CardGifBinding

class ViewHolderGif(
    private val binding: CardGifBinding,
    private val onClickGif: OnClickGif,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gif: Gif) {
        binding.root.setOnClickListener {
            onClickGif.onClick(gif.id)
        }
        val requestOption = RequestOptions().placeholder(R.drawable.gif)

        Glide
            .with(binding.root)
            .asGif()
            .apply(requestOption)
            .load(gif.images.gifPreview.url)
            .fallback(R.drawable.gif)
            .into(binding.imageGif)

    }
}

interface OnClickGif {
    fun onClick(gifId: String)
}

class GifMarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 2,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) < spanCount) {
                top = spaceSize
            }
            left = spaceSize
            right = spaceSize
            bottom = spaceSize
        }
    }
}