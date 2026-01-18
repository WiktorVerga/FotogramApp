package com.example.fotogramapp.navigation

import kotlinx.serialization.Serializable
import com.example.fotogramapp.domain.model.User

// == Routes without Parameters ==
@Serializable
object SignUp

@Serializable
object Discover

@Serializable
object CreatePost

// == Routes with Parameters ==
@Serializable
data class Profile(val id: Int?)

@Serializable
data class MapPage(
    val startingLongitude: Double?,
    val startingLatitude: Double?
)

@Serializable
data class EditProfile(
    val username: String,
    val biography: String,
    val dob: String,
    val image: String
)
