package com.arceapps.newsapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.arceapps.newsapi.adapters.HeadlinesRecyclerViewAdapter
import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.R
import com.arceapps.newsapi.databinding.ActivityTopStoriesActivityBinding
import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.RetrofitClient
import com.arceapps.newsapi.utils.UtilMethods
import com.arceapps.newsapi.viewmodel.TopStoriesViewModel
import com.arceapps.newsapi.db.BookmarkDatabase
import com.arceapps.newsapi.extensions.loadImageId
import retrofit2.Call

class TopStoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopStoriesActivityBinding
    private val viewModel: TopStoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopStoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getStringExtra("name")!!.isNotEmpty()){
            binding.layoutTopHeadlines.tvPageTitle.text = intent.getStringExtra("name")
            title = intent.getStringExtra("name")!!
        }

        observeNews()
        checkInternet()
        getNews()

    }

    override fun onRestart() {
        checkInternet()
        super.onRestart()
    }

    override fun onResume() {
        checkInternet()
        super.onResume()
    }

    private fun checkInternet(){
        if(!UtilMethods.isInternetAvailable(applicationContext)){
            binding.root.visibility = View.GONE
            binding.dialogNoInternet.root.visibility = View.VISIBLE
            binding.dialogNoInternet.noInternetImage.loadImageId(R.drawable.no_internet)
        }
        else{
            binding.root.visibility = View.VISIBLE
            binding.dialogNoInternet.root.visibility = View.GONE
        }
    }

    private fun getNews() {

        val apiInterface: ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java
        )

        var call: Call<ArticlesModel> = apiInterface.getArticlesModel()

        when (title) {
            "Entertainment" -> {
                viewModel.getEntertainment()

            }
            "Technology" -> {
                viewModel.getTechnology()
            }
            "Business" -> {
                viewModel.getBusiness()

            }
            "Sports" -> {
                viewModel.getSports()
            }
            "Medical" -> {
                viewModel.getMedical()

            }
            "Science" -> {
                viewModel.getScience()

            }
            "International" -> {
                viewModel.getInternational()

            }
            "Bookmarks" -> {

                BookmarkDatabase(this).bookmarkDao().getBookmarks().observe(this, Observer {

                    var myNewsList = mutableListOf<NewsHeadlines>()
                    for (i in it)
                        if (!i.urlToImage.isNullOrEmpty() ){

                            myNewsList.add(
                                NewsHeadlines(
                                    i.author,
                                    i.id.toString(),
                                    i.name,
                                    i.title,
                                    i.description,
                                    i.url,
                                    i.urlToImage,
                                    i.publishedAt,
                                    i.content
                                )
                            )

                        }

                    myNewsList.reverse()

                    if (myNewsList.isNullOrEmpty())
                        //noItem.visibility = View.VISIBLE
                    else {
                        //noItem.visibility = View.GONE
                    }

                    binding.layoutTopHeadlines.headlinesRecyclerView.adapter = HeadlinesRecyclerViewAdapter(applicationContext,myNewsList)
                })

            }
            else -> {
                viewModel.getArticles()
            }
        }
    }

    private fun observeNews() {
        viewModel.liveData.observe(this, Observer {

            var myNewsList = mutableListOf<NewsHeadlines>()
            for (i in it.articles)
                if (!i.urlToImage.isNullOrEmpty() ){

                    myNewsList.add(
                        NewsHeadlines(
                            i.author,
                            i.source.id,
                            i.source.name,
                            i.title,
                            i.description,
                            i.url,
                            i.urlToImage,
                            i.publishedAt,
                            i.content
                        )
                    )

                }


            binding.layoutTopHeadlines.headlinesRecyclerView.adapter = HeadlinesRecyclerViewAdapter(applicationContext,myNewsList)
        })
    }
}