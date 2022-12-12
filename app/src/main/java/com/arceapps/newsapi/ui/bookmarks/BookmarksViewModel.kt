package com.arceapps.newsapi.ui.bookmarks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arceapps.newsapi.NetworkRepository
import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksViewModel : ViewModel() {

    private val repository = NetworkRepository(ApiInterface())

    private var mutableLiveData = MutableLiveData<ArticlesModel>()
    val liveData: LiveData<ArticlesModel>

    private var mutableTopLiveData = MutableLiveData<ArticlesModel>()
    val topliveData: LiveData<ArticlesModel>

    init {
        liveData = mutableLiveData
        topliveData = mutableTopLiveData
    }

    fun getArticles () {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableLiveData.postValue(repository.getArticles())
            }
            catch (e: Exception) {
                Log.e("Get Feeds", e.message!!)
            }
        }
    }
}