package com.example.watchlist.adapters

import com.example.watchlist.model.Actor
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.watchlist.databinding.ActorListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment

/**
 * BindingAdapter for [Actor] list in [SerieDetailFragment]
 * @param fragment [SerieDetailFragment]
 * @see ListAdapter
 * @see ActorDiffCallback
 * */
class ActorAdapter(private val fragment: SerieDetailFragment):
    ListAdapter<Actor, ActorAdapter.ViewHolder>(ActorDiffCallback()) {

    var listData = listOf<Actor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return listData.size
    }

    /**
     * Function that creates a custom [ViewHolder]
     * @param parent [ViewGroup]
     * @param ViewType [Int]
     * @return [ViewHolder]
     * */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    /**
     * Called by Recyclerview to display data at given [position]
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
     * Custom [ViewHolder] class
     * @constructor creates Viewholder
     * @param binding [ActorListContentBinding]
     * @see RecyclerView.ViewHolder
     * */
    class ViewHolder private constructor(val binding: ActorListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the [item]
         * @param item [Actor]
         * */
        fun bind(item: Actor) {
            binding.actor = item
            binding.executePendingBindings()
        }

        companion object {
            /**
             * Inflate ItemView
             * @param parent [ViewGroup]
             * @return [ViewHolder]
             * */
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActorListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

/**
 * Used to verify if an item is updated, the contents have changed.
 * @see DiffUtil.ItemCallback
 * */
class ActorDiffCallback: DiffUtil.ItemCallback<Actor>() {

    /**
     * Verifies if two [Actor] objects are the same by comparing id's
     * @param oldItem [Actor]
     * @param newItem [Actor]
     * @return [Boolean]
     * */
    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Verifies if contents of two [Actor] objects are the same.
     * @param oldItem [Actor]
     * @param newItem [Actor]
     * @return [Boolean]
     * */
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}