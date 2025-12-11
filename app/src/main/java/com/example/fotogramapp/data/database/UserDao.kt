package com.example.fotogramapp.data.database

import androidx.room.*
import com.example.fotogramapp.domain.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun clear(id: Int)
}