package com.example.watchlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.watchlist.R
import com.example.watchlist.databinding.FragmentMainBinding
import com.example.watchlist.ui.MainViewModel

class MainFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)

        binding.MySeries.setOnClickListener {
            view: View -> Navigation.findNavController(view).navigate(R.id.serieListFragment)
        }

        binding.SearchSeries.setOnClickListener {
            view : View -> Navigation.findNavController(view).navigate(R.id.addSerieListFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

}