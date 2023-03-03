package com.example.gifsearcher.fragments.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.gifsearcher.core.data.model.Gif
import com.example.gifsearcher.databinding.CardGifBinding
import com.example.gifsearcher.fragments.home.presentation.viewholder.OnClickGif
import com.example.gifsearcher.fragments.home.presentation.viewholder.ViewHolderGif

class GifsAdapter(private val onClickGif: OnClickGif): ListAdapter<Gif, ViewHolderGif>(DiffUtilGifs()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGif {
        val binding = CardGifBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return ViewHolderGif(binding, onClickGif)
    }

    override fun onBindViewHolder(holder: ViewHolderGif, position: Int) {
        holder.bind(currentList[position])
    }
    class DiffUtilGifs: DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean =
            oldItem.id == newItem.id && oldItem.title == newItem.title

    }
}