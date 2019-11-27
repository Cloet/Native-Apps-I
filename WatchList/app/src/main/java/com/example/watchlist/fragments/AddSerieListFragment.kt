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
import com.example.watchlist.adapters.AddSerieRecyclerViewAdapter
import com.example.watchlist.adapters.AddSerieRecyclerViewListener
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.AddSeriesViewModel
import kotlinx.android.synthetic.main.add_series_list.*

class AddSerieListFragment: Fragment(), SearchView.OnQueryTextListener {

    private lateinit var addSerieViewModel: AddSeriesViewModel
    private lateinit var adapter: AddSerieRecyclerViewAdapter
    var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_add_series_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addSerieViewModel = ViewModelProviders.of(activity!!).get(AddSeriesViewModel::class.java)

        adapter = AddSerieRecyclerViewAdapter(this, AddSerieRecyclerViewListener {
            serie -> addSerieViewModel.insertSerie(serie)
        })

        searchView = view!!.findViewById(R.id.add_searchBar)
        searchView!!.setOnQueryTextListener(this)

        addSerieViewModel.foundSeriesObject.observe(this, Observer<List<SavedSerie>> {
            adapter.listData = it
        })

        add_serie_recyclerView.adapter = adapter
        add_serie_recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        addSerieViewModel.RetrieveSeries(p0)
        return true
    }

    override fun onQueryTextChange(filter: String?): Boolean {
        return false
    }

}