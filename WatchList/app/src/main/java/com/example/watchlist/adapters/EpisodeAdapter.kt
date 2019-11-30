package com.example.watchlist.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.EpisodesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.model.Episode

class EpisodeAdapter(private val fragment: SerieDetailFragment):
    ListAdapter<Episode, EpisodeAdapter.ViewHolder>(EpisodeDiffCallback()) {

    var listData = listOf<Episode>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val item = listData[position]
        val item = listData[position]

        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: EpisodesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Episode) {
            binding.episode = item
            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EpisodesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

class EpisodeDiffCallback: DiffUtil.ItemCallback<Episode>() {
    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}