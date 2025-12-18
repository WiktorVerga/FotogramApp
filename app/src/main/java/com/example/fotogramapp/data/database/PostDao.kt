package com.example.fotogramapp.data.database

import androidx.room.*
import com.example.fotogramapp.domain.model.Post

@Dao
interface PostDao {
    @Upsert
    suspend fun insertPost(post: Post)

    @Upsert
    suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM Post WHERE id = :id")
    suspend fun getPostById(id: Int): Post?

    @Query("SELECT * FROM Post WHERE id IN (:ids)")
    suspend fun getPostsByIds(ids: List<Int>): List<Post?>

    @Query("SELECT id FROM Post WHERE authorId = :authorId")
    suspend fun getPostsByAuthor(authorId: Int): List<Int>

    @Query("DELETE FROM Post WHERE id = :id")
    suspend fun clear(id: Int)

    @Query("DELETE FROM Post")
    suspend fun clearAll()
}