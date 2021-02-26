package com.example.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.TMDBMovie
import com.example.movieapp.util.getProgressDrawable
import com.example.movieapp.util.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

// Adapter for movie RecyclerView
class MovieListAdapter(private val movieList: ArrayList<TMDBMovie>):
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    class MovieViewHolder(var view: View): RecyclerView.ViewHolder(view)

    // Updates movie list to be displayed
    fun updateMovieList(newMovieList: List<TMDBMovie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged() // Notifies system of list changes and refreshes view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movieName.text = movieList[position].title
        // Load TMDB image
        holder.view.movieImage.loadImage(movieList[position].poster_path,
            getProgressDrawable(holder.view.context))
        // Pass TMDBMovie data into detail fragment using Navigation (Parcelable)
        holder.view.movieLayout.setOnClickListener{
            val action = ListFragmentDirections.actionDetail(movieList[position])
            Navigation.findNavController(holder.view).navigate(action)
        }
    }

}