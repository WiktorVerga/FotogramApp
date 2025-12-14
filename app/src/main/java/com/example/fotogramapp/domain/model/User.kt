package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey val id: Int,
    var username: String,
    var biography: String,
    var birthDate: String,
    var profilePicture: String?,
    var isYourFollower: Boolean,
    var isYourFollowing: Boolean,
    var followersCount: Int,
    var followingCount: Int,
    var postCount: Int,
    var postIds: List<Int>,
    var lastUpdated: Long = System.currentTimeMillis()
) {
}