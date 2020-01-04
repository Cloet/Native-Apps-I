package com.example.watchlist.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.SeriesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.model.SavedSerie

/**
 * [RecyclerView] adapter for [SavedSerie] list
 * @param fragment [SerieListFragment]
 * @see SerieDiffCallback
 * @see Filterable
 * @see ListAdapter
 * */
class SerieRecyclerViewAdapter(private val fragment: SerieListFragment, private val listener: SerieRecyclerViewListener, private val removeListener: DeleteSerieListener) :
    ListAdapter<SavedSerie, SerieRecyclerViewAdapter.ViewHolder>(SerieDiffCallback()), Filterable {

    /**
     * Function to get the current filter value.
     * @return [Filter]
     * */
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

    /**
     * Constructor
     * Sets the filteredData to the initial received listdata.
     * */
    init {
        filteredData = listData
        notifyDataSetChanged()
    }

    /**
     * Function that returns total amount of items in the list.
     * @return [Int]
     * */
    override fun getItemCount(): Int {
        return filteredData.size
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
        val item = filteredData[position]

        holder.bind(item,listener, removeListener)
    }

    /**
     * Custom [ViewHolder]
     * @constructor Creates the [ViewHolder]
     * @property binding [SeriesListContentBinding]
     * @see RecyclerView.ViewHolder
     * */
    class ViewHolder private constructor(val binding: SeriesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds [item], [listener] and [removeListener]
         * @param item [SavedSerie]
         * @param listener [SerieRecyclerViewListener] used to navigate to SerieDetailFragment
         * @param removeListener [DeleteSerieListener] used to delete series from room database.
         * */
        fun bind(item: SavedSerie, listener: SerieRecyclerViewListener, removeListener: DeleteSerieListener) {
            binding.serie = item
            binding.clickListener = listener
            binding.removeListener = removeListener
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
                val binding = SeriesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

/**
 * Clicklistener for deleting a [SavedSerie] from room database.
 * @property clickListener returns clicked [SavedSerie] in the list
 * */
class DeleteSerieListener (val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

/**
 * Clicklistener for navigation to [SerieDetailFragment]
 * @property clickListener returns clicked [SavedSerie] in the list
 * */
class SerieRecyclerViewListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

/**
 * [SerieDiffCallback] provides 2 functions to check if an item is changed.
 * @see DiffUtil.ItemCallback
 * */
class SerieDiffCallback: DiffUtil.ItemCallback<SavedSerie>() {

    /**
     * Verifies if 2 [SavedSerie] objects are the same by comparing id's
     * @param oldItem [SavedSerie]
     * @param newItem [SavedSerie]
     * @return [Boolean]
     * */
    override fun areContentsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem.savedSerieId == newItem.savedSerieId
    }

    /**
     * Verifies if 2 [SavedSerie] objects are the same. Used to verify if an item has changed.
     * @param oldItem [SavedSerie]
     * @param newItem [SavedSerie]
     * @return [Boolean]
     * */
    override fun areItemsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return oldItem == newItem
    }
}

