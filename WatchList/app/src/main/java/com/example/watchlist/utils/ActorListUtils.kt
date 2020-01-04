package com.example.watchlist.utils


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.watchlist.model.Actor
import com.squareup.picasso.Picasso

/**
 * BindingAdapter to display the name of the [Actor]
 * @param item [Actor]
 * */
@BindingAdapter("actor_name")
fun TextView.setActorName(item: Actor?) {
    item?.let {
        text = item.name
    }
}

/**
 * BindingAdapter that displays the role of the [Actor]
 * @param item [Actor]
 * */
@BindingAdapter("actor_role")
fun TextView.setRole(item: Actor?) {
    item?.let {
        text = item.role
    }
}

/**
 * BindingAdapter to show a banner of the [Actor].
 * Banner is downloaded from the given url.
 * @param item : [Actor]
 * */
@BindingAdapter("actor_image")
fun ImageView.setImageResource(item: Actor) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com/banners/${item.image}").into(this)
    }
}
