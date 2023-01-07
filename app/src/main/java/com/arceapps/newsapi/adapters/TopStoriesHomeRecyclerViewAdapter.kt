package com.arceapps.newsapi.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arceapps.newsapi.R
import com.arceapps.newsapi.activities.SingleNewsActivity
import com.bumptech.glide.Glide
import com.arceapps.newsapi.model.NewsHeadlines

class TopStoriesHomeRecyclerViewAdapter(var context : Context, var newsheadlines : List<NewsHeadlines>) : RecyclerView.Adapter<TopStoriesHomeRecyclerViewAdapter.TopStoriesHomeViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesHomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_round_top_headlines, parent, false)
        val viewHolder : TopStoriesHomeViewHolder = TopStoriesHomeViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return newsheadlines.size
    }

    override fun onBindViewHolder(holder: TopStoriesHomeViewHolder, position: Int) {

        if (newsheadlines[position].name != null) {
            holder.text.text = newsheadlines[position].name
        }
        else if (newsheadlines[position].author != null){
            holder.text.text = newsheadlines[position].author
        }
        else if (newsheadlines[position].id != null){
            holder.text.text = newsheadlines[position].id
        }
        else {
            holder.text.text = newsheadlines[position].title
        }

        Glide.with(context)
            .load(newsheadlines[position].urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, SingleNewsActivity::class.java);
            intent.putExtra(context.getString(R.string.content), newsheadlines[position].content)
            intent.putExtra(context.getString(R.string.description), newsheadlines[position].description)
            intent.putExtra(context.getString(R.string.author), newsheadlines[position].author)
            intent.putExtra(context.getString(R.string.url), newsheadlines[position].url)
            intent.putExtra(context.getString(R.string.urlToImage), newsheadlines[position].urlToImage)
            intent.putExtra(context.getString(R.string.title), newsheadlines[position].title)
            intent.putExtra(context.getString(R.string.publishedAt), newsheadlines[position].publishedAt)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }


    }

    class TopStoriesHomeViewHolder(itemView: View) : ViewHolder(itemView) {

        val image : ImageView = itemView.findViewById(R.id.image_view_top_headlines_round)
        val text : TextView = itemView.findViewById(R.id.text_view_top_headlines_round)

    }

}
