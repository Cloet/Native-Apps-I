package com.example.watchlist.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.watchlist.model.SavedSerie
import com.squareup.picasso.Picasso

@BindingAdapter("serie_overview")
fun TextView.setOverview(item: SavedSerie?) {
    item?.let {
       text = item.overview
    }
}

@BindingAdapter("serie_name")
fun TextView.setName(item: SavedSerie?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("serie_banner")
fun ImageView.setImageResource(item: SavedSerie) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com${item.banner_location}").resize(200,200).into(this)
    }
}
