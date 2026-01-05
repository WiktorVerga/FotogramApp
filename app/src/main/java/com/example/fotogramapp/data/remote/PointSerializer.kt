package com.example.fotogramapp.data.remote

import com.mapbox.geojson.Point
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

// == Converter for Remote Calls ==
object PointSerializer : KSerializer<Point?> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Point") {
        element<String>("type")
        element<Double?>("longitude")
        element<Double?>("latitude")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Point?) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw IllegalStateException("This serializer can be used only with Json")

        if (value == null) {
            jsonEncoder.encodeNull()
            return
        }

        jsonEncoder.encodeJsonElement(buildJsonObject {
            put("type", "Point")
            put("longitude", value.longitude())
            put("latitude", value.latitude())
        })
    }

    override fun deserialize(decoder: Decoder): Point? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer can be used only with Json")

        val jsonElement = jsonDecoder.decodeJsonElement()

        if (jsonElement is JsonNull) {
            return null
        }

        val jsonObj = jsonElement.jsonObject
        val longitude = jsonObj["longitude"]?.jsonPrimitive?.doubleOrNull
        val latitude = jsonObj["latitude"]?.jsonPrimitive?.doubleOrNull

        return if (longitude != null && latitude != null) {
            Point.fromLngLat(longitude, latitude)
        } else {
            null
        }
    }
}
