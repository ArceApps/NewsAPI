package com.arceapps.newsapi.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arceapps.newsapi.R
import com.arceapps.newsapi.adapters.PreferencesViewPagerAdapter
import com.arceapps.newsapi.adapters.SuggestedTopicsRecyclerViewAdapter
import com.arceapps.newsapi.adapters.TopStoriesHomeRecyclerViewAdapter
import com.arceapps.newsapi.databinding.FragmentHomeBinding
import com.arceapps.newsapi.model.NewsHeadlines
import com.arceapps.newsapi.model.SuggestedTopics
import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.RetrofitClient
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    lateinit var apiInterface: ApiInterface
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiInterface = RetrofitClient.getClient().create(ApiInterface::class.java)

        getCurrentTime()
        getTopics()
        observeNews()

        viewModel.getGeneral()
        viewModel.getTopHeadlines()

        binding.mainRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_dark,
            android.R.color.holo_red_dark,
            android.R.color.holo_purple,
            android.R.color.holo_green_dark
        )

        binding.mainRefreshLayout.setOnRefreshListener {
            getCurrentTime()
            observeNews()
        }

        binding.layoutMain.homeViewPager.clipToPadding = false
        Log.e("Screen Width", getScreenWidth().toString())
        binding.layoutMain.homeViewPager.setPadding(0, 0, (getScreenWidth()*0.2).toInt(), 0)

        binding.layoutMain.viewAllTopStories.setOnClickListener {
            val bundle = bundleOf("name" to "Dashboard")
            view.findNavController().navigate(R.id.navigation_dashboard, bundle)
        }

        binding.layoutMain.viewBookmarks.setOnClickListener {
            val bundle = bundleOf("name" to "Bookmarks")
            view.findNavController().navigate(R.id.navigation_bookmarks, bundle)
        }
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    private fun getTopics() {
        val suggestedTopics = mutableListOf(
            SuggestedTopics(R.drawable.topic_business, "Business"),
            SuggestedTopics(R.drawable.topic_entertainment, "Entertainment"),
            SuggestedTopics(R.drawable.topic_sports, "Sports"),
            SuggestedTopics(R.drawable.topic_science, "Science"),
            SuggestedTopics(R.drawable.topic_technology, "Technology"),
            SuggestedTopics(R.drawable.topic_medical, "Medical"),
        )

        binding.layoutMain.suggestedTopicsRecyclerView.layoutManager = GridLayoutManager(requireContext().applicationContext, 3)
        binding.layoutMain.suggestedTopicsRecyclerView.adapter =
            SuggestedTopicsRecyclerViewAdapter(requireContext().applicationContext, suggestedTopics)
    }

    private fun getCurrentTime() {

        val dateFormatter = SimpleDateFormat("HH")
        dateFormatter.isLenient = false
        val today = Date()
        val s = dateFormatter.format(today).toInt()

        binding.layoutMain.welcomeTextView.text = when {
            s < 5 -> "Welcome!"
            s in 5..11 -> "Good Morning!"
            s in 12..18 -> "Good Afternoon!"
            else -> "Good Evening!"
        }
    }

    private fun observeNews() {

        viewModel.topliveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val myNewsList = mutableListOf<NewsHeadlines>()

            for (i in it.articles) {

                if (i.urlToImage != null) {

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

                binding.mainRefreshLayout.isRefreshing = false

            }

            binding.layoutMain.topStoriesRecyclerView.adapter =
                TopStoriesHomeRecyclerViewAdapter(requireContext().applicationContext, myNewsList)

            Log.d("List Items: ", myNewsList.size.toString())

        })

        viewModel.liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val prefList = mutableListOf<NewsHeadlines>()

            for (i in it.articles) {

                if (i.urlToImage != null) {

                    prefList.add(
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

                binding.mainRefreshLayout.isRefreshing = false

            }

            binding.layoutMain.homeViewPager.adapter =
                PreferencesViewPagerAdapter(requireContext().applicationContext, prefList)

            Log.d("List Items: ", prefList.size.toString())

        })
    }


    override fun onStart() {
        super.onStart()
        getCurrentTime()
    }

    override fun onResume() {
        getCurrentTime()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}