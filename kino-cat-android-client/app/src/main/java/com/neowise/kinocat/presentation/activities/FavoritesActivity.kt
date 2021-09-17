package com.neowise.kinocat.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.neowise.kinocat.R
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.databinding.ActivityFavoritesBinding
import com.neowise.kinocat.databinding.ActivityMainBinding
import com.neowise.kinocat.databinding.ActivitySearchBinding
import com.neowise.kinocat.presentation.fragments.FilmsFragment

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var filmFragment: FilmsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filmFragment = FilmsFragment.getInstance()

        binding.backButton.setOnClickListener {
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.film_fragment, filmFragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        filmFragment.loadFavorites()
    }
}