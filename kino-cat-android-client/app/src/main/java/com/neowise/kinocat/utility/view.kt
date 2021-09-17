package com.neowise.kinocat.utility

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.neowise.kinocat.R
import com.squareup.picasso.Picasso
import java.io.IOException

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun loadImage(context: Context, id: Long, imageView: ImageView) {
    val firebase = FirebaseStorage.getInstance()
    val storageReference = firebase.reference
    try {
        storageReference.child("posters/$id").downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.empty_poster)
                .into(imageView)
        }
    }
    catch (e: IOException) { }
}

fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
}
