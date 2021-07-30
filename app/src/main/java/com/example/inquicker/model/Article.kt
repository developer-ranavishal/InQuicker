package com.example.inquicker.model

import android.os.Parcelable
import androidx.databinding.adapters.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable {
    @IgnoredOnParcel
    var source: Source?=null
}