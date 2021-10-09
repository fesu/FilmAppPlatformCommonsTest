package com.movieapp.ui.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.movieapp.data.model.Movie
import com.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This Fragment will show the Single Movie details.
 *
 */
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

            binding.movie = movie
        }
    }

    /**
     * Load Image using DataBinding with Glide
     */
    companion object {
        @BindingAdapter("image")
        @JvmStatic
        fun setImage(image: ImageView, url: String?) {
            if (!url.isNullOrEmpty()){

                Glide.with(image.context).load(url).into(image)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}