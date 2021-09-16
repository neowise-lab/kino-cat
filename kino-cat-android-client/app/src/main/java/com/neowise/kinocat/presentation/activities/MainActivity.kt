package com.neowise.kinocat.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.neowise.kinocat.R
import com.neowise.kinocat.domain.Genres
import com.neowise.kinocat.databinding.ActivityMainBinding
import com.neowise.kinocat.presentation.fragments.FilmsFragment
import com.neowise.kinocat.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    private lateinit var filmsFragment: FilmsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favlistBtn.setOnClickListener { showFavorites() }
        binding.searchBtn.setOnClickListener { showSearch() }
        binding.addBtn.setOnClickListener { showAdd() }

        val genres = Genres.values()

        for(genre in genres) {
            binding.genreTabs.newTab().apply {
                text = genre.name
                binding.genreTabs.addTab(this, false)
            }
        }

        filmsFragment = FilmsFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, filmsFragment).commit()

        binding.genreTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                val genre = Genres.values()[tab.position]
                changeGenre(genre)
            }
            override fun onTabUnselected(tab: Tab) {}
            override fun onTabReselected(tab: Tab) {}
        })
    }

    override fun onResume() {
        super.onResume()
        binding.genreTabs.selectTab(binding.genreTabs.getTabAt(0))
    }

    private fun changeGenre(genre: Genres) {
        filmsFragment.loadGenre(genre)
    }

    private fun showFavorites() {
        startActivity(Intent(this, FavoritesActivity::class.java))
    }

    private fun showAdd() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    private fun showSearch() {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}