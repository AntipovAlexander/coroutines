package com.antipov.coroutines.idp.utils.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject


fun JsonObject?.getDouble(key: String): Double? =
    try {
        if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asDouble
    } catch (e: NumberFormatException) {
        0.0
    }

fun JsonObject?.getString(key: String): String =
    if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) "" else this.get(key).asString

fun JsonObject?.getNullableString(key: String): String? =
    if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asString

fun JsonObject?.getJsonObject(key: String): JsonObject? =
    if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asJsonObject

fun JsonObject?.getJsonArray(key: String): JsonArray? =
    if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asJsonArray

fun JsonObject?.getInt(key: String): Int? =
    try {
        if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asInt
    } catch (e: NumberFormatException) {
        0
    }

fun JsonObject?.getFloat(key: String): Float? =
    try {
        if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asFloat
    } catch (e: NumberFormatException) {
        0f
    }

fun JsonObject?.getLong(key: String): Long? =
    try {
        if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) null else this.get(key).asLong
    } catch (e: NumberFormatException) {
        0L
    }

fun JsonObject?.getBoolean(key: String): Boolean =
    if (this == null || this.get(key) == null || this.get(key) == JsonNull.INSTANCE) false else this.get(key).asBoolean