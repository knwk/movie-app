package com.example.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.model.TMDBMovie
import com.example.movieapp.util.getProgressDrawable
import com.example.movieapp.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    // Stores argument passed via Navigation
    var movie: TMDBMovie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set movie variable when arguments passed via Navigation
        arguments?.let {
            movie = DetailFragmentArgs.fromBundle(it).movie
        }
        // Display info about movie detail using Navigation arguments
        context?.let {
            movieImage.loadImage(movie?.poster_path, getProgressDrawable(it))
        }
        movieName.text = movie?.title
        movieReleaseDate.text = movie?.release_date
        movieRating.text = movie?.voteAverage.toString()
        movieDescription.text = movie?.overview
    }
}