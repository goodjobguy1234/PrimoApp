package com.example.primohomepage.data.extension

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T> T.toJson(moshi: Moshi): String {
    return try {
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        adapter.toJson(this)
    } catch (e: Exception) {
        "" // Return empty string on failure
    }
}

inline fun <reified T> List<T>.toJson(moshi: Moshi): String {
    return try {
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
        adapter.toJson(this)
    } catch (e: Exception) {
        "[]" // Return empty array string on failure to prevent crash
    }
}

inline fun <reified T> String.toObject(moshi: Moshi): T? {
    return try {
        moshi.adapter(T::class.java).fromJson(this)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> String.toListObject(moshi: Moshi): List<T>? {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(type)
    return try {
        adapter.fromJson(this)
    } catch (e: Exception) {
        null
    }
}