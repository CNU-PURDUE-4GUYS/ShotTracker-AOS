package com.example.shoottraker.data

import java.util.*

data class Shot(
    val date : String, // Shot date
    val imageUrl : String, // Image url
    val totalSet : Int, // Total set count
    val bulletPerSet : Int, // Bullet count per set
)
