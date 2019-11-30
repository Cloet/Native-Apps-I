package com.example.watchlist.adapters

import com.example.watchlist.model.SavedSerie
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.watchlist.fragments.AddSerieListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.AddSeriesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment

class AddSerieRecyclerViewAdapter(private val fragment: AddSerieListFragment, private val listener: AddSerieRecyclerViewListener, private val addListener: AddSerieButtonListener) :
    ListAdapter<SavedSerie, AddSerieRecyclerViewAdapter.ViewHolder>(AddSerieDiffCallback()), Filterable {

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults? {
                return null
            }

            override fun publishResults(charSeq: CharSequence?, fRes: FilterResults?) {
            }
        }
    }

    var listData = listOf<SavedSerie>()
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

        holder.bind(item, listener, addListener)
    }

    class ViewHolder private constructor(val binding: AddSeriesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedSerie, clickListener: AddSerieRecyclerViewListener, click: AddSerieButtonListener) {
            binding.serie = item
            binding.clickListener = clickListener
            binding.addSerieListener = click
            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AddSeriesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

class AddSerieButtonListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

 class AddSerieRecyclerViewListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

class AddSerieDiffCallback: DiffUtil.ItemCallback<SavedSerie>() {
    override fun areContentsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem == newItem
    }
}

