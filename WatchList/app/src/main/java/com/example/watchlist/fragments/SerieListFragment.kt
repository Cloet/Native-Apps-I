package com.example.watchlist.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.DeleteSerieListener
import com.example.watchlist.adapters.SerieRecyclerViewAdapter
import com.example.watchlist.adapters.SerieRecyclerViewListener
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.SeriesViewModel
import kotlinx.android.synthetic.main.series_list.*
import org.jetbrains.anko.doAsync
import java.lang.ClassCastException

class SerieListFragment: Fragment(), SearchView.OnQueryTextListener {


    private lateinit var serieListViewModel: SeriesViewModel
    private lateinit var adapter: SerieRecyclerViewAdapter
    private lateinit var listener: SerieDetailFragment.onSerieSelected
    var searchView: SearchView? = null
    var filterGlobal : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        var root = inflater.inflate(R.layout.fragment_series_list, container, false)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.search_action, menu)

        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as android.widget.SearchView
        searchView?.queryHint = "Enter a series name"
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SerieDetailFragment.onSerieSelected)
            listener = context
        else
            throw ClassCastException(context.toString() + " has to implement onSerieSelected.")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        serieListViewModel = ViewModelProviders.of(activity!!).get(SeriesViewModel::class.java)

        adapter = SerieRecyclerViewAdapter(this, SerieRecyclerViewListener {
            serie -> listener.onSerieSelected(serie)
        }, DeleteSerieListener {
            serie ->
            Toast.makeText(context,"Serie " + serie.name + " has been deleted from your watchlist.", Toast.LENGTH_LONG)
            serieListViewModel.deleteSerie(serie)
        })


        serieListViewModel.getAllSeries().observe(this, Observer<List<SavedSerie>> {
            adapter.listData = it
            if (filterGlobal.isNullOrEmpty())
                adapter.filteredData = it
            else
                this.onQueryTextChange(filterGlobal)
        })

        serie_recyclerView.adapter = adapter
        serie_recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(filter: String?): Boolean {
        filterGlobal = filter
        adapter!!.filter.filter(filter)
        return true
    }

}