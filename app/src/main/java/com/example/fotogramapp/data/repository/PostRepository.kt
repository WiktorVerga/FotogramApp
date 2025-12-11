package com.example.fotogramapp.data.repository

import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.domain.model.Post
import com.mapbox.geojson.Point

class PostRepository(private val database: AppDatabase, private val settingsRepository: SettingsRepository) {
    val postDao = database.postDao()
    val userRepo = UserRepository(database, settingsRepository)


    private val allPosts = listOf<Post>(
        Post(
            id = 1,
            authorId = 1,
            message = "Hello World!",
            image = "dousahyudhas",
            location = Point.fromLngLat(10.0, 49.0)
        ),
        Post(
            id = 2,
            authorId = 1,
            message = "I'm not only hellowing the world, I'm in it hehe",
            image = "dousahyudhas",
            location = Point.fromLngLat(10.0, 49.0)
        ),
        Post(
            id = 3,
            authorId = 1,
            message = "The third post I'm now famous",
            image = "dousahyudhas",
            location = Point.fromLngLat(10.0, 49.0)
        ),
        Post(
            id = 4,
            authorId = 2,
            message = "Do you not follow me yet?",
            image = "dousahyudhas",
            location = null
        )
    )

    // == Get Posts ==
    suspend fun getPost(id: Int): Post? {
        val cachedPost = postDao.getPostById(id)

        if (cachedPost != null) {
            return cachedPost
        } else {
            //TODO: chiamata di rete per prendere il post
            val remotePost = allPosts.find { it.id == id }

            if (remotePost != null) {
                //Riaggiorno la Cache
                cachePost(remotePost)

                return remotePost
            }

            return null
        }
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
        postDao.clear(post.id)
        postDao.insertPost(post)
    }

    suspend fun getUserPosts(userId: Int): List<Post> {
        //TODO: fai chiamata di rete per prendere gli id dei post dell'utente
        // risultato della chiamata di rete: lista di id
        val remotePosts = userRepo.getUser(userId)?.postIds //Momentaneamente simula la chiamata

        if (remotePosts == null) {
            return listOf()
        }

        return getPosts(remotePosts)
    }

}