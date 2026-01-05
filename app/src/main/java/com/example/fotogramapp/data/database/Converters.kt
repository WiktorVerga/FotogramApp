package com.example.fotogramapp.data.database
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Point

// == Converters for Room Database ==
class Converters {
    private val gson = Gson()

    // Converter per List<Int>
    @TypeConverter
    fun fromStringList(value: String?): List<Int>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<Int>?): String? {
        if (list == null) {
            return null
        }
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToPoint(value: String?): Point? {
        if (value == null) {
            return null
        }
        // Dividiamo la stringa "longitudine,latitudine"
        val coords = value.split(',').map { it.toDouble() }
        if (coords.size != 2) {
            return null // O gestisci l'errore come preferisci
        }
        // Point.fromLngLat() accetta prima la longitudine, poi la latitudine
        return Point.fromLngLat(coords[0], coords[1])
    }

    @TypeConverter
    fun fromPointToString(point: Point?): String? {
        if (point == null) {
            return null
        }
        // Creiamo una stringa formattata "longitudine,latitudine"
        return "${point.longitude()},${point.latitude()}"
    }
}
