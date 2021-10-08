package com.movieapp.ui.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.movieapp.R
import com.movieapp.data.model.Movie
import com.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    var movie:Movie? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupUI()

        setHasOptionsMenu(true)

        return root
    }

    private fun setupUI() {
        if (arguments != null) {
            movie = Gson().fromJson(requireArguments().getString("movie"), Movie::class.java)

            binding.tvName.text = movie!!.title
            binding.tvDescription.text = movie!!.description
            binding.tvRtScore.text = getString(R.string.title_score, movie!!.rt_score)
            binding.tvDirector.text = getString(R.string.title_director, movie!!.director)
            binding.tvProducer.text = getString(R.string.title_producer, movie!!.producer)
            binding.tvReleaseDate.text = movie!!.release_date
            binding.tvRunningTime.text = getString(R.string.title_duration, movie!!.running_time)

            context?.let { Glide.with(it).load(movie!!.movie_banner).into(binding.ivMovie) }
            context?.let { Glide.with(it).load(movie!!.image).into(binding.ivMovieImage) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}