package com.example.watchlist.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.adapters.AddSerieRecyclerViewAdapter.ViewHolder
import com.example.watchlist.databinding.AddSeriesListContentBinding
import com.example.watchlist.databinding.EpisodesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.model.Episode

/**
 * [RecyclerView] adapter for [Episode] list
 * @param fragment [SerieDetailFragment]
 * @see EpisodeDiffCallback
 * */
class EpisodeAdapter(private val fragment: SerieDetailFragment):
    ListAdapter<Episode, EpisodeAdapter.ViewHolder>(EpisodeDiffCallback()) {

    var listData = listOf<Episode>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Function that returns total amount of items in the list.
     * @return [Int]
     * */
    override fun getItemCount(): Int {
        return listData.size
    }

    /**
     * Creates a custom [ViewHolder]
     * @param parent [ViewGroup]
     * @param viewType [Int]
     * */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Called by [RecyclerView] to display content at given [position].
     * @param holder [ViewHolder]
     * @param position [Int]
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val item = listData[position]
        val item = listData[position]

        holder.bind(item)
    }

    /**
     * Custom [ViewHolder]
     * @constructor Creates the [ViewHolder]
     * @property binding [EpisodesListContentBinding]
     * @see RecyclerView.ViewHolder
     * */
    class ViewHolder private constructor(val binding: EpisodesListContentBinding) : RecyclerView.ViewHolder(binding.root) {


        /**
         * Binds [item]
         * @param item [Episode]
         * */
        fun bind(item: Episode) {
            binding.episode = item
            binding.executePendingBindings()
        }
        companion object {

            /**
             * Inflates the ItemView into the [ViewHolder]
             * @param parent [ViewGroup]
             * @return [ViewHolder]
             * */
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EpisodesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

/**
 * [EpisodeDiffCallback] provides 2 functions to check if an item is changed.
 * @see DiffUtil.ItemCallback
 * */
class EpisodeDiffCallback: DiffUtil.ItemCallback<Episode>() {

    /**
     * Verifies if 2 [Episode] objects are the same by comparing id's
     * @param oldItem [Episode]
     * @param newItem [Episode]
     * @return [Boolean]
     * */
    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Verifies if 2 [Episode] objects are the same. Used to verify if an item has changed.
     * @param oldItem [Episode]
     * @param newItem [Episode]
     * @return [Boolean]
     * */
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}