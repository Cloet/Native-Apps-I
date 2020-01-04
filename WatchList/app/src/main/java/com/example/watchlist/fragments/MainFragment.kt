package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchlist.R
import com.example.watchlist.adapters.*
import com.example.watchlist.databinding.FragmentMainBinding
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.ui.MainViewModel

/**
 * Main [Fragment] for showing all continuing [SavedSerie] objects and total [SavedSerie] saved in database.
 * @see Fragment
 * */
class MainFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: SerieContinuingAdapter

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

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)

        binding.spinningLoaderRunning.visibility = View.GONE
        binding.lifecycleOwner = this

        adapter = SerieContinuingAdapter(this, SerieRecyclerViewListener {serie ->
            serie?.let {
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSerieDetailFragment(serie)
                )
            }
        })

        mainViewModel.getAllContinuingSeries().observe(viewLifecycleOwner, Observer<List<SavedSerie>> {
            adapter.listData = it

            if (it.size === 0){
                binding.spinningLoaderRunning.visibility = View.GONE
                binding.placeholderTextRunning.visibility = View.VISIBLE
            } else {
                binding.placeholderTextRunning.visibility = View.GONE
                binding.serieRunningRecyclerView.visibility = View.VISIBLE
            }
        })

        binding.serieRunningRecyclerView.adapter = adapter
        binding.serieRunningRecyclerView.layoutManager = LinearLayoutManager(activity)

        mainViewModel.totalSeries.observe(viewLifecycleOwner, Observer {
            binding.totalSeries = it.toString()
        })

        mainViewModel.retrieving.observe(viewLifecycleOwner, Observer{
            if (it) {
                binding.spinningLoaderRunning.visibility = View.VISIBLE
                binding.serieRunningRecyclerView.visibility = View.GONE
            } else {
                binding.spinningLoaderRunning.visibility = View.GONE
                binding.serieRunningRecyclerView.visibility = View.VISIBLE
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}