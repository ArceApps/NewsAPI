package com.arceapps.newsapi.ui.bookmarks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.arceapps.newsapi.databinding.FragmentBookmarksBinding
import com.arceapps.newsapi.db.BookmarkDatabase
import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.RetrofitClient
import retrofit2.Call

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarksViewModel by viewModels()
    private lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = (requireContext() as AppCompatActivity).supportActionBar?.title.toString()
        binding.layoutBookmarks.tvPageTitle.text = title

        observeNews()
        getNews()
    }

    private fun getNews() {

        val apiInterface: ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java
        )

        var call: Call<ArticlesModel> = apiInterface.getArticlesModel()

        Log.d("Bookmarks_title: ", title)
        when (title) {
            "Bookmarks" -> {
                BookmarkDatabase(requireContext().applicationContext).bookmarkDao().getBookmarks()
                    .observe(viewLifecycleOwner, Observer {

                        var myNewsList = mutableListOf<NewsHeadlines>()
                        for (i in it)
                            if (!i.urlToImage.isNullOrEmpty()) {

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
                            binding.layoutBookmarks.noItem.visibility = View.VISIBLE
                        else {
                            binding.layoutBookmarks.noItem.visibility = View.GONE
                        }

                        binding.layoutBookmarks.bookmarksRecyclerView.adapter =
                            BookmarksRecyclerViewAdapter(
                                requireContext().applicationContext,
                                myNewsList
                            )
                    })

            }
            else -> {
                viewModel.getArticles()
            }
        }
    }

    private fun observeNews() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer {

            var myNewsList = mutableListOf<NewsHeadlines>()
            for (i in it.articles)
                if (!i.urlToImage.isNullOrEmpty()) {

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


            binding.layoutBookmarks.bookmarksRecyclerView.adapter =
                BookmarksRecyclerViewAdapter(requireContext().applicationContext, myNewsList)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}