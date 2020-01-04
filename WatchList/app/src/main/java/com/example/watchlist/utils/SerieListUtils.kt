package com.example.watchlist.utils

import android.os.Build
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.watchlist.model.SavedSerie
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

/**
 * BindingAdapter for a [SavedSerie] overview.
 * @param item [SavedSerie]
 * */
@BindingAdapter("serie_overview")
fun TextView.setOverview(item: SavedSerie?) {
    item?.let {
       text = item.overview
    }
}

/**
 * BindingAdapter for the [SavedSerie] name.
 * @param item [SavedSerie]
 * */
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("serie_name")
fun TextView.setName(item: SavedSerie?) {
    item?.let {
        text = item.name
    }
}

/**
 * BindingAdapter that shows the banner of a [SavedSerie]
 * Downloads and loads a resized version of the series banner from API
 * @param item [SavedSerie]
 * */
@BindingAdapter("serie_banner")
fun ImageView.setImageResource(item: SavedSerie) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com${item.banner_location}").resize(500,756).into(this)
    }
}

/**
 * BindingAdapter that shows the rating of a [SavedSerie]
 * @param view [RatingBar]
 * @param item [SavedSerie]
 * */
@BindingAdapter("serie_rating")
fun setRating(view: RatingBar, item: SavedSerie) {
    item?.let {
        view.rating = item.rating
    }
}
