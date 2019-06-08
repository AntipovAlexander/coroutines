package com.antipov.coroutines.idp.di

import android.app.Application
import com.antipov.coroutines.idp.BuildConfig
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.parser.StockPriceParser
import com.antipov.coroutines.idp.data.retrofit.ApiHelper
import com.antipov.coroutines.idp.data.retrofit.Service
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    private val BASE_URL = "https://www.quandl.com/api/v3/datasets/"

    @Provides
    @Singleton
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder().apply {
            registerTypeAdapter(StockPrice::class.java, StockPriceParser())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @LoggingInterceptor apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonBuilder: GsonBuilder, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    @Provides
    fun provideApiHelper(apiService: Service): ApiHelper {
        return ApiHelper(apiService)
    }

    @Provides
    @Singleton
    fun provideStockDbHelper(application: Application) = StockPriceDbHelper.getInstance(application.applicationContext)
}