package com.example.newsapp.presentation.news_detail.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object FavoriteNewsSettingsSerializer : Serializer<FavoriteNewsSettings> {

    // `defaultValue` property'si, `FavoriteNewsSettings` nesnesinin varsayılan değerini sağlar.
    override val defaultValue: FavoriteNewsSettings =  FavoriteNewsSettings()

    // `readFrom` fonksiyonu, bir `InputStream`'den JSON verisini okuyarak `FavoriteNewsSettings` nesnesine dönüştürür.
    override suspend fun readFrom(input: InputStream): FavoriteNewsSettings {
        return try {
            // `Json.decodeFromString` kullanarak JSON verisini `FavoriteNewsSettings` nesnesine dönüştürür.
            Json.decodeFromString(
                FavoriteNewsSettings.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read FavoriteNewsSettings", e)
        }
    }

    // `writeTo` fonksiyonu, `FavoriteNewsSettings` nesnesini JSON formatında bir `OutputStream`'e yazar.
    override suspend fun writeTo(t: FavoriteNewsSettings, output: OutputStream) {
        // `Json.encodeToString` kullanarak `FavoriteNewsSettings` nesnesini JSON string'ine dönüştürür.
        output.write(Json.encodeToString(FavoriteNewsSettings.serializer(), t).encodeToByteArray())
    }
}