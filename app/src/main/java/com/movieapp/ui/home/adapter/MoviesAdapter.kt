package com.movieapp.ui.home.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.movieapp.R
import com.movieapp.data.model.Movie
import com.movieapp.databinding.MovieListItemBinding
import com.movieapp.ui.home.view.HomeFragmentDirections
import java.util.*


class MoviesAdapter(movieList: ArrayList<Movie>?, context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>(){

    private var mMovies: ArrayList<Movie>
    private val context: Context
//    private var navController: NavController? = null

    init {
        requireNotNull(movieList) { "Movie Data must not be null" }
        mMovies = movieList
        this.context = context
//        navController =
//            Navigation.findNavController((context as Activity), R.id.nav_host_fragment_activity_main)
    }

    class MyViewHolder(view: View?) : ViewHolder(view!!) {
        var binding: MovieListItemBinding = MovieListItemBinding.bind(view!!)
    }

    fun addData(list: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        val myViewHolder = MyViewHolder(itemView)
        val screenWidth =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (screenWidth * 0.50).toInt()
        )
        myViewHolder.binding.ivMovie.layoutParams = params
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)

        holder.binding.tvName.text = movie.title
        holder.binding.tvDescription.text = movie.description
        holder.binding.tvRtScore.text = movie.rt_score

        Glide.with(context).load(movie.movie_banner).into(holder.binding.ivMovie)

        // Show Movie details screen
        holder.binding.root.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_navigation_home_to_navigation_movie_details)
        }

    }

    private fun getItem(position: Int): Movie {
        return mMovies[position]
    }
}