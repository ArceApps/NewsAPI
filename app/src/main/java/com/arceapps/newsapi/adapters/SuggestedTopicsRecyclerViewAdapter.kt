package com.arceapps.newsapi.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arceapps.newsapi.R
import com.arceapps.newsapi.activities.TopStoriesActivity
import com.arceapps.newsapi.model.SuggestedTopics

class SuggestedTopicsRecyclerViewAdapter(val context: Context, private val suggestedTopics: List<SuggestedTopics>)  : RecyclerView.Adapter<SuggestedTopicsRecyclerViewAdapter.SuggestedTopicsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedTopicsViewHolder {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_category_news, parent, false)
        val viewHolder = SuggestedTopicsViewHolder(view)

        return viewHolder


    }

    override fun getItemCount(): Int {

        return suggestedTopics.size

    }

    override fun onBindViewHolder(holder: SuggestedTopicsViewHolder, position: Int) {

        holder.text.text = suggestedTopics[position].title
        holder.image.setImageResource(suggestedTopics[position].image)

        holder.image.setOnClickListener {
            val intent = Intent (context, TopStoriesActivity::class.java)
            intent.putExtra("name", suggestedTopics[position].title)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }

    }

    class SuggestedTopicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)!!
        val text = itemView.findViewById<TextView>(R.id.text)!!

    }
}
