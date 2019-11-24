package com.example.watchlist.adapters

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.SeriesListBinding
import com.example.watchlist.databinding.SeriesListContentBinding
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.MainViewModel

class SerieRecyclerViewAdapter(private val fragment: SerieListFragment) :
    ListAdapter<SavedSerie, SerieRecyclerViewAdapter.ViewHolder>(SerieDiffCallback()) {

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
        val item = listData[position]

        if (position %2 == 1){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"))
        }

        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: SeriesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedSerie) {
            binding.serie = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SeriesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

class SerieDiffCallback: DiffUtil.ItemCallback<SavedSerie>() {
    override fun areContentsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem == newItem
    }
}

