package com.arceapps.newsapi.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.arceapps.newsapi.R
import com.arceapps.newsapi.activities.SingleNewsActivity
import com.bumptech.glide.Glide
import com.arceapps.newsapi.model.NewsHeadlines

class PreferencesViewPagerAdapter(var context: Context, var articleList: List<NewsHeadlines>): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return articleList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.item_pager_news, container, false)

        val image = view.findViewById<ImageView>(R.id.image_preference)
        val text = view.findViewById<TextView>(R.id.text_preferences)

        Glide.with(context)
            .load(articleList[position].urlToImage)
            .into(image)

        text.text = articleList[position].title

        view.setOnClickListener {
            val intent = Intent(context, SingleNewsActivity::class.java);
            intent.putExtra(context.getString(R.string.content), articleList[position].content)
            intent.putExtra(context.getString(R.string.description), articleList[position].description)
            intent.putExtra(context.getString(R.string.author), articleList[position].author)
            intent.putExtra(context.getString(R.string.url), articleList[position].url)
            intent.putExtra(context.getString(R.string.urlToImage), articleList[position].urlToImage)
            intent.putExtra(context.getString(R.string.title), articleList[position].title)
            intent.putExtra(context.getString(R.string.publishedAt), articleList[position].publishedAt)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }


}