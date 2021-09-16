package com.neowise.kinocat.data.model

import com.neowise.kinocat.R

enum class Color(val background: Int, val gradient: Int) {
    Red(R.drawable.round_youtube_background, R.drawable.gradient_youtube),
    Orange(R.drawable.round_ok_background, R.drawable.gradient_ok),
    Blue(R.drawable.round_vk_background, R.drawable.gradient_vk)
}