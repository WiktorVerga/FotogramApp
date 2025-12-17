package com.example.fotogramapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// == User ==
@Serializable
data class UserLogged (
    @SerialName("userId") val userId: Int,
    @SerialName("sessionId") val sessionId: String
)

@Serializable
data class UserUpdateParams(
    @SerialName("username") val username: String,
    @SerialName("bio") val bio: String,
    @SerialName("dateOfBirth") val dob: String
)

@Serializable
data class UserImageParams(
    @SerialName("base65") val base64: String
)

// == MapBox ==

@Serializable
data class GeocodingResponse(
    val features: List<Feature>
)

@Serializable
data class Feature(
    val place_name: String
)

// == Errors ==
@Serializable
data class RemoteError(
    @SerialName("message") val message: String
)

class APIException(message: String) : Exception(message)