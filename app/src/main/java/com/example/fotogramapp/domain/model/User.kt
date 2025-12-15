package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: Int,
    val createdAt: String,
    val username: String,
    val bio: String,
    val dateOfBirth: String,
    val profilePicture: String,
    val isYourFollower: Boolean,
    val isYourFollowing: Boolean,
    val followersCount: Int,
    val followingCount: Int,
    val postsCount: Int
)