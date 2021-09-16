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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = FilmsFragment.getInstance()
        fragment.loadFavorites()

        binding.backButton.setOnClickListener {
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.film_fragment, fragment)
            .commit()
    }
}