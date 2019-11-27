package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.SerieRecyclerViewAdapter
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.SavedSeriesViewModel
import kotlinx.android.synthetic.main.series_list.*

class SerieListFragment: Fragment(), SearchView.OnQueryTextListener {


    private lateinit var serieListViewModel: SavedSeriesViewModel
    private lateinit var adapter: SerieRecyclerViewAdapter
    var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_series_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        serieListViewModel = ViewModelProviders.of(activity!!).get(SavedSeriesViewModel::class.java)

        adapter = SerieRecyclerViewAdapter(this)

        searchView = view!!.findViewById(R.id.searchBar)
        searchView!!.setOnQueryTextListener(this)

        serieListViewModel.getAllSeries().observe(this, Observer<List<SavedSerie>> {
            if (adapter.listData.isEmpty())
                adapter.filteredData = it
            adapter.listData = it
        })

        serie_recyclerView.adapter = adapter
        serie_recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(filter: String?): Boolean {
        adapter!!.filter.filter(filter)
        return true
    }

}