package com.example.assignment1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val username: String,
    val caption: String,
    val imageRes: String,
    var likes: Int,
    var isLiked: Boolean = false
) : Parcelable