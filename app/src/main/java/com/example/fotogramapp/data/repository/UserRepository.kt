package com.example.fotogramapp.data.repository

import android.util.Log
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.domain.model.User
import com.example.testing_apis.model.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import kotlin.collections.emptyMap

class UserRepository(
    private val settingsRepository: SettingsRepository,
    private val remoteDataSource: RemoteDataSource
) {

    private val _users = MutableStateFlow<Map<String, User>>(emptyMap())

    val users: StateFlow<Map<String, User>> = _users.asStateFlow()


    init {
        //If not First Access, provide SessionId to RemoteDataSource
        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.getSessionId()?.let { remoteDataSource.provideSessionId(it) }
        }
    }

    // == Cache Logic ==
    fun updateUserCache(user: User) {
        _users.update {
            it + (user.id.toString() to user)
        }
    }

    // == User Signup ==
    suspend fun userSignup(username: String, biography: String, dob: String, image: String): Pair<Int, String> {
        var signupResults = remoteDataSource.signUpUser()

        val (userId, sessionId) = signupResults

        //Provide SessionId to RemoteDataSource
        remoteDataSource.provideSessionId(sessionId)

        //Update created User with data from Signup
        remoteDataSource.updateUserDetails(
            username = username,
            bio = biography,
            dob = dob
        )

        //Add Profile Image to User
        remoteDataSource.upadteUserImage(
            base64 = image
        )

        return signupResults
    }

    suspend fun setLoggedUser(userId: Int, sessionId: String) {
        settingsRepository.setLoggedUser(userId, sessionId)
    }

    // == Update User ==
    suspend fun updateUserDetails(username: String?, biography: String?, dob: String?, image: String?) {
        var updatedUser: User? = null

        image?.let {
            updatedUser = remoteDataSource.upadteUserImage(
                base64 = it
            )
        }

        if (username != null || biography != null || dob != null) {
            updatedUser = remoteDataSource.updateUserDetails(
                username = username,
                bio = biography,
                dob = dob
            )
        }

        //Update Cache
        updatedUser?.let { updateUserCache(it) }
    }

    // == Get User ==
    suspend fun getUser(id: Int): User {
        val cachedUser = _users.value

        if (cachedUser.containsKey(id.toString())) {
            _users.value[id.toString()]?.let { return it }
        }

        //Fetch from Network
        try {
            val remoteUser = remoteDataSource.fetchUser(id)


            //Update Cache
            updateUserCache(remoteUser)

            return remoteUser
        } catch (e: APIException) {
            throw IOException(e.message)
        }
    }

    suspend fun isLoggedUser(userId: Int) = userId == settingsRepository.getLoggedUserId()

    suspend fun getLoggedUser() = settingsRepository.getLoggedUserId()?.let { getUser(it) }

    // == Follow Logic ==
    suspend fun followUser(targetId: Int) {
        remoteDataSource.followUser(targetId)

        //Update Cache
        var updatedUser = getUser(targetId)
        updatedUser.isYourFollowing = true
        updateUserCache(updatedUser)
    }

    suspend fun unFollowUser(targetId: Int) {
        remoteDataSource.unFollowUser(targetId)

        //Update Cache
        var updatedUser = getUser(targetId)
        updatedUser.isYourFollowing = false
        updateUserCache(updatedUser)
    }
}