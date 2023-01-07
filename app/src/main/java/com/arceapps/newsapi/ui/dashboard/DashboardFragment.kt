package com.arceapps.newsapi.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.arceapps.newsapi.databinding.FragmentDashboardBinding
import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.RetrofitClient
import retrofit2.Call

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var title: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
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
            "Dashboard" -> {
                viewModel.getTopHeadlines()

            }
            else -> {
                viewModel.getGeneral()
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
                DashboardRecyclerViewAdapter(requireContext().applicationContext, myNewsList)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}