package com.neowise.kinocat.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.neowise.kinocat.R
import com.neowise.kinocat.databinding.ActivitySearchBinding
import com.neowise.kinocat.presentation.fragments.FilmsFragment

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = FilmsFragment.getInstance()
        fragment.search("")

        binding.searchEdit.addTextChangedListener { editable ->
            fragment.search(editable?.toString()!!.trim())
        }

        binding.searchBackButton.setOnClickListener {
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.film_fragment, fragment)
            .commit()

    }
}