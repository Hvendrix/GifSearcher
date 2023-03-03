package com.example.gifsearcher.core.domain.converters

import com.example.gifsearcher.core.data.model.Gif
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type

class GifDeserializer : JsonDeserializer<GifJSONFactory> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GifJSONFactory =
        json?.asJsonObject?.get("data").let { data ->
            if (data != null && data.isJsonArray) {
                val responseType = object : TypeToken<ArrayList<Gif?>?>() {}.type
                val arrayList: ArrayList<Gif?>? = context?.deserialize(data, responseType)
                GifJSONFactory(gifArray = arrayList)

            } else {
                val responseType = object : TypeToken<Gif?>() {}.type
                val gif: Gif? = context?.deserialize(data, responseType)
                GifJSONFactory(gif = gif)
            }
        }
}