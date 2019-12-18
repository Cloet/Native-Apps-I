package com.example.watchlist.fragments

import android.app.AlertDialog
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
import com.example.watchlist.adapters.DeleteSerieListener
import com.example.watchlist.adapters.SerieRecyclerViewAdapter
import com.example.watchlist.adapters.SerieRecyclerViewListener
import com.example.watchlist.databinding.SeriesListBinding
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.SeriesViewModel
import kotlinx.android.synthetic.main.series_list.*
import org.jetbrains.anko.doAsync

class SerieListFragment: Fragment(), SearchView.OnQueryTextListener {

    private lateinit var serieListViewModel: SeriesViewModel
    private lateinit var adapter: SerieRecyclerViewAdapter
    var searchView: SearchView? = null
    var filterGlobal : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        serieListViewModel = ViewModelProviders.of(activity!!).get(SeriesViewModel::class.java)

        val binding : SeriesListBinding = DataBindingUtil.inflate(inflater,R.layout.series_list, container,false)
        binding.lifecycleOwner = this
        binding.spinningLoader.visibility = View.GONE

        adapter = SerieRecyclerViewAdapter(this, SerieRecyclerViewListener { serie ->
            serie?.let {
                this.findNavController().navigate(
                    SerieListFragmentDirections.actionSerieListFragmentToSerieDetailFragment(serie)
                )
            }
        }, DeleteSerieListener { serie ->
            serie?.let {

                val builder = AlertDialog.Builder(activity!!)
                builder.setMessage("Delete " + serie.name + "?").setCancelable(true)
                    .setPositiveButton("Ok") { dialog, _ ->
                        Toast.makeText(context,serie.name + " has been deleted from your watchlist!", Toast.LENGTH_SHORT).show()
                        serieListViewModel.deleteSerie(serie)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()
                    }

                activity!!.runOnUiThread {
                    val alert = builder.create()
                    alert.setTitle("Delete")
                    alert.show()
                }
            }
        })

        serieListViewModel.getAllSeries().observe(viewLifecycleOwner, Observer<List<SavedSerie>> {
            adapter.listData = it

            if (it.size === 0){
                binding.spinningLoader.visibility = View.GONE
                binding.placeholderText.visibility = View.VISIBLE
            } else {
                binding.placeholderText.visibility = View.GONE
                binding.serieRecyclerView.visibility = View.VISIBLE
            }

            if (filterGlobal.isNullOrEmpty()) {
                adapter.filteredData = it
                this.onQueryTextChange("")
            }
            else
                this.onQueryTextChange(filterGlobal)
        })

        binding.serieRecyclerView.adapter = adapter
        binding.serieRecyclerView.layoutManager = LinearLayoutManager(activity)

        serieListViewModel.retrieving.observe(viewLifecycleOwner, Observer{
            if (it) {
                binding.spinningLoader.visibility = View.VISIBLE
                binding.serieRecyclerView.visibility = View.GONE
            } else {
                binding.spinningLoader.visibility = View.GONE
                binding.serieRecyclerView.visibility = View.VISIBLE
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.search_action, menu)

        val searchItem = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as android.widget.SearchView
        searchView?.queryHint = "Enter a series name"
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu, inflater)
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