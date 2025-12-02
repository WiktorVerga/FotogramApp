package com.example.fotogramapp.navigation

import kotlinx.serialization.Serializable

//Route without parameters
@Serializable
object SignUp

//Route with parameters
@Serializable
data class Login(val email: String, val password: String)