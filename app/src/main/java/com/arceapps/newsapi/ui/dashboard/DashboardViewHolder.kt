package com.arceapps.newsapi.ui.dashboard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arceapps.newsapi.R

/**
 * Created by ArceApps on 18/12/2022.
 * Project name: NewsAPI.
 */
class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image : ImageView = itemView.findViewById(R.id.image_top_headlines)
    val text : TextView = itemView.findViewById(R.id.title_top_headlines)
    val date : TextView = itemView.findViewById(R.id.date_top_headlines)
    val item : View = itemView

}