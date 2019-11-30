package com.example.watchlist.utils

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.watchlist.model.Episode
import com.squareup.picasso.Picasso


@BindingAdapter("episode_overview")
fun TextView.setOverview(item: Episode?) {
    item?.let {
        text = item.overView
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("episode_name")
fun TextView.setName(item: Episode?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("episode_season")
fun TextView.setSeason(item: Episode?) {
    item?.let {
        text = "S"  + item.season.toString() + "E" + item.episodeNumber
    }
}

@BindingAdapter("episode_banner")
fun ImageView.setImageResource(item: Episode) {
    item?.let {
        Picasso.with(this.context).load("https://thetvdb.com/banners/${item.fileName}").resize(400,300).into(this)
    }
}
