package com.movieapp.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieapp.data.model.Movie
import com.movieapp.data.roomdb.RoomDbHelper
import com.movieapp.databinding.FragmentHomeBinding
import com.movieapp.ui.home.adapter.MoviesAdapter
import com.movieapp.ui.home.viewmodel.HomeViewModel
import com.movieapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var allMovies: List<Movie> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupUI()

        setupObserver()

        return root
    }

    private fun setupUI() {
        binding.rcvMovies.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { MoviesAdapter(arrayListOf(), it) }!!
        binding.rcvMovies.adapter = adapter
    }

    /**
     * Set Observer for All Movie List
     */
    private fun setupObserver() {
        homeViewModel.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { movies ->
                        allMovies = movies
                        renderList()
                    }
                    binding.rcvMovies.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rcvMovies.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList() {
        try {
            val roomDbHelper = RoomDbHelper(requireContext())
            roomDbHelper.cacheDataToRoomDB(allMovies)
            adapter.addData(allMovies)
            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}