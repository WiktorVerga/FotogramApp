package com.example.fotogramapp.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.domain.model.User

@Database(entities = [Post::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}