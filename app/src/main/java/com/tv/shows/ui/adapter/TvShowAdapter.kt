package com.tv.shows.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tv.shows.databinding.ItemTvShowBinding
import com.tv.shows.model.Show
import com.tv.shows.ui.fragment.main.MainFragmentDirections
import com.tv.shows.ui.helper.ImageLoader

private const val STATUS_ENDED = "Ended"

class TvShowAdapter : ListAdapter<Show, ShowViewHolder>(TvShowDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShowViewHolder.from(parent)

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object TvShowDiffCallback : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show) = oldItem === newItem

        override fun areContentsTheSame(oldItem: Show, newItem: Show) = oldItem == newItem
    }
}

class ShowViewHolder(private val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Show) {
        binding.textTitle.text = item.name

        item.image?.medium?.let { url ->
            ImageLoader().load(url).into(binding.imagePoster)
        }

        if (item.status == STATUS_ENDED) {
            binding.textStatusEnded.visibility = View.VISIBLE
        } else {
            binding.textStatusEnded.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            binding.root.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(item)
            )
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShowViewHolder {
            return ShowViewHolder(
                ItemTvShowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
