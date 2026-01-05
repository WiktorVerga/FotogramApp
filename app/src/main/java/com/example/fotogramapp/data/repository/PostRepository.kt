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
    private val settingsRepository: SettingsRepository,
    private val remoteDataSource: RemoteDataSource
) {
    private val postDao = database.postDao()

    // == Get Posts ==
    suspend fun getFeed(maxPostId: Int?, limit: Int?, seed: Int?): List<Int> {
        return remoteDataSource.fetchFeed(
            maxPostId = maxPostId,
            limit = limit,
            seed = seed
        )
    }

    suspend fun getPost(postId: Int): Post {
        //Fetch from Cache
        val cachedPost = postDao.getPostById(postId)

        if (cachedPost != null) {
            return cachedPost
        }

        //Fetch from Network
        val remotePost = remoteDataSource.fetchPost(postId)

        //Update Cache
        postDao.insertPost(remotePost)

        return remotePost
    }

    suspend fun getAuthorPosts(authorId: Int, maxPostId: Int? = null, limit: Int? = null): List<Int> {
        return remoteDataSource.fetchAuthorPosts(authorId, maxPostId, limit)
    }

    suspend fun getOfflineAuthorPosts(authorId: Int, maxPostId: Int? = null, limit: Int = 10): List<Int> {
        return postDao.getOfflineAuthorPosts(authorId, maxPostId)
    }

    suspend fun getMappedLoadedPosts(): List<Int> {
        return postDao.getMappedLoadedPosts()
    }

    // == Add Post ==

    suspend fun addPost(message: String, image: String, location: Point? = null) {
        val post = remoteDataSource.createPost(message, image, location)

        //Save in Cache
        postDao.insertPost(post)
    }

    // == Delete Post ==
    suspend fun deletePost(postId: Int) {
        remoteDataSource.deletePost(postId)
        postDao.deletePostById(postId)
    }
}