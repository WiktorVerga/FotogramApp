package com.example.fotogramapp.navigation

import kotlinx.serialization.Serializable

//Route without parameters
@Serializable
object SignUp

@Serializable
object Discover

@Serializable
object CreatePost

//Route with parameters
@Serializable
data class Profile(val id: Int)

@Serializable
data class MapPage(val startingLongitude: Double, val startingLatitude: Double, val zoom: Double)
