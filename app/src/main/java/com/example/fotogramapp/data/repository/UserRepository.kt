package com.example.fotogramapp.data.repository

import com.example.fotogramapp.domain.model.User
import com.example.testing_apis.model.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyMap

class UserRepository(
    private val settingsRepository: SettingsRepository
) {
    private val remoteDataSource = RemoteDataSource()

    private val _users = MutableStateFlow<Map<String, User>>(emptyMap())

    val users: StateFlow<Map<String, User>> = _users.asStateFlow()


    init {
        //Se già fatto l'accesso, Fornisco il sessionId al remoteDataSource
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

        //Fornisco il sessionId al remoteDataSource
        remoteDataSource.provideSessionId(sessionId)

        //Aggiorno l'utente creato con i dati inseriti dall'utente
        remoteDataSource.updateUserDetails(
            username = username,
            bio = biography,
            dob = dob
        )

        //Aggiungo l'immagine profilo
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

        //Prendo l'utente aggiornato e aggiorno la cache
        updatedUser?.let { updateUserCache(it) }
    }

    // == Get User ==
    suspend fun getUser(id: Int): User {
        val cachedUser = _users.value

        if (cachedUser.containsKey(id.toString())) {
            _users.value[id.toString()]?.let { return it }
        }

        //Prendo da Rete
        val remoteUser = remoteDataSource.fetchUser(id)

        //Aggiorno la cache
        updateUserCache(remoteUser)

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