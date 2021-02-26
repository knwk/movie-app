package com.example.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.model.TMDBMovie
import com.example.movieapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

// ListFragment class connects to ListViewModel (view model) and ListFragment (view)
// Observers connect to LiveData and updates view accordingly

class ListFragment : Fragment() {

    // Reference to ViewModel
    private lateinit var viewModel: ListViewModel
    private val listAdapter = MovieListAdapter(arrayListOf())

    // Observers for LiveData from ViewModel
    private val movieListDataObserver = Observer<List<TMDBMovie>> {list ->
        list?.let {
            movieList.visibility = View.VISIBLE
            listAdapter.updateMovieList(it)
        }
    }
    private val loadingLiveDataObserver = Observer<Boolean> {isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            movieList.visibility = View.GONE
        }
    }
    private val errorLiveDataObserver = Observer<Boolean> {isError ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    // LifeCycle: called to do initial creation of the fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // LifeCycle: creates and returns the view hierarchy associated with the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    // LifeCycle: called after onCreateView, subclasses are initialized upon view hierarchy creation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handles lifecycle events for fragment
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        // Attach observers & fragment to LiveData variables from ListViewModel
        viewModel.movies.observe(this, movieListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.refresh()

        // Create two-column grid RecyclerView layout
        movieList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        // Handle pull to refresh
        refreshLayout.setOnRefreshListener {
            movieList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

    }

}