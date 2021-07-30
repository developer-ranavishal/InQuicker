package com.example.inquicker.screen
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.inquicker.R
import com.example.inquicker.database.ArticleDatabase
import com.example.inquicker.databinding.FragmentNewsArticleScreenBinding
import com.example.inquicker.model.Article
import com.example.inquicker.repository.NewsRepository
import com.example.inquicker.viewmodel.DataSaverViewModel
import com.example.inquicker.viewmodel.DataSaverViewModelFactory
import com.google.android.material.snackbar.Snackbar

class NewsArticleScreen : Fragment() {
    private lateinit var binding: FragmentNewsArticleScreenBinding
    private val args: NewsArticleScreenArgs by navArgs()
    private lateinit var saverViewModel: DataSaverViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val article = args.article
        binding = FragmentNewsArticleScreenBinding.inflate(layoutInflater)
        val database: ArticleDatabase = ArticleDatabase.getArticleDatabase(requireContext())
         val newsRepository=NewsRepository(database)
        val dataSaverViewModelFactory=DataSaverViewModelFactory(newsRepository)
        saverViewModel=ViewModelProvider(this,dataSaverViewModelFactory).get(DataSaverViewModel::class.java)

        binding.floatingActionSaveButton.setOnClickListener {
            saveCurrentArticle(article)
        }

        webView(article.url)
        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun webView(url: String) {
        binding.webView.apply {
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = true
            settings.setSupportZoom(true)
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                    visibility = View.VISIBLE
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility = View.VISIBLE
                    visibility = View.VISIBLE
                }
            }
            loadUrl(url)
        }
    }

    private fun saveCurrentArticle(article: Article){
        saverViewModel.insertArticle(article)
        Snackbar.make(requireView(),"Article saved successfully",Snackbar.LENGTH_SHORT).show()
    }
}