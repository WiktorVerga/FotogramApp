package com.example.fotogramapp.data.repository

import android.util.Log
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.domain.model.User

class UserRepository(
    private val database: AppDatabase,
    private val settingsRepository: SettingsRepository
) {

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
            postIds = listOf(1, 2, 3),
            isYourFollower = false,
            isYourFollowing = false
        ),

    )

    suspend fun getUser(id: Int): User? {
        val cachedUser = userDao.getUserById(id)

        val now = System.currentTimeMillis()

        if (cachedUser != null && (now - cachedUser.lastUpdated) < CACHE_TIMEOUT) {
            Log.d("ProfileViewModel", "Returning cached user")
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

    suspend fun isLoggedUser(userId: Int) = userId == settingsRepository.getLoggedUserId()

    suspend fun getLoggedUser() = settingsRepository.getLoggedUserId()?.let { getUser(it) }

    // == Update User ==
    suspend fun followUser(userId: Int) {
        //TODO: chiamata per fare il follow

        val user = getUser(userId)
        if (user != null) {
            user.isYourFollowing = true
            cacheUser(user)

        }
    }

    suspend fun unFollowUser(userId: Int) {
        //TODO: chiamata per fare il unfollow

        val user = getUser(userId)
        if (user != null) {
            user.isYourFollowing = false
            cacheUser(user)

        }
    }
}