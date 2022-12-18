package com.arceapps.newsapi.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arceapps.newsapi.R
import com.arceapps.newsapi.activities.SingleNewsActivity
import com.arceapps.newsapi.model.NewsHeadlines
import com.bumptech.glide.Glide

/**
 * Created by ArceApps on 18/12/2022.
 * Project name: NewsAPI.
 */
class DashboardRecyclerViewAdapter(var context: Context, var newsheadlines: List<NewsHeadlines>) :
    RecyclerView.Adapter<DashboardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_top_headlines, parent, false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsheadlines.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {

        if (newsheadlines.get(position).name != null) {
            holder.text.text = newsheadlines.get(position).name
        } else if (newsheadlines.get(position).author != null) {
            holder.text.text = newsheadlines.get(position).author
        } else if (newsheadlines.get(position).id != null) {
            holder.text.text = newsheadlines.get(position).id
        } else {
            holder.text.text = newsheadlines.get(position).title
        }

        Glide.with(context)
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, SingleNewsActivity::class.java);
            intent.putExtra(context.getString(R.string.content), newsheadlines.get(position).content)
            intent.putExtra(context.getString(R.string.description), newsheadlines.get(position).description)
            intent.putExtra(context.getString(R.string.author), newsheadlines.get(position).author)
            intent.putExtra(context.getString(R.string.url), newsheadlines.get(position).url)
            intent.putExtra(context.getString(R.string.urlToImage), newsheadlines.get(position).urlToImage)
            intent.putExtra(context.getString(R.string.title), newsheadlines.get(position).title)
            intent.putExtra(context.getString(R.string.publishedAt), newsheadlines.get(position).publishedAt)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}