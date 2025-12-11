package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mapbox.geojson.Point

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["authorId"])]
)
class Post(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val authorId: Int,
    val message: String,
    val image: String,
    val location: Point?,
    val lastUpdated: Long = System.currentTimeMillis()
)