package com.neowise.kinocat.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.neowise.kinocat.R
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.databinding.ActivityPreviewBinding
import com.neowise.kinocat.presentation.viewmodel.PreviewViewModel
import com.neowise.kinocat.utility.loadFirebaseImage
import android.content.Intent
import android.net.Uri
import com.neowise.kinocat.presentation.viewmodel.AndroidViewModelFactory


class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var viewModel: PreviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film = intent.getParcelableExtra<Film>("film")!!

        viewModel = ViewModelProvider(this, AndroidViewModelFactory(application))
            .get(PreviewViewModel::class.java)

        viewModel.init(film)

        viewModel.favoriteState.observe(this) { isFavorite ->
            binding.favoriteBtn.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite
            )
        }

        binding.description.text = film.description
        binding.name.text = film.name
        binding.filmCountry.text = film.country
        binding.filmGenreTxt.text = film.genre

        loadFirebaseImage(this, film.id, binding.previewImage)

        binding.searchBackButton.setOnClickListener {
            finish()
        }

        binding.watch.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(film.url))
            startActivity(browserIntent)
        }

        binding.favoriteBtn.setOnClickListener { viewModel.changeFavorite() }
    }
}