package com.example.watchlist.adapters

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.SeriesListBinding
import com.example.watchlist.databinding.SeriesListContentBinding
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.model.SavedSerie

class SerieRecyclerViewAdapter(private val fragment: SerieListFragment, private val listener: SerieRecyclerViewListener, private val removeListener: DeleteSerieListener) :
    ListAdapter<SavedSerie, SerieRecyclerViewAdapter.ViewHolder>(SerieDiffCallback()), Filterable {

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch = charString.toString()
                if (charSearch.isEmpty())
                    filteredData = listData
                else {
                    val resultList = ArrayList<SavedSerie>()
                    for(row in listData){
                        if(row.name!!.toLowerCase().contains(charSearch.toLowerCase()))
                            resultList.add(row)
                    }
                    filteredData = resultList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = filteredData
                return filterResults
            }

            override fun publishResults(charSeq: CharSequence?, fRes: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredData = fRes?.values as List<SavedSerie>
                notifyDataSetChanged()
            }
        }
    }

    var filteredData = listOf<SavedSerie>()

    var listData = listOf<SavedSerie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        filteredData = listData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredData.size
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
        val item = filteredData[position]

        holder.bind(item,listener, removeListener)
    }

    class ViewHolder private constructor(val binding: SeriesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedSerie, listener: SerieRecyclerViewListener, removeListener: DeleteSerieListener) {
            binding.serie = item
            binding.clickListener = listener
            binding.removeListener = removeListener
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

class DeleteSerieListener (val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

class SerieRecyclerViewListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

class SerieDiffCallback: DiffUtil.ItemCallback<SavedSerie>() {
    override fun areContentsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem.savedSerieId == newItem.savedSerieId
    }

    override fun areItemsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem == newItem
    }
}

