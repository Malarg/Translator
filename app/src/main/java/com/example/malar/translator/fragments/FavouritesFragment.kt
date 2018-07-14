package com.example.malar.translator.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.malar.translator.R
import com.example.malar.translator.adapters.FavouritesAdapter
import com.example.malar.translator.di.App
import com.example.malar.translator.viewModels.FavouritesViewModel
import com.example.malar.translator.viewModels.ViewModelFactory
import javax.inject.Inject

class FavouritesFragment @Inject constructor(): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        App.getComponent().inject(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favouritesRecycleView)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[FavouritesViewModel::class.java]
        val recyclerViewAdapter = FavouritesAdapter()
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModel.translations.observe(this, Observer {
            recyclerViewAdapter.update(it ?: emptyList())
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}
