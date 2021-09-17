package com.neowise.kinocat.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.neowise.kinocat.R
import com.neowise.kinocat.domain.Genres
import com.neowise.kinocat.data.model.Color
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.databinding.ActivityAddBinding
import com.neowise.kinocat.presentation.state.AddUIState
import com.neowise.kinocat.presentation.viewmodel.AddFilmViewModel
import com.neowise.kinocat.utility.showSnackbar
import com.squareup.picasso.Picasso

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private val viewModel: AddFilmViewModel by viewModels()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val genres = Genres.values().map { it.name }

        binding.genre.adapter = ArrayAdapter(this, R.layout.spinner_item, genres)

        binding.selectPoster.setOnClickListener {
            ImagePicker.with(this)
                .compress(512)
                .start()
        }

        binding.saveButton.setOnClickListener {
            val film = getFilmModel()
            viewModel.sendFilm(film)
        }

        viewModel.uiState.observe(this) { state ->
            snackbar?.dismiss()

            when(state) {
                is AddUIState.Error -> {
                    showSnackbar(binding.root, "Error while upload film..")
                }
                is AddUIState.PosterNotExists -> {
                    showSnackbar(binding.root, "Please first select poster!")
                }
                is AddUIState.Loading -> {
                    snackbar = Snackbar.make(binding.root, "Uploading..", Snackbar.LENGTH_INDEFINITE)
                    snackbar?.show()
                }
                is AddUIState.Success -> {
                    showSnackbar(binding.root, "Successfuly")
                    finish()
                }
            }
        }
    }

    private fun getFilmModel(): Film {
        val name = binding.name.text.toString()
        val url = binding.url.text.toString()
        val genre = binding.genre.selectedItemPosition
        val year = binding.year.text.toString()
        val country = binding.country.text.toString()
        val description = binding.description.text.toString()

        return Film(
            id = -1L,
            name = name,
            url = url,
            genre = Genres.values()[genre].code,
            year = if (year == "") 2021 else year.toInt(),
            country = country,
            description = description,
            color = Color.values().random().name,
            imageUrl = ""
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val fileUri = data!!.data!!

            Picasso.Builder(this)
                .listener { picasso, uri, ex ->
                    Log.e("PICASSO", "message error", ex)
                }
                .build()
                .load(fileUri.toString())
                .error(R.drawable.ic_app_logo)
                .placeholder(R.drawable.ic_share)
                .into(binding.posterImage)

            binding.selectPoster.visibility = View.INVISIBLE
            binding.posterImage.visibility = View.VISIBLE

            viewModel.initPoster(fileUri)
        }
    }
}