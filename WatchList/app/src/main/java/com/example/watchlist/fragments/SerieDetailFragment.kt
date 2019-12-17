package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.ActorAdapter
import com.example.watchlist.adapters.EpisodeAdapter
import com.example.watchlist.databinding.AddSeriesListBinding
import com.example.watchlist.databinding.SeriesDetailBinding
import com.example.watchlist.model.Actor
import com.example.watchlist.model.Episode
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.AddSeriesViewModel
import com.example.watchlist.ui.EpisodeViewModel
import com.example.watchlist.ui.SeriesDetailViewModel
import com.example.watchlist.ui.SeriesViewModel
import kotlinx.android.synthetic.main.actor_list.*
import kotlinx.android.synthetic.main.episodes_list.*
import kotlinx.android.synthetic.main.series_detail.view.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class SerieDetailFragment: Fragment() {

    private lateinit var episodeViewModel: EpisodeViewModel
    private lateinit var seriesDetailViewModel: SeriesDetailViewModel
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var clickListener: SerieDetailAddListener
    private lateinit var serie: SavedSerie

    val args : SerieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val detailFragment: SeriesDetailBinding = DataBindingUtil.inflate(inflater,R.layout.series_detail, container,false)
        val model = args.serie

        serie = model
        detailFragment.serie = model
        detailFragment.lifecycleOwner = this

        seriesDetailViewModel = ViewModelProviders.of(activity!!).get(SeriesDetailViewModel::class.java)
        episodeViewModel = ViewModelProviders.of(activity!!).get(EpisodeViewModel::class.java)

        doAsync {
            val exists = episodeViewModel.savedSerieRepository.checkIfSeriesExists(model)
            seriesDetailViewModel.setFromApi(!exists)
        }

        seriesDetailViewModel.fromApi.observe(viewLifecycleOwner, Observer {
            if (it) {
                detailFragment.addButton.visibility = View.VISIBLE
                detailFragment.deleteButton.visibility = View.GONE
            } else {
                detailFragment.addButton.visibility = View.GONE
                detailFragment.deleteButton.visibility = View.VISIBLE
            }
        })

        clickListener = SerieDetailAddListener {
            serie ->
            serie?.let {
                doAsync {
                    val exists = episodeViewModel.savedSerieRepository.checkIfSeriesExists(serie)
                    if (!exists) {
                        activity!!.runOnUiThread(Runnable {
                            Toast.makeText(context,serie.name + " has been added to your watchlist!", Toast.LENGTH_SHORT).show()
                        })
                        episodeViewModel.savedSerieRepository.insert(serie)
                    } else {
                        activity!!.runOnUiThread(Runnable {
                            Toast.makeText(context,serie.name + " has been deleted from your watchlist!", Toast.LENGTH_SHORT).show()
                        })
                        episodeViewModel.savedSerieRepository.delete(serie)
                    }
                    seriesDetailViewModel.setFromApi(exists)
                }
            }
        }

        detailFragment.clickListener = clickListener
        return detailFragment.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        episodeAdapter = EpisodeAdapter(this)
        actorAdapter = ActorAdapter(this)

        episodeViewModel.RetrieveEpisodes(serie.savedSerieId)
        episodeViewModel.RetrieveActors(serie.savedSerieId)

        episodeViewModel.foundEpisodeObject.observe(this, Observer<List<Episode>> {
            episodeAdapter.listData = it
        })

        episodeViewModel.foundActorObject.observe(this, Observer<List<Actor>> {
            actorAdapter.listData = it
        })

        actor_recyclerView.adapter = actorAdapter
        actor_recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        episode_recyclerView.adapter = episodeAdapter
        episode_recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

}

class SerieDetailAddListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}
