package com.example.watchlist.adapters

import com.example.watchlist.model.SavedSerie
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.watchlist.fragments.AddSerieListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.R
import com.example.watchlist.databinding.AddSeriesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment
import org.jetbrains.anko.doAsync

/**
 * Adapter for [AddSerieListFragment]
 * @param fragment [AddSerieListFragment]
 * @param listener [AddSerieRecyclerViewListener]
 * @param addListener [AddSerieButtonListener]
 * @see ListAdapter
 * @see AddSerieDiffCallback
 * @see Filterable
 * */
class AddSerieRecyclerViewAdapter(private val fragment: AddSerieListFragment
                                  , private val listener: AddSerieRecyclerViewListener
                                  , private val addListener: AddSerieButtonListener) :
    ListAdapter<SavedSerie, AddSerieRecyclerViewAdapter.ViewHolder>(AddSerieDiffCallback()), Filterable {

    /**
     * Function to get the current filter value.
     * @return [Filter]
     * */
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

    /**
     * Returns total amount of items in the [RecyclerView]
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
        val item = listData[position]

        doAsync {
            val exists = fragment.isSeriesInWatchList(item)

            item.rating = if (exists) fragment.getSeriesRating(item) else 0F

            fragment.activity!!.runOnUiThread {
                if (exists) {
                    holder.binding.serieRatingBar.rating = item.rating
                    holder.binding.addToPlaylist.visibility = View.GONE
                    holder.binding.addedToPlaylist.visibility = View.VISIBLE
                } else {
                    holder.binding.serieRatingBar.visibility = View.GONE
                    holder.binding.addToPlaylist.visibility = View.VISIBLE
                    holder.binding.addedToPlaylist.visibility = View.GONE
                }
                holder.binding.executePendingBindings()
            }
        }

        holder.bind(item, listener, addListener)
    }

    /**
     * Custom [ViewHolder]
     * @constructor Creates the [ViewHolder]
     * @property binding [AddSeriesListContentBinding]
     * @see RecyclerView.ViewHolder
     * */
    class ViewHolder private constructor(val binding: AddSeriesListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds [item], [clickListener], [click]
         * @param item [SavedSerie]
         * @param clickListener [AddSerieRecyclerViewListener]
         * @param click [AddSerieButtonListener]
         * */
        fun bind(item: SavedSerie, clickListener: AddSerieRecyclerViewListener, click: AddSerieButtonListener) {
            binding.serie = item
            binding.clickListener = clickListener
            binding.addSerieListener = click
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
                val binding = AddSeriesListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}


/**
 * ClickListener for adding a [SavedSerie] to the room database.
 * @property clickListener returns clicked [SavedSerie] of the recyclerview;
 * */
class AddSerieButtonListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

/**
 * Clicklistener for navigation to [SerieDetailFragment]
 * @property clickListener returns clicked [SavedSerie] in the list
 * */
class AddSerieRecyclerViewListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}

/**
 * [AddSerieDiffCallback] provides 2 functions to check if an item is changed.
 * @see DiffUtil.ItemCallback
 * */
class AddSerieDiffCallback: DiffUtil.ItemCallback<SavedSerie>() {

    /**
     * Verifies if 2 [SavedSerie] objects are the same by comparing id's
     * @param oldItem [SavedSerie]
     * @param newItem [SavedSerie]
     * @return [Boolean]
     * */
    override fun areContentsTheSame(oldItem: SavedSerie, newItem: SavedSerie): Boolean {
        return (oldItem.savedSerieId == newItem.savedSerieId)
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

