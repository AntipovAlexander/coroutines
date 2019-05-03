package com.antipov.coroutines.idp.data.retrofit

import com.antipov.coroutines.idp.BuildConfig
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.parser.StockPriceParser
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private const val BASE_URL = "https://www.quandl.com/api/v3/datasets/"

    fun makeRetrofitService(): Service {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(makeGsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(Service::class.java)
    }

    private fun makeGsonBuilder(): GsonBuilder {
        return GsonBuilder().apply {
            registerTypeAdapter(StockPrice::class.java, StockPriceParser())
        }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(provideLoggingInterceptor())
        return builder.build()
    }

    private fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
}