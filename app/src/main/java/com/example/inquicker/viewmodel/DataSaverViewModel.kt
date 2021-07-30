package com.example.inquicker.viewmodel

import androidx.lifecycle.*
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSaverViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    // LiveData from Room
    private var _getAllArticle=newsRepository.getAllArticle
    val getAllArticle : LiveData<List<Article>>
        get() = _getAllArticle


     fun insertArticle(article: Article){
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.upsertArticle(article)
        }
    }


    fun deleteArticle(article: Article){
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.deleteArticle(article)
        }
    }

}








class DataSaverViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataSaverViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataSaverViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}