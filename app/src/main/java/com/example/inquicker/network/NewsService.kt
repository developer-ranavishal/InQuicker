package com.example.inquicker.network

import com.example.inquicker.model.NewsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=3c7b584412f04fb6a72353208a012524
// https://newsapi.org/v2/top-headlines?country=in&apiKey=3c7b584412f04fb6a72353208a012524
//https://newsapi.org/v2/everything?q=apple&from=2021-06-05&to=2021-06-05&sortBy=popularity&apiKey=3c7b584412f04fb6a72353208a012524
private const val BASE_URL="https://newsapi.org/v2/"
private const val API_KEY="3c7b584412f04fb6a72353208a012524"
interface NewsApi{
    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getHeadLines(
        @Query("country") country : String,
        @Query("page")  page : Int) : Response<NewsResponse>

    @GET("everything?apiKey=$API_KEY")
    suspend fun getEverything(
        @Query("q")searchType: String,
        @Query("page")page :Int
    ) : Response<NewsResponse>
}

     // make singleton retrofit object
      object NewsService{
         private val retrofit =Retrofit.Builder().
         addConverterFactory(GsonConverterFactory.create()).
         baseUrl(BASE_URL).build()
         val retrofitService: NewsApi by lazy {
              retrofit.create(NewsApi::class.java)
          }
      }