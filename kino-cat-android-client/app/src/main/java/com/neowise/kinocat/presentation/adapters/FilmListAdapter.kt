package com.neowise.kinocat.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.neowise.kinocat.R
import com.neowise.kinocat.data.model.Color
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.databinding.ItemFilmBinding
import com.neowise.kinocat.utility.loadImage

class FilmListAdapter(private val selectListener: FilmSelectListener) : RecyclerView.Adapter<FilmListAdapter.FilmViewHolder>() {

    private var items: List<Film> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding, selectListener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): Film {
        return items[position]
    }

    fun setData(films: List<Film>) {
        items = films
        notifyDataSetChanged()
    }

    class FilmViewHolder(
        private val binding: ItemFilmBinding,
        private val selectListener: FilmSelectListener
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Film) {

            binding.root.setOnClickListener {
                val animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.click)
                binding.root.startAnimation(animation)
                selectListener.filmSelected(adapterPosition)
            }

            binding.genre.text = model.genre
            binding.name.text = model.name
            binding.year.text = model.year.toString()
            binding.country.text = model.country

            loadImage(itemView.context, model.id, binding.poster)

            val color = Color.valueOf(model.color)

            binding.background.setBackgroundResource(color.background)
            binding.gradientImg.setImageResource(color.gradient)
        }
    }

    fun interface FilmSelectListener {
        fun filmSelected(position: Int)
    }
}