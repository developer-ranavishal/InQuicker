package com.example.inquicker.screen

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inquicker.adapter.NewsAdapter
import com.example.inquicker.adapter.OnItemClickListener
import com.example.inquicker.database.ArticleDatabase
import com.example.inquicker.databinding.FragmentBreakingNewsScreenBinding
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import com.example.inquicker.viewmodel.NewsViewModel
import com.example.inquicker.viewmodel.NewsViewModelFactory


class BreakingNewsScreen : Fragment(),OnItemClickListener{
    private lateinit var binding: FragmentBreakingNewsScreenBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsScreenBinding.inflate(layoutInflater)
        val database: ArticleDatabase = ArticleDatabase.getArticleDatabase(requireContext())
        val newsRepository = NewsRepository(database)
        val application=Application()
        val newsViewFactory = NewsViewModelFactory(newsRepository, application)
          newsViewModel = ViewModelProvider(this,newsViewFactory).get(NewsViewModel::class.java)
        binding.viewModel = newsViewModel
        adapter=NewsAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onClick(article: Article, position: Int) {
        Toast.makeText(requireContext(), "item  $position", Toast.LENGTH_SHORT).show()
            val action=BreakingNewsScreenDirections.actionBreakingNewsScreenToArticleScreen(article)
        findNavController().navigate(action)
    }

    // pagination
      var isLoading= false
     var isLastPage=false
    var isScrolling=false

    /**
     * RecyclerView.OnScrollListener() -> abstract class
    An OnScrollListener can be added to a RecyclerView to receive messages
    when a scrolling event has occurred on that RecyclerView
     */




}