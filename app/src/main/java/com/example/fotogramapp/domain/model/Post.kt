package com.example.fotogramapp.domain.model

import com.mapbox.geojson.Point

class Post(
    val id: Int,
    val authorId: Int,
    val message: String,
    val image: String,
    val location: Point?
)