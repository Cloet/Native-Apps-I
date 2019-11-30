package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.watchlist.databinding.SeriesDetailBinding
import com.example.watchlist.model.SavedSerie

class SerieDetailFragment: Fragment() {
    companion object {
        val SERIE_MODEL = "model"

        fun newInstance(savedSerie: SavedSerie): SerieDetailFragment {
            val args = Bundle()
            args.putSerializable(SERIE_MODEL,savedSerie)
            val fragment = SerieDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailFragment = SeriesDetailBinding.inflate(inflater,container,false)
        val model = arguments!!.getSerializable(SERIE_MODEL) as SavedSerie

        detailFragment.serie = model
        detailFragment.executePendingBindings()

        return detailFragment.root
    }

    interface onSerieSelected {
        fun onSerieSelected(serie: SavedSerie)
    }

}