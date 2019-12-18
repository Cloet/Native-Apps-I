package com.example.watchlist.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.SeriesListContentBinding
import com.example.watchlist.databinding.SeriesRunningContentBinding
import com.example.watchlist.fragments.MainFragment
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.model.SavedSerie

class SerieContinuingAdapter(private val fragment: MainFragment, private val detailListener: SerieRecyclerViewListener) :
    ListAdapter<SavedSerie, SerieContinuingAdapter.ViewHolder>(SerieDiffCallback()) {


    var listData = listOf<SavedSerie>()
        set(value) {
            field = value
            notifyDataSetChanged()
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

        holder.bind(item, detailListener)
    }

    class ViewHolder private constructor(val binding: SeriesRunningContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedSerie, listener: SerieRecyclerViewListener) {
            binding.serie = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SeriesRunningContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }


}