package com.example.inquicker.bindingadapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.inquicker.R
import com.example.inquicker.adapter.NewsAdapter
import com.example.inquicker.model.Article
import com.example.inquicker.viewmodel.CheckInternet
import com.example.inquicker.viewmodel.NewsState

private const val TAG="BindingAdapter"

@SuppressLint("CheckResult")
@BindingAdapter("loadingImage")
fun loadImg(imageView: ImageView,url : String?){
    Log.d(TAG, "url is $url")
    if (url!=null)
             Glide.with(imageView.context).
              load(url).
            apply(RequestOptions().error(R.drawable.ic_broken_image)).
             into(imageView)
}

@BindingAdapter("setRecyclerView")
    fun bindBreakingNewsRV(recyclerView: RecyclerView, articleList : List<Article>?){
              val adapter=recyclerView.adapter as NewsAdapter
                 if (articleList!=null)
                     adapter.refreshArticleList(articleList)
     }

@BindingAdapter("responseState")
   fun checkResponseState(stateImageView: ImageView,state : NewsState?){
       when (state){
             NewsState.LOADING -> {
                   stateImageView.visibility=View.VISIBLE
               // stateImageView.setImageResource(R.drawable.loading_animation)
             }
               NewsState.DONE -> {
                   stateImageView.visibility=View.GONE
               }
            NewsState.ERROR ->{
                stateImageView.visibility=View.VISIBLE
           //     stateImageView.setImageResource(R.drawable.ic_error)
            }
       }

}

@BindingAdapter("setSearchNewsRV")
fun bindSearchNewsRV(recyclerView: RecyclerView,searchNewsList: List<Article>?){
    val adapter=recyclerView.adapter as NewsAdapter
       if (searchNewsList!=null)
           adapter.refreshArticleList(searchNewsList)
}

// used to show the list of news in room database table in recyclerView
@BindingAdapter("setSaveArticleRv")
fun bindSaveNewsRv(recyclerView: RecyclerView, saveList : List<Article>?){
    val adapter=recyclerView.adapter as NewsAdapter
    if (saveList!=null)
        adapter.refreshArticleList(saveList)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("isInternet")
fun checkInternet(textView: TextView,checkInternet: CheckInternet?){
    when(checkInternet){
        CheckInternet.INTERNET  -> textView.visibility=View.GONE
        CheckInternet.NO_INTERNET -> {
            textView.visibility = View.VISIBLE
            textView.text="Oops! No Internet Connection!"
        }
    }
}
