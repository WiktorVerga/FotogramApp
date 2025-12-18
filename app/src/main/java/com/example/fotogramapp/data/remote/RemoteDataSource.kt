package com.example.testing_apis.model

import android.util.Log
import androidx.core.net.toUri
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.remote.GeocodingResponse
import com.example.fotogramapp.data.remote.Location
import com.example.fotogramapp.data.remote.RemoteError
import com.example.fotogramapp.data.remote.UserLogged
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.domain.model.User
import com.mapbox.geojson.Point
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json

class RemoteDataSource(val sessionId: String = "") {

    private var SESSION_ID = sessionId

    fun provideSessionId(value: String) {
        SESSION_ID = value
    }

    companion object {
        private const val BASE_URL = "https://develop.ewlab.di.unimi.it/mc/2526/"
        private val TAG = "RemoteDataSource"

        enum class HttpMethod {
            GET,
            POST,
            DELETE,
            PUT
        }
    }

    private val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
        }
    }

    suspend fun genericRequest(
        endpoint: String,
        method: HttpMethod,
        bodyParams: Any? = null,
        queryParams: Map<String, Any> = emptyMap(),
    ) : HttpResponse {

        // Build the URL
        val urlUri = (BASE_URL + endpoint).toUri()
        val urlBuilder = urlUri.buildUpon()
        queryParams.forEach { (key, value) ->
            urlBuilder.appendQueryParameter(key, value.toString())
        }
        val completeUrlString = urlBuilder.build().toString()

        // Build the Request Header
        Log.d(TAG, "Request to: $completeUrlString")
        val request: HttpRequestBuilder.() -> Unit = {
            bodyParams?.let {
                contentType(ContentType.Application.Json)
                setBody(bodyParams)
            }
            headers {
                append("x-session-id",
                    SESSION_ID
                )
            }
        }

        // Make the Request and return the Response

        val httpResponse = when (method) {
            HttpMethod.GET -> client.get(completeUrlString, request)
            HttpMethod.POST -> client.post(completeUrlString, request)
            HttpMethod.DELETE -> client.delete(completeUrlString, request)
            HttpMethod.PUT -> client.put(completeUrlString, request)
        }
        return httpResponse
    }

    // == User API ==

    suspend fun signUpUser(): Pair<Int, String> {
        val httpResponse = genericRequest(
            endpoint = "user",
            method = HttpMethod.POST
        )

        when(httpResponse.status.value) {
            200 -> {
                val responseObj = httpResponse.body<UserLogged>()

                return Pair(responseObj.userId, responseObj.sessionId)
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    suspend fun updateUserDetails(
        username: String? = null,
        bio: String? = null,
        dob: String? = null
    ): User {

        val bodyParams = mutableMapOf<String, Any>()

        username?.let { bodyParams["username"] = it }
        bio?.let { bodyParams["bio"] = it }
        dob?.let { bodyParams["dateOfBirth"] = it }

        val httpResponse = genericRequest(
            "user",
            HttpMethod.PUT,
            bodyParams = bodyParams
        )

         when (httpResponse.status.value) {
            200 -> return httpResponse.body<User>()

            else -> throw APIException(httpResponse.body<RemoteError>().message)
        }
    }

    suspend fun upadteUserImage (
        base64: String
    ): User {
        val httpResponse = genericRequest(
            endpoint = "user/image",
            method = HttpMethod.PUT,
            bodyParams = mapOf(
                "base64" to base64
            )
        )

        when (httpResponse.status.value) {
            200 -> return httpResponse.body<User>()

            else -> throw APIException(httpResponse.body<RemoteError>().message)
        }
    }

    suspend fun fetchUser(
        userId: Int
    ): User {
        val httpResponse = genericRequest(
            endpoint = "user/$userId",
            method = HttpMethod.GET
        )

        when (httpResponse.status.value) {
            200 -> {
                return httpResponse.body<User>()
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    // == Posts API ==
    suspend fun createPost(message: String, image: String, location: Point?): Post {

        val bodyParams = mutableMapOf<String, Any>(
            "contentText" to message,
            "contentPicture" to image,
        )
        location?.let {
            bodyParams["location"] = Location(it.latitude(), it.longitude())
        }

        val httpResponse = genericRequest(
            endpoint = "post",
            method = HttpMethod.POST,
            bodyParams = bodyParams
        )

        when (httpResponse.status.value) {
            200 -> {
                return httpResponse.body<Post>()
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    suspend fun fetchPost(postId: Int): Post {
        val httpResponse = genericRequest(
            endpoint = "post/$postId",
            method = HttpMethod.GET
        )

        Log.d("fetchPost", httpResponse.body<String>())

        when (httpResponse.status.value) {
            200 -> {
                return httpResponse.body<Post>()
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    suspend fun deletePost(postId: Int) {
        val httpResponse = genericRequest(
            endpoint = "post/$postId",
            method = HttpMethod.DELETE
        )

        when (httpResponse.status.value) {
            204 -> {
                return
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    suspend fun fetchAuthorPosts(authorId: Int, maxPostId: Int? = null, limit: Int? = null): List<Int> {
        val queryParams = mutableMapOf<String, Any>()
        maxPostId?.let {
            queryParams["maxPostId"] = it
        }
        limit?.let {
            queryParams["limit"] = it
        }

        val httpResponse = genericRequest(
            endpoint = "post/list/$authorId",
            method = HttpMethod.GET,
            queryParams = queryParams
        )

        when (httpResponse.status.value) {
            200 -> {
                return httpResponse.body<List<Int>>()
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    suspend fun fetchFeed(maxPostId: Int? = null, limit: Int? = null, seed: Int? = null): List<Int> {
        val queryParams = mutableMapOf<String, Any>()
        maxPostId?.let {
            queryParams["maxPostId"] = it
        }
        limit?.let {
            queryParams["limit"] = it
        }
        seed?.let {
            queryParams["seed"] = it
        }

        val httpResponse = genericRequest(
            endpoint = "feed",
            method = HttpMethod.GET,
            queryParams = queryParams
        )

        Log.d("fetchPost", httpResponse.body<String>())

        when (httpResponse.status.value) {
            200 -> {
                return httpResponse.body<List<Int>>()
            }
            else -> {
                throw APIException(httpResponse.body<RemoteError>().message)
            }
        }
    }

    // == Map Box ==
    suspend fun fetchAddress(lng: Double, lat: Double, accessToken: String): String? {
        val response: HttpResponse =
            client.get("https://api.mapbox.com/geocoding/v5/mapbox.places/$lng,$lat.json") {
                parameter("access_token", accessToken)
            }.body()

        when (response.status.value) {
            200 -> {
                val address = response.body<GeocodingResponse>().features.firstOrNull()?.place_name
                return address
            }
            else -> {
                throw APIException(response.body<RemoteError>().message)
            }
        }
    }
}