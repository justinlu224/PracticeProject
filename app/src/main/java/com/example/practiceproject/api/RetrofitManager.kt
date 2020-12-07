package com.example.practiceproject.api

import com.example.practiceproject.PracticeApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private val httpClient: OkHttpClient by lazy {
        //setup cache
        val httpCacheDirectory = File(PracticeApplication.instance.cacheDir, "responses");
        val cacheSize: Long = 10 * 1024 * 1024; // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize);
        OkHttpClient.Builder()
            .readTimeout(7676, TimeUnit.MILLISECONDS)
            .connectTimeout(7676, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .cache(cache)
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(PracticeApplication.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient)
        .build()

    val apiService: Apiservice by lazy { retrofit.create(Apiservice::class.java) }
}