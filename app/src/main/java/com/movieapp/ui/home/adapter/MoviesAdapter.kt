package com.movieapp.ui.home.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.movieapp.R
import com.movieapp.data.model.Movie
import com.movieapp.databinding.MovieListItemBinding
import java.util.*

/**
 * RecyclerView Adapter class to set values on Movie List on home screen
 *
 * @constructor
 * TODO
 *
 * @param movieList
 * @param context
 */
class MoviesAdapter(movieList: ArrayList<Movie>?, context: Context) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var mMovies: ArrayList<Movie>
    private val context: Context

    init {
        requireNotNull(movieList) { "Movie Data must not be null" }
        mMovies = movieList
        this.context = context
    }

    fun addData(list: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        val myViewHolder = MyViewHolder(itemView)
        val screenWidth =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (screenWidth * 0.50).toInt()
        )
        myViewHolder.binding.ivMovie.layoutParams = params
        return MyViewHolder(itemView)*/

        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    private fun getItem(position: Int): Movie {
        return mMovies[position]
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(mMovies.get(position))

    inner class ViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.item = item
//            binding.promotionItemSpecialLabel.visibility = if(item.isSpecial) View.VISIBLE else View.GONE
            binding.executePendingBindings()
        }
    }

    //    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(mMovies[position])
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        // Show Movie details screen
        holder.binding.root.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("movie", Gson().toJson(movie))
            view.findNavController()
                .navigate(R.id.action_navigation_home_to_navigation_movie_details, bundle)
        }
    }

}