package com.example.inquicker.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class NewsState { LOADING, ERROR, DONE }
enum class CheckInternet{NO_INTERNET, INTERNET}
const val TAG="NewsViewModel"
class NewsViewModel(private val newsRepository: NewsRepository, application: Application) : AndroidViewModel(application) {

    // Livedata that notify the article list from top-headline response
    private var _newsArticleList = MutableLiveData<List<Article>>()
    val newsArticleList: LiveData<List<Article>>
        get() = _newsArticleList

    //Livedata that notify the article list from searching-news response
    private var _searchNewsList = MutableLiveData<List<Article>>()
    val searchNewsList: LiveData<List<Article>>
        get() = _searchNewsList

    // Livedata that notify the response state
    private var _responseState = MutableLiveData<NewsState>()
    val responseState: LiveData<NewsState>
        get() = _responseState

    // Livedata to check internet connectivity
    private var _internetStatus = MutableLiveData<CheckInternet>()
    val internetStatus: LiveData<CheckInternet>
        get() = _internetStatus

    // create to jobs to control the lifetime of two network calls
    private var breakingNewsJob: Job = Job()
    private var searchingNewsJob: Job = Job()


    init {
        internetChecking(application)
    }

    private fun getBreakingNews() {
        breakingNewsJob = viewModelScope.launch {
            val response = newsRepository.getBreakingNews("in", 1)
            val newsResponse = response.body()
            try {
                if (newsResponse != null)
                    _newsArticleList.value = newsResponse.articles
            } catch (t: Throwable) {
                Log.d(TAG, "error at time of network data fetching!")
            }

        }
    }

    fun getSearchingNews(searchQuery: String) {
        searchingNewsJob = viewModelScope.launch {
            val response = newsRepository.getSearchNews(searchQuery, 1)
            val newsResponse = response.body()
            try {
                if (newsResponse != null)
                    _searchNewsList.value = newsResponse.articles
            } catch (t: Throwable) {
                Log.d(TAG, "error at time of network data fetching!")
            }

        }
    }


    override fun onCleared() {
        super.onCleared()
        breakingNewsJob.cancel()
        searchingNewsJob.cancel()
    }


    private fun internetChecking(application: Application) {
        if (isOnline(getApplication())) {
            Log.d(TAG, "safeNetworkCall:  network is ready ")
              getBreakingNews()

        } else {
            Log.d(TAG, "safeNetworkCall: network is not ready")

        }

    }

    // Internet
    private fun isOnline(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.d("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}


class NewsViewModelFactory(private val newsRepository: NewsRepository,private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsRepository,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
