package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.ActorAdapter
import com.example.watchlist.adapters.EpisodeAdapter
import com.example.watchlist.databinding.SeriesDetailBinding
import com.example.watchlist.model.Actor
import com.example.watchlist.model.Episode
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.AddSeriesViewModel
import com.example.watchlist.ui.EpisodeViewModel
import com.example.watchlist.ui.SeriesViewModel
import kotlinx.android.synthetic.main.actor_list.*
import kotlinx.android.synthetic.main.episodes_list.*
import kotlinx.android.synthetic.main.series_detail.view.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

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

    private lateinit var episodeViewModel: EpisodeViewModel
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var clickListener: SerieDetailAddListener
    private lateinit var serie: SavedSerie


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailFragment = SeriesDetailBinding.inflate(inflater,container,false)
        val model = arguments!!.getSerializable(SERIE_MODEL) as SavedSerie

        episodeViewModel = ViewModelProviders.of(activity!!).get(EpisodeViewModel::class.java)

        doAsync {
            val api = episodeViewModel.savedSerieRepository.checkIfSeriesExists(model)
            detailFragment.fromApi = !api
            detailFragment.executePendingBindings()
        }

        clickListener = SerieDetailAddListener {
            serie ->
            doAsync {
                val exists = episodeViewModel.savedSerieRepository.checkIfSeriesExists(serie)
                val button = view!!.findViewById<Button>(R.id.add_delete_button)
                if (!exists) {
                    episodeViewModel.savedSerieRepository.insert(serie)
                    button.setText("Delete")
                } else {
                    episodeViewModel.savedSerieRepository.delete(serie)
                    button.setText("Add")
                }
                detailFragment.fromApi = !exists
                detailFragment.executePendingBindings()
            }
        }

        serie = model
        detailFragment.serie = model
        detailFragment.fromApi = false
        detailFragment.clickListener = clickListener
        detailFragment.executePendingBindings()

        return detailFragment.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // episodeViewModel = ViewModelProviders.of(activity!!).get(EpisodeViewModel::class.java)

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




    interface onSerieSelected {
        fun onSerieSelected(serie: SavedSerie)
    }


}

class SerieDetailAddListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}
