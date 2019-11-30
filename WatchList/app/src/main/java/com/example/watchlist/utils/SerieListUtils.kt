package com.example.watchlist.utils

import android.os.Build
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.watchlist.model.SavedSerie
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

@BindingAdapter("serie_overview")
fun TextView.setOverview(item: SavedSerie?) {
    item?.let {
       text = item.overview
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("serie_name")
fun TextView.setName(item: SavedSerie?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("serie_banner")
fun ImageView.setImageResource(item: SavedSerie) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com${item.banner_location}").resize(500,756).into(this)
    }
}

@BindingAdapter("serie_rating")
fun RatingBar.setRating(item: SavedSerie) {
    item?.let {
        rating = item.rating
    }
}
