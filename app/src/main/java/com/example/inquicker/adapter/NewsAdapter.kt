package com.example.inquicker.adapter

import android.graphics.Color
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inquicker.databinding.LayoutArticleItemBinding
import com.example.inquicker.model.Article

const val TAG="NewsAdapter"
class NewsAdapter(private val listener : OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    var articleList= listOf<Article>()
    class ArticleViewHolder(private val binding: LayoutArticleItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingArticle(article : Article){
          binding.article=article
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
       ArticleViewHolder(LayoutArticleItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
              val currentItem= articleList[position]
             holder.bindingArticle(currentItem)
            holder.itemView.setOnClickListener {
                listener.onClick(currentItem,position)
            }
        
    }

    override fun getItemCount()=articleList.size

    fun refreshArticleList(newArticleList : List<Article>){
            articleList=newArticleList
          notifyDataSetChanged()    // imp line
    }

}

interface OnItemClickListener {
     fun onClick(article : Article,position: Int)

}
