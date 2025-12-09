package com.example.fotogramapp.data.repository

import com.example.fotogramapp.domain.model.User

class UserRepository {

    private val allUsers = listOf<User>(
        User(
            id = 1,
            username = "First User",
            biography = "This is a biography",
            birthDate = "01/01/2002",
            profilePicture = "",
            followersCount = 10,
            followingCount = 3,
            postCount = 2,
            postIds = listOf(1, 2, 3)
        ),
        User(
            id = 2,
            username = "Second User",
            biography = "This another longer biography",
            birthDate = "14/06/2008",
            profilePicture = "",
            followersCount = 1,
            followingCount = 10,
            postCount = 1,
            postIds = listOf(4)
        )
    )

    fun getUser(id: Int): User {
        return allUsers.first { it.id == id }
    }
}