package com.example.watchlist.utils


import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.watchlist.model.Actor
import com.squareup.picasso.Picasso


@BindingAdapter("actor_name")
fun TextView.setActorName(item: Actor?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("actor_role")
fun TextView.setRole(item: Actor?) {
    item?.let {
        text = item.role
    }
}

@BindingAdapter("actor_image")
fun ImageView.setImageResource(item: Actor) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com/banners/${item.image}").into(this)
    }
}
