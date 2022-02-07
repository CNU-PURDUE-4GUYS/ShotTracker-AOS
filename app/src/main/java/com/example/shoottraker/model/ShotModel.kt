package com.example.shoottraker.model

data class ShotModel(
    val date: String, // Shot date
    val imageUrl: String, // Image url
    val totalSet: Int, // Total set count
    val totalBullet: Int, // Bullet count per set
    val averageSize: Int // Size of target
)
