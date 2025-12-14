package com.example.fotogramapp.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.domain.model.User

@Database(entities = [User::class, Post::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}