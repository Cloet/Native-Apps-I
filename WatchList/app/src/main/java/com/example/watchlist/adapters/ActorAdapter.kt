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
import com.example.watchlist.databinding.EpisodesListContentBinding
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.model.Episode

class ActorAdapter(private val fragment: SerieDetailFragment):
    ListAdapter<Actor, ActorAdapter.ViewHolder>(ActorDiffCallback()) {

    var listData = listOf<Actor>()
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

    class ViewHolder private constructor(val binding: ActorListContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Actor) {
            binding.actor = item
            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActorListContentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

class ActorDiffCallback: DiffUtil.ItemCallback<Actor>() {
    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}