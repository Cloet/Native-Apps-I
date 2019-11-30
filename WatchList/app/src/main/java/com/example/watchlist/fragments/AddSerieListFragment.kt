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
import com.example.watchlist.adapters.AddSerieButtonListener
import com.example.watchlist.adapters.AddSerieRecyclerViewAdapter
import com.example.watchlist.adapters.AddSerieRecyclerViewListener
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.AddSeriesViewModel
import kotlinx.android.synthetic.main.add_series_list.*
import org.jetbrains.anko.doAsync
import java.lang.ClassCastException

class AddSerieListFragment: Fragment(), SearchView.OnQueryTextListener {

    private lateinit var addSerieViewModel: AddSeriesViewModel
    private lateinit var adapter: AddSerieRecyclerViewAdapter
    private lateinit var listener: SerieDetailFragment.onSerieSelected
    private lateinit var addListener: AddSerieButtonListener
    var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        var root = inflater.inflate(R.layout.fragment_add_series_list, container, false)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.search_action, menu)

        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
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

        addSerieViewModel = ViewModelProviders.of(activity!!).get(AddSeriesViewModel::class.java)

        adapter = AddSerieRecyclerViewAdapter(this, AddSerieRecyclerViewListener {
            serie -> listener.onSerieSelected(serie)
        }, AddSerieButtonListener {
            serie ->
            Toast.makeText(context,"Serie " + serie.name + " has been added to your watchlist!",Toast.LENGTH_SHORT)
            addSerieViewModel.insertSerie(serie)
        })

        // searchView = view!!.findViewById(R.id.app_bar_search)
        //searchView = view!!.findViewById(R.id.add_searchBar)
        // searchView!!.setOnQueryTextListener(this)

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