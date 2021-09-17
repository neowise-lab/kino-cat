package com.neowise.kinocat.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.neowise.kinocat.R
import com.neowise.kinocat.databinding.ActivitySearchBinding
import com.neowise.kinocat.presentation.fragments.FilmsFragment

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var filmsFragment: FilmsFragment


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filmsFragment = FilmsFragment.getInstance()

        binding.searchEdit.addTextChangedListener { editable ->
            filmsFragment.search(editable?.toString()!!.trim())
        }

        binding.searchBackButton.setOnClickListener {
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.film_fragment, filmsFragment)
            .commit()

    }

    override fun onResume() {
        super.onResume()
        filmsFragment.search("")
    }
}