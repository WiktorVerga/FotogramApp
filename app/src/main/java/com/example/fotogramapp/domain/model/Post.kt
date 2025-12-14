package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mapbox.geojson.Point

@Entity
class Post(
    @PrimaryKey val id: Int,
    val authorId: Int,
    val message: String,
    val image: String,
    val location: Point? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)