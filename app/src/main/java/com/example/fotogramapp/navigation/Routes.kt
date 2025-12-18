package com.example.fotogramapp.navigation

import kotlinx.serialization.Serializable
import com.example.fotogramapp.domain.model.User

//Route without parameters
@Serializable
object SignUp

@Serializable
object Discover

@Serializable
object CreatePost

//Route with parameters
@Serializable
data class Profile(val id: Int?)

@Serializable
data class MapPage(
    val startingLongitude: Double,
    val startingLatitude: Double,
    val zoom: Double
)

@Serializable
data class EditProfile(
    val username: String,
    val biography: String,
    val dob: String,
    val image: String
)
