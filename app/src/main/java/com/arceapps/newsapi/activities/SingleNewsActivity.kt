package com.arceapps.newsapi.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.arceapps.newsapi.R
import com.arceapps.newsapi.db.BookmarkDatabase
import com.arceapps.newsapi.db.BookmarkModel
import com.arceapps.newsapi.utils.UtilMethods.convertISOTime
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SingleNewsActivity : AppCompatActivity() {

    private lateinit var fabBookmarkBorder: FloatingActionButton
    private lateinit var fabBookmarkFilled: FloatingActionButton

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news)

        val image = findViewById<ImageView>(R.id.news_full_image)
        val bottomImage = findViewById<ImageView>(R.id.bottomImage)
        val title = findViewById<TextView>(R.id.news_full_headline_text)
        val description = findViewById<TextView>(R.id.news_full_description_text)
        val content = findViewById<TextView>(R.id.news_full_content_text)
        val date = findViewById<TextView>(R.id.news_full_date_text)
        val fullNews = findViewById<ConstraintLayout>(R.id.fullNews)
        fabBookmarkBorder =
            findViewById<FloatingActionButton>(R.id.fab_news_full_bookmark_border)
        fabBookmarkFilled =
            findViewById<FloatingActionButton>(R.id.news_full_fab_bookmark_filled)
        val fabShare = findViewById<FloatingActionButton>(R.id.news_full_fab_share)

        title.text = intent.getStringExtra(getString(R.string.title))
        if (intent.hasExtra(getString(R.string.description))) {
            try {
                description.text =
                    intent.getStringExtra(getString(R.string.description)).toString()
            }
            catch (e: Exception) {
                description.text = ""
            }
        }
        if (intent.hasExtra(getString(R.string.content))) {
            if (!intent.getStringExtra(getString(R.string.content)).isNullOrEmpty()) {
                if (intent.getStringExtra(getString(R.string.content)).toString().contains('['))
                    content.text = intent.getStringExtra(getString(R.string.content)).toString()
                        .substringBeforeLast('[')
                else
                    content.text = intent.getStringExtra(getString(R.string.content)).toString()
            }
            else {
                content.text = ""
                content.text = ""
            }
        }
        date.text = convertISOTime(
            applicationContext,
            intent.getStringExtra(getString(R.string.publishedAt))
        )

        Glide.with(this)
            .load(intent.getStringExtra(getString(R.string.urlToImage)))
            .placeholder(R.drawable.index)
            .into(image)

        Glide.with(this)
            .load(intent.getStringExtra(getString(R.string.urlToImage)))
            .placeholder(R.drawable.index)
            .into(bottomImage)

        checkBookmark()

        fabBookmarkBorder.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkAdded), Toast.LENGTH_SHORT).show()
            fabBookmarkBorder.visibility = View.GONE
            fabBookmarkFilled.visibility = View.VISIBLE

            var bookmark = BookmarkModel(
                intent.getStringExtra(getString(R.string.author)),
                "name",
                intent.getStringExtra(getString(R.string.title)),
                intent.getStringExtra(getString(R.string.description)),
                intent.getStringExtra(getString(R.string.url)),
                intent.getStringExtra(getString(R.string.urlToImage)),
                intent.getStringExtra(getString(R.string.publishedAt)),
                intent.getStringExtra(getString(R.string.content))
            )


            BookmarkDatabase(this).bookmarkDao().addBookmark(bookmark)

        }

        fabBookmarkFilled.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkRemoved), Toast.LENGTH_SHORT).show()
            fabBookmarkBorder.visibility = View.VISIBLE
            fabBookmarkFilled.visibility = View.GONE
            BookmarkDatabase(this).bookmarkDao().removeBookmark(intent.getStringExtra(getString(R.string.title))!!)
        }

        fabShare.setOnClickListener {
//            Toast.makeText(this, getString(R.string.share), Toast.LENGTH_SHORT).show()
            val imageUri = Uri.parse(intent.getStringExtra(getString(R.string.urlToImage)))
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "*${intent.getStringExtra(getString(R.string.title))}*\n\n" +
                            "${intent.getStringExtra(getString(R.string.description))}\n\n" +
                            "To read full news visit:\n${intent.getStringExtra(getString(R.string.url))}\n\n"
                            +"Download the News Up App from..."
                )
                type = "text/simple"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

        fullNews.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra(getString(R.string.url))))
            startActivity(browserIntent)
        }
    }

    @SuppressLint("RestrictedApi")
    fun checkBookmark() {
        var item = BookmarkDatabase(this).bookmarkDao().checkBookmark(
            intent.getStringExtra(
                getString(
                    R.string.title
                )
            )!!
        )
        if (!item.isNullOrEmpty()) {
            fabBookmarkBorder.visibility = View.GONE
            fabBookmarkFilled.visibility = View.VISIBLE
        }
    }

}