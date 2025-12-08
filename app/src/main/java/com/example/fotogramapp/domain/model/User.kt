package com.example.fotogramapp.domain.model

class User(
    val id: Int,
    val username: String,
    val biography: String,
    val birthDate: String,
    val profilePicture: String,
    val followersCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val postIds: List<Int>
) {
}