package com.arceapps.newsapi.ui.bookmarks

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arceapps.newsapi.R
import com.arceapps.newsapi.activities.SingleNewsActivity
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.utils.UtilMethods
import com.bumptech.glide.Glide

/**
 * Created by ArceApps on 10/12/2022.
 * Project name: NewsAPI.
 */
class BookmarksRecyclerViewAdapter (var context : Context, var newsheadlines : List<NewsHeadlines>) : RecyclerView.Adapter<BookmarksViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_top_headlines, parent, false)
        return BookmarksViewHolder(view)
    }

    override fun getItemCount(): Int = newsheadlines.size

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        if (newsheadlines.get(position).title != null) {
            holder.text.setText(newsheadlines.get(position).title)
        }
        else if (newsheadlines.get(position).description != null){
            holder.text.setText(newsheadlines.get(position).description)
        }
        else if (newsheadlines.get(position).content != null){
            holder.text.setText(newsheadlines.get(position).content)
        }

        Glide.with(context)
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.item.setOnClickListener {
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

        holder.date.setText(
            UtilMethods.convertISOTime(context, newsheadlines.get(position).publishedAt))
    }

}