package com.example.fotogramapp.data.repository

import com.example.fotogramapp.domain.model.Post
import com.mapbox.geojson.Point

class PostRepository {
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
    fun getPost(id: Int): Post {
        return allPosts.first { it.id == id }
    }

    fun getPosts(ids: List<Int>): List<Post> {
        val posts = ids.map {
            getPost(it)
        }

        return posts
    }

    fun getPosts(ids: IntRange): List<Post> {
        val posts = ids.map {
            getPost(it)
        }

        return posts
    }

    fun getUserPosts(userId: Int): List<Post> {
        return allPosts.filter { it.authorId == userId }
    }
}