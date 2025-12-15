package com.example.fotogramapp.data.repository

import android.util.Log
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.domain.model.User
import com.example.testing_apis.model.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(
    private val database: AppDatabase,
    private val settingsRepository: SettingsRepository
) {
    private lateinit var remoteDataSource: RemoteDataSource

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val sessionId = settingsRepository.getSessionId()

            if (sessionId != null) {
                remoteDataSource = RemoteDataSource(sessionId)
            }
        }
    }

    // == Get User ==

    suspend fun getUser(id: Int): User? {
        //Prendo da Rete
        val remoteUser = remoteDataSource.getUserDetails(id)

        return remoteUser
    }

    suspend fun isLoggedUser(userId: Int) = userId == settingsRepository.getLoggedUserId()

    suspend fun getLoggedUser() = settingsRepository.getLoggedUserId()?.let { getUser(it) }

    // == Update User ==
    suspend fun followUser(userId: Int) {
        //TODO: chiamata per fare il follow
        TODO()
    }

    suspend fun unFollowUser(userId: Int) {
        //TODO: chiamata per fare il unfollow

        TODO()
    }
}