package com.example.fotogramapp.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fotogramapp.data.remote.PointSerializer
import com.mapbox.geojson.Point
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
class Post(
    @SerialName("id") @PrimaryKey val id: Int,
    @SerialName("authorId") val authorId: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("contentText") val message: String,
    @SerialName("contentPicture") val image: String,
    @SerialName("location") @Serializable(with = PointSerializer::class) val location: Point? = null
)