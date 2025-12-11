package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey val id: Int,
    val username: String,
    val biography: String,
    val birthDate: String,
    val profilePicture: String?,
    val followersCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val postIds: List<Int>,
    var lastUpdated: Long = System.currentTimeMillis()
) {
}