package com.arceapps.newsapi.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arceapps.newsapi.R
import com.arceapps.newsapi.databinding.ActivitySingleNewsBinding
import com.arceapps.newsapi.db.BookmarkDatabase
import com.arceapps.newsapi.db.BookmarkModel
import com.arceapps.newsapi.utils.UtilMethods.convertISOTime
import com.bumptech.glide.Glide

class SingleNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleNewsBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsFullHeadlineText.text = intent.getStringExtra(getString(R.string.title))
        if (intent.hasExtra(getString(R.string.description))) {
            try {
                binding.newsFullDescriptionText.text =
                    intent.getStringExtra(getString(R.string.description)).toString()
            }
            catch (e: Exception) {
                binding.newsFullDescriptionText.text = ""
            }
        }
        if (intent.hasExtra(getString(R.string.content))) {
            if (!intent.getStringExtra(getString(R.string.content)).isNullOrEmpty()) {
                if (intent.getStringExtra(getString(R.string.content)).toString().contains('['))
                    binding.newsFullContentText.text = intent.getStringExtra(getString(R.string.content)).toString()
                        .substringBeforeLast('[')
                else
                    binding.newsFullContentText.text = intent.getStringExtra(getString(R.string.content)).toString()
            }
            else {
                binding.newsFullContentText.text = ""
            }
        }
        binding.newsFullDateText.text = convertISOTime(
            applicationContext,
            intent.getStringExtra(getString(R.string.publishedAt))
        )

        Glide.with(this)
            .load(intent.getStringExtra(getString(R.string.urlToImage)))
            .placeholder(R.drawable.index)
            .into(binding.newsFullImage)

        Glide.with(this)
            .load(intent.getStringExtra(getString(R.string.urlToImage)))
            .placeholder(R.drawable.index)
            .into(binding.bottomImage)

        checkBookmark()

        binding.fabNewsFullBookmarkBorder.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkAdded), Toast.LENGTH_SHORT).show()
            binding.fabNewsFullBookmarkBorder.visibility = View.GONE
            binding.newsFullFabBookmarkFilled.visibility = View.VISIBLE

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

        binding.newsFullFabBookmarkFilled.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkRemoved), Toast.LENGTH_SHORT).show()
            binding.fabNewsFullBookmarkBorder.visibility = View.VISIBLE
            binding.newsFullFabBookmarkFilled.visibility = View.GONE
            BookmarkDatabase(this).bookmarkDao().removeBookmark(intent.getStringExtra(getString(R.string.title))!!)
        }

        binding.newsFullFabShare.setOnClickListener {
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

        binding.fullNews.setOnClickListener {
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
            binding.fabNewsFullBookmarkBorder.visibility = View.GONE
            binding.newsFullFabBookmarkFilled.visibility = View.VISIBLE
        }
    }

}