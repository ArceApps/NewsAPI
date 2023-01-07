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
import com.bumptech.glide.Glide
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.R
import com.arceapps.newsapi.utils.UtilMethods.convertISOTime
import com.arceapps.newsapi.activities.SingleNewsActivity

class HeadlinesRecyclerViewAdapter(var context : Context, var newsheadlines : List<NewsHeadlines>) : RecyclerView.Adapter<HeadlinesRecyclerViewAdapter.HeadlinesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_top_headlines, parent, false)
        return HeadlinesViewHolder(view)
    }

    override fun getItemCount(): Int = newsheadlines.size

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {

        if (newsheadlines[position].title != null) {
            holder.text.text = newsheadlines[position].title
        }
        else if (newsheadlines[position].description != null){
            holder.text.text = newsheadlines[position].description
        }
        else if (newsheadlines[position].content != null){
            holder.text.text = newsheadlines[position].content
        }

        Glide.with(context)
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.item.setOnClickListener {
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

        holder.date.text = convertISOTime(context, newsheadlines[position].publishedAt)

    }

    class HeadlinesViewHolder(itemView: View) : ViewHolder(itemView) {

        val image : ImageView = itemView.findViewById(R.id.image_top_headlines)
        val text : TextView = itemView.findViewById(R.id.title_top_headlines)
        val date : TextView = itemView.findViewById(R.id.date_top_headlines)
        val item : View = itemView

    }

}

