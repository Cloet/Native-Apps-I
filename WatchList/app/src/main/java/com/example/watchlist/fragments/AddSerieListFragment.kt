package com.example.watchlist.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.AddSerieButtonListener
import com.example.watchlist.adapters.AddSerieRecyclerViewAdapter
import com.example.watchlist.adapters.AddSerieRecyclerViewListener
import com.example.watchlist.databinding.AddSeriesListBinding
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.AddSeriesViewModel

/**
 * Series List [Fragment] for showing all [SavedSerie] retrieved from the API
 * @see Fragment
 * @see SearchView.OnQueryTextListener
 * */
class AddSerieListFragment: Fragment(), SearchView.OnQueryTextListener {

    private lateinit var addSerieViewModel: AddSeriesViewModel
    private lateinit var adapter: AddSerieRecyclerViewAdapter
    var searchView: SearchView? = null

    /**
     * Initializes the [AddSerieListFragment].
     * @param inflater [LayoutInflater]
     * @param container [ViewGroup]
     * @param savedInstanceState [Bundle]
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        addSerieViewModel = ViewModelProviders.of(activity!!).get(AddSeriesViewModel::class.java)

        val binding: AddSeriesListBinding = DataBindingUtil.inflate(inflater,R.layout.add_series_list, container,false)
        binding.spinningLoader.visibility = View.GONE
        binding.lifecycleOwner = this

        adapter = AddSerieRecyclerViewAdapter(this, AddSerieRecyclerViewListener { serie ->
            serie?.let {
                this.findNavController().navigate(
                    AddSerieListFragmentDirections.actionAddSerieListFragmentToSerieDetailFragment(serie)
                )
            }
        }, AddSerieButtonListener {serie ->
            serie?.let {
                Toast.makeText(context,serie.name + " has been added to your watchlist!",Toast.LENGTH_SHORT).show()
                addSerieViewModel.insertSerie(serie)
                adapter.notifyDataSetChanged()
            }

        })

        addSerieViewModel.retrieving.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.spinningLoader.visibility = View.VISIBLE
                binding.addSerieRecyclerView.visibility = View.GONE
            } else {
                binding.spinningLoader.visibility = View.GONE
                binding.addSerieRecyclerView.visibility = View.VISIBLE
            }
        })

        addSerieViewModel.foundSeriesObject.observe(this, Observer<List<SavedSerie>> {
            adapter.listData = it
            if (it.size === 0) {
                binding.spinningLoader.visibility = View.GONE
                binding.placeholderText.visibility = View.VISIBLE
            } else {
                binding.placeholderText.visibility = View.GONE
            }
        })

        binding.addSerieRecyclerView.adapter = adapter
        binding.addSerieRecyclerView.layoutManager = LinearLayoutManager(activity!!)

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Checks if a [SavedSerie] exists in the room database.
     * @param serie [SavedSerie]
     * @return [Boolean]
     * */
    fun isSeriesInWatchList(serie: SavedSerie): Boolean {
        return addSerieViewModel.savedSeriesRepository.checkIfSeriesExists(serie)
    }

    /**
     * Gets the rating of a [SavedSerie]. This is used when a series is shown from the API but is already in the database.
     * @param serie [SavedSerie]
     * @return [Float]
     * */
    fun getSeriesRating(serie: SavedSerie) : Float {
        val retSerie = addSerieViewModel.savedSeriesRepository.getSerieWithId(serie)
        return retSerie!!.rating
    }

    /**
     * Inflates the search action in the menu.
     * @param menu [Menu]
     * @param inflater [MenuInflater]
     * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.search_action, menu)

        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = "Enter a series name"
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Called when the user submits in search menu item.
     * @param filterValue: [String]
     * @return [Boolean]
     * */
    override fun onQueryTextSubmit(filterValue: String?): Boolean {
        addSerieViewModel.RetrieveSeries(filterValue)
        return true
    }

    /**
     * Called every time a character is changed in the search menu item.
     * @param filter: [String]
     * @return [Boolean]
     * */
    override fun onQueryTextChange(filter: String?): Boolean {
        return false
    }

}