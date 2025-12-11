package com.example.fotogramapp.data.repository

import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.domain.model.User

class UserRepository(private val database: AppDatabase) {

    val userDao = database.userDao()

    companion object {
        private const val CACHE_TIMEOUT = 60 * 60 * 1000 // 1 ora
    }

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

    suspend fun getUser(id: Int): User? {
        val cachedUser = userDao.getUserById(id)

        val now = System.currentTimeMillis()

        if (cachedUser != null && (now - cachedUser.lastUpdated) < CACHE_TIMEOUT) {
            return cachedUser
        } else {
            //TODO: prendi dalla rete
            val remoteUser = allUsers.find { it.id == id }

            if (remoteUser != null) {
                remoteUser.lastUpdated = now

                //Riaggiorno la Cache
                cacheUser(remoteUser)

                return remoteUser
            }

            return null
        }
    }

    suspend fun cacheUser(user: User) {
        userDao.clear(user.id)
        userDao.insertUser(user)
    }
}