package com.example.testing_apis.model

import android.util.Log
import androidx.core.net.toUri
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.remote.RemoteError
import com.example.fotogramapp.data.remote.UserLogged
import com.example.fotogramapp.domain.model.User
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
import kotlinx.serialization.json.Json
import kotlin.jvm.Throws

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

    suspend fun getUser(
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
}