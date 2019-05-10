package com.antipov.coroutines.idp.data.parser

import com.antipov.coroutines.idp.data.model.StockPrice
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StockPriceParser : JsonDeserializer<StockPrice> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): StockPrice {
        val dataArray = json.asJsonObject.getAsJsonObject("dataset_data").getAsJsonArray("data")
        dataArray.firstOrNull()?.let {
            with(it.asJsonArray) {
                val date = this[0].asString
                val open = this[1].asFloat
                val high = this[2].asFloat
                val low = this[3].asFloat
                val close = this[4].asFloat
                return StockPrice(date, StockPrice.Data(open, high, low, close))
            }
        } ?: kotlin.run {
            return StockPrice("", StockPrice.Data(0.0f, 0.0f, 0.0f, 0.0f))
        }
    }

}