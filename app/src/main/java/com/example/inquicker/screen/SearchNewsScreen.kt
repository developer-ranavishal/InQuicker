 package com.example.inquicker.screen

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inquicker.adapter.NewsAdapter
import com.example.inquicker.adapter.OnItemClickListener
import com.example.inquicker.database.ArticleDatabase
import com.example.inquicker.databinding.FragmentSearchNewsScreenBinding
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import com.example.inquicker.viewmodel.NewsViewModel
import com.example.inquicker.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val SEARCH_NEWS_TIME_DELAY=500L
class SearchNewsScreen : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentSearchNewsScreenBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchNewsScreenBinding.inflate(layoutInflater)
        val database: ArticleDatabase = ArticleDatabase.getArticleDatabase(requireContext())
        val newsRepository = NewsRepository(database)

        val application=Application()
        val newsViewFactory = NewsViewModelFactory(newsRepository,application)
         newsViewModel = ViewModelProvider(this,newsViewFactory).get(NewsViewModel::class.java)
        binding.viewModel = newsViewModel
        adapter=NewsAdapter(this)
        binding.searchRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        searchingNews()
        return binding.root
    }

    private fun searchingNews(){
        var job : Job?=null
        binding.searchNewsEtxt.addTextChangedListener {searchTxt ->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                  searchTxt.let {
                      if (searchTxt.toString().isNotEmpty())
                       newsViewModel.getSearchingNews(searchTxt.toString())
                      else
                          adapter.refreshArticleList(emptyList())
                  }
            }
        }
    }

    override fun onClick(article: Article, position: Int) {
          val action=SearchNewsScreenDirections.actionSearchNewsScreenToArticleScreen(article)
         findNavController().navigate(action)
    }
}