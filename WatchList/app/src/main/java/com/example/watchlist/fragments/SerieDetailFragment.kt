package com.example.watchlist.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.ActorAdapter
import com.example.watchlist.adapters.EpisodeAdapter
import com.example.watchlist.adapters.SerieDiffCallback
import com.example.watchlist.databinding.SeriesDetailBinding
import com.example.watchlist.model.Actor
import com.example.watchlist.model.Episode
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.EpisodeViewModel
import com.example.watchlist.ui.SeriesDetailViewModel
import kotlinx.android.synthetic.main.actor_list.*
import kotlinx.android.synthetic.main.episodes_list.*
import org.jetbrains.anko.doAsync

/**
 * Detail view for a selected [SavedSerie].
 * @see Fragment
 * */
class SerieDetailFragment: Fragment()  {

    private lateinit var episodeViewModel: EpisodeViewModel
    private lateinit var seriesDetailViewModel: SeriesDetailViewModel
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var clickListener: SerieDetailAddListener
    private lateinit var serie: SavedSerie

    val args : SerieDetailFragmentArgs by navArgs()

    /**
     * Initializes the [MainFragment].
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup]
     * @param savedInstanceState [Bundle]
     * */
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

        detailFragment.serieRatingBar.setOnRatingBarChangeListener { _, fl, _ ->
            serie?.let {
                serie.rating = fl
                seriesDetailViewModel.updateSerieRating(serie)
            }
        }

        seriesDetailViewModel.fromApi.observe(viewLifecycleOwner, Observer {
            if (it) {
                detailFragment.serieRatingBar.visibility = View.GONE
                detailFragment.addButton.visibility = View.VISIBLE
                detailFragment.deleteButton.visibility = View.GONE
            } else {
                detailFragment.serieRatingBar.visibility = View.VISIBLE
                detailFragment.addButton.visibility = View.GONE
                detailFragment.deleteButton.visibility = View.VISIBLE
            }
        })

        clickListener = SerieDetailAddListener { serie ->
            serie?.let {
                doAsync {
                    val exists = episodeViewModel.savedSerieRepository.checkIfSeriesExists(serie)
                    if (!exists) {
                        activity!!.runOnUiThread(Runnable {
                            Toast.makeText(context,serie.name + " has been added to your watchlist!", Toast.LENGTH_SHORT).show()
                        })
                        episodeViewModel.savedSerieRepository.insert(serie)
                        seriesDetailViewModel.setFromApi(exists)
                    } else {

                        val builder = AlertDialog.Builder(activity!!)
                        builder.setMessage("Delete " + serie.name + "?").setCancelable(true)
                            .setPositiveButton("Ok") { dialog, _ ->
                                Toast.makeText(context,serie.name + " has been deleted from your watchlist!", Toast.LENGTH_SHORT).show()
                                doAsync {
                                    episodeViewModel.savedSerieRepository.delete(serie)
                                }
                                dialog.dismiss()
                                seriesDetailViewModel.setFromApi(exists)
                            }
                            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()
                            }

                        activity!!.runOnUiThread {
                            val alert = builder.create()
                            alert.setTitle("Delete")
                            alert.show()
                        }
                    }

                }
            }
        }

        detailFragment.clickListener = clickListener
        return detailFragment.root
    }

    /**
     * Called after the [Fragment] is created.
     * Creates the [episodeAdapter] and [actorAdapter] and retrieves all [Episode] and [Actor] objects for the selected [SavedSerie].
     * @param savedInstanceState [Bundle]
     * */
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

/**
 * [SerieDetailAddListener] listens when a [SavedSerie] is clicked an retruns the clicked [SavedSerie].
 * Used to navigate to [SerieDetailFragment]
 * */
class SerieDetailAddListener(val clickListener: (serie: SavedSerie) -> Unit) {
    fun onClick(serie: SavedSerie) = clickListener(serie)
}
