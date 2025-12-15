package com.example.fotogramapp.data.repository

import android.graphics.Bitmap
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.utils.toBase64
import com.example.fotogramapp.domain.model.Post
import com.mapbox.geojson.Point

class PostRepository(private val database: AppDatabase, private val settingsRepository: SettingsRepository) {
    val postDao = database.postDao()
    val userRepo = UserRepository(database, settingsRepository)

    // == Get Posts ==
    suspend fun getPost(id: Int): Post? {
        val cachedPost = postDao.getPostById(id)

        if (cachedPost != null) {
            return cachedPost
        } else {
            //TODO: chiamata di rete per prendere il post
            val remotePost = null

            if (remotePost != null) {
                //Riaggiorno la Cache
                cachePost(remotePost)

                return remotePost
            }

            return null
        }
    }

    suspend fun addPost(message: String, image: String, location: String?) {
        //TODO: richiesta di rete post, ricevo anche dati per id, authorId
        TODO()

    }

    suspend fun getPosts(ids: List<Int>): List<Post> {
        val posts = ids.map {
            getPost(it)
        }

        //Se non trovo qualche post lo elimino dalla lista
        return posts.filterNotNull()
    }

    suspend fun getPosts(ids: IntRange): List<Post> {
        val posts = ids.map {
            getPost(it)
        }

        return posts.filterNotNull()
    }

    suspend fun cachePost(post: Post) {
        TODO()
    }

    suspend fun getUserPosts(userId: Int): List<Post> {
        TODO()
    }

}