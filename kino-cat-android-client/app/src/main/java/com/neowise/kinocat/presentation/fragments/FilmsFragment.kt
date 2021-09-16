package com.neowise.kinocat.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neowise.kinocat.domain.Genres
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.databinding.FragmentFilmsBinding
import com.neowise.kinocat.presentation.activities.PreviewActivity
import com.neowise.kinocat.presentation.adapters.FilmListAdapter
import com.neowise.kinocat.presentation.state.FilmUIState
import com.neowise.kinocat.presentation.viewmodel.AndroidViewModelFactory
import com.neowise.kinocat.presentation.viewmodel.FilmsViewModel
import com.neowise.kinocat.utility.hide
import com.neowise.kinocat.utility.show
import com.neowise.kinocat.utility.showSnackbar

class FilmsFragment : Fragment() {

    companion object {
        fun getInstance() = FilmsFragment()
    }

    private lateinit var viewModel: FilmsViewModel
    private lateinit var binding: FragmentFilmsBinding
    private lateinit var genre: String
    private lateinit var adapter: FilmListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        genre = arguments?.getString("genre") ?: ""

        binding = FragmentFilmsBinding.inflate(inflater)

        viewModel = ViewModelProvider(this,
            AndroidViewModelFactory(requireActivity().application))
            .get(FilmsViewModel::class.java)

        adapter = FilmListAdapter { position ->
            showPreview(adapter.getItem(position))
        }

        viewModel.filmsList.observe(requireActivity()) {
            when(it) {
                is FilmUIState.Error -> showError(it.e)
                is FilmUIState.Loading -> showProgress()
                is FilmUIState.Success -> {
                    if (it.value.isEmpty()) {
                        showNothing()
                    }
                    else {
                        showFilms(it.value)
                    }
                }
            }
        }

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    fun loadFavorites() {
        viewModel.favoriteFilms()
    }

    fun loadGenre(genre: Genres) {
        viewModel.fetchFilms(genre)
    }

    fun search(criteria: String) {
        viewModel.searchFilm(criteria)
    }

    private fun showPreview(film: Film) {
        val intent = Intent(context, PreviewActivity::class.java)
            .putExtra("film", film)

        startActivity(intent)
    }

    private fun showError(error: Throwable) {
        Log.e("FILMS", "Error while loading", error)
        showSnackbar(binding.root, "Error while loading films...")
        showNothing()
    }

    private fun showFilms(films: List<Film>) {

        adapter.setData(films)

        binding.apply {

            recyclerView.show()
            progressBar.hide()
            nothingText.hide()
        }
    }

    private fun showNothing() {

        binding.apply {

            nothingText.text = "No content"
            nothingText.show()
            progressBar.hide()
            recyclerView.hide()
        }
    }


    private fun showProgress() {

        binding.apply {
            progressBar.show()
            nothingText.hide()
            recyclerView.hide()
        }
    }
}