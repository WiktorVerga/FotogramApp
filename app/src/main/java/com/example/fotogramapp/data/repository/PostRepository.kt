package com.example.fotogramapp.data.repository

import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.domain.model.Post
import com.example.testing_apis.model.RemoteDataSource
import com.mapbox.geojson.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostRepository(
    private val database: AppDatabase,
    private val settingsRepository: SettingsRepository
) {
    private val postDao = database.postDao()
    var remoteDataSource = RemoteDataSource()

    init {
        //Se già fatto l'accesso, Fornisco il sessionId al remoteDataSource
        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.getSessionId()?.let { remoteDataSource.provideSessionId(it) }
        }
    }

    // == Get Posts ==
    suspend fun getFeed(maxPostId: Int?, limit: Int?, seed: Int?): List<Int> {
        return remoteDataSource.fetchFeed(
            maxPostId = maxPostId,
            limit = limit,
            seed = seed
        )
    }

    suspend fun getPost(postId: Int): Post {
        //Prendo da Cache
        val cachedPost = postDao.getPostById(postId)

        if (cachedPost != null) {
            return cachedPost
        }

        //Prendo da rete
        val remotePost = remoteDataSource.fetchPost(postId)

        //Aggiorno cache
        postDao.insertPost(remotePost)

        return remotePost
    }

    suspend fun getAuthorPosts(authorId: Int, maxPostId: Int? = null, limit: Int? = null) =
        remoteDataSource.fetchAuthorPosts(authorId, maxPostId, limit)

    suspend fun addPost(message: String, image: String, location: Point? = null) {
        val post = remoteDataSource.createPost(message, image, location)

        //Salvo nella Cache
        postDao.insertPost(post)
    }
}