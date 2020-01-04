package com.example.watchlist.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.SeriesRunningContentBinding
import com.example.watchlist.fragments.MainFragment
import com.example.watchlist.model.SavedSerie

/**
 * [RecyclerView] adapter for [SavedSerie] list
 * @param fragment [MainFragment]
 * @param detailListener [SerieRecyclerViewListener]
 * @see SerieDiffCallback
 * */
class SerieContinuingAdapter(private val fragment: MainFragment, private val detailListener: SerieRecyclerViewListener) :
    ListAdapter<SavedSerie, SerieContinuingAdapter.ViewHolder>(SerieDiffCallback()) {


    var listData = listOf<SavedSerie>()
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

        holder.bind(item, detailListener)
    }

    /**
     * Custom [ViewHolder]
     * @constructor Creates the [ViewHolder]
     * @property binding [SeriesRunningContentBinding]
     * @see RecyclerView.ViewHolder
     * */
    class ViewHolder private constructor(val binding: SeriesRunningContentBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds [item] and [listener]
         * @param item [SavedSerie]
         * @param listener [SerieRecyclerViewListener]
         * */
        fun bind(item: SavedSerie, listener: SerieRecyclerViewListener) {
            binding.serie = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            /**
             * Inflates the ItemView into the [ViewHolder]
             * @param parent [ViewGroup]
             * @return [ViewHolder]
             * */
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SeriesRunningContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }


}