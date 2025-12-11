package com.example.fotogramapp.data.database

import androidx.room.*
import com.example.fotogramapp.domain.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM Post WHERE id = :id")
    suspend fun getPostById(id: Int): Post?

    @Query("SELECT * FROM Post WHERE id IN (:ids)")
    suspend fun getPostsByIds(ids: List<Long>): List<Post>?

    @Query("DELETE FROM Post WHERE id = :id")
    suspend fun clear(id: Int)
}