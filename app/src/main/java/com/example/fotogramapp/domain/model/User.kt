package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: Int,
    val createdAt: String,
    val username: String = "Unkown User",
    val bio: String = "We don't know much about this user",
    val dateOfBirth: String = "Unknown",
    val profilePicture: String? = null,
    val isYourFollower: Boolean,
    var isYourFollowing: Boolean,
    val followersCount: Int,
    val followingCount: Int,
    val postsCount: Int
)