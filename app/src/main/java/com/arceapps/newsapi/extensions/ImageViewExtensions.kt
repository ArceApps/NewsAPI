package com.arceapps.newsapi.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

/**
 * Created by ArceApps on 08/12/2022.
 * Project name: NewsAPI.
 */
fun ImageView.fromUrl(url:String){
    Picasso.get().load(url).into(this)
}

fun ImageView.loadImageId(image: Int) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}