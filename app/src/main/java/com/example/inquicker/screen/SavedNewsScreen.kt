package com.example.inquicker.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.inquicker.R
import com.example.inquicker.adapter.NewsAdapter
import com.example.inquicker.adapter.OnItemClickListener
import com.example.inquicker.database.ArticleDatabase
import com.example.inquicker.databinding.FragmentSavedNewsScreenBinding
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import com.example.inquicker.viewmodel.DataSaverViewModel
import com.example.inquicker.viewmodel.DataSaverViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SavedNewsScreen : Fragment(),OnItemClickListener {
    private lateinit var binding: FragmentSavedNewsScreenBinding
    private lateinit var saverViewModel: DataSaverViewModel
    private lateinit var adapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding= FragmentSavedNewsScreenBinding.inflate(layoutInflater)
        val database: ArticleDatabase = ArticleDatabase.getArticleDatabase(requireContext())
        val newsRepository= NewsRepository(database)
        val dataSaverViewModelFactory= DataSaverViewModelFactory(newsRepository)
        saverViewModel= ViewModelProvider(this,dataSaverViewModelFactory).get(DataSaverViewModel::class.java)
        adapter=NewsAdapter(this)
        binding.savedRecyclerView.adapter=adapter
          binding.saveViewModel=saverViewModel
        binding.lifecycleOwner=viewLifecycleOwner
      val itemTouchHelper= object : ItemTouchHelper.SimpleCallback(
          ItemTouchHelper.UP or ItemTouchHelper.DOWN,
          ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
      ) {
          override fun onMove(
              recyclerView: RecyclerView,
              viewHolder: RecyclerView.ViewHolder,
              target: RecyclerView.ViewHolder
          )=true



          @SuppressLint("ShowToast")
          override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position= viewHolder.adapterPosition
                  val article=adapter.articleList[position]
                 saverViewModel.deleteArticle(article)
              Snackbar.make(requireView(),"Successfully deleted article",Snackbar.LENGTH_SHORT).
                      setAction("Undo"){
                          saverViewModel.insertArticle(article)
                      }
                  .show()
          }

      }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.savedRecyclerView)
        return binding.root
    }


    override fun onClick(article: Article, position: Int) {
      val action= SavedNewsScreenDirections.actionSavedNewsScreenToArticleScreen(article)
        findNavController().navigate(action)
    }

}