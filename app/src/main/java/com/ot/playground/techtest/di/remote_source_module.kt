package com.ot.playground.techtest.di

import com.ot.playground.techtest.BuildConfig
import com.ot.playground.techtest.model.GithubService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HEADER_API_KEY = "Authorization"
const val server_url = "https://api.github.com/"

val remoteDatasourceModule = module {
    single { createOkHttpClient() }
    single { createWebService<GithubService>(get(), server_url) }
}


fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(getAuthInterceptor())
        .build()
}

private fun getAuthInterceptor(): (Interceptor.Chain) -> Response {

    return { chain ->
        val original = chain.request()
        val headerBuilder = original.headers.newBuilder()
        headerBuilder.add(HEADER_API_KEY, BuildConfig.ACCESS_TOKEN)
        chain.proceed(
            original.newBuilder()
                .headers(headerBuilder.build()).build()
        )
    }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}