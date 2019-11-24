package com.example.watchlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.SerieRecyclerViewAdapter
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.MainViewModel
import kotlinx.android.synthetic.main.series_list.*

class SerieListFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: SerieRecyclerViewAdapter

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

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        adapter = SerieRecyclerViewAdapter(this)

        mainViewModel.getSerieDataObject().observe(this, Observer<List<SavedSerie>> {
            adapter.listData = it
        })

        serie_recyclerView.adapter = adapter
        serie_recyclerView.layoutManager = LinearLayoutManager(activity)
    }

}