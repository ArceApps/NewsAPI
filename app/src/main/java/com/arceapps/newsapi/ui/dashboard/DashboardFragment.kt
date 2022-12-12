package com.arceapps.newsapi.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.arceapps.newsapi.adapters.HeadlinesRecyclerViewAdapter
import com.arceapps.newsapi.databinding.FragmentDashboardBinding
import com.arceapps.newsapi.db.BookmarkDatabase
import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.RetrofitClient
import com.arceapps.newsapi.viewmodel.TopStoriesViewModel
import retrofit2.Call

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopStoriesViewModel by viewModels()
    private lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = (requireContext() as AppCompatActivity).supportActionBar?.title.toString()
        binding.layoutTopHeadlines.tvPageTitle.text = title

        observeNews()
        getNews()
    }

    private fun getNews() {

        val apiInterface: ApiInterface = RetrofitClient.getClient().create(
            ApiInterface::class.java
        )

        var call: Call<ArticlesModel> = apiInterface.getArticlesModel()

        Log.d("Dashboard_title: ", title)
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

                BookmarkDatabase(requireContext().applicationContext).bookmarkDao().getBookmarks().observe(viewLifecycleOwner, Observer {

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
                        binding.layoutTopHeadlines.noItem.visibility = View.VISIBLE
                    else {
                        binding.layoutTopHeadlines.noItem.visibility = View.GONE
                    }

                    binding.layoutTopHeadlines.headlinesRecyclerView.adapter =
                        HeadlinesRecyclerViewAdapter(requireContext().applicationContext, myNewsList)
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


            binding.layoutTopHeadlines.headlinesRecyclerView.adapter =
                HeadlinesRecyclerViewAdapter(requireContext().applicationContext, myNewsList)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}