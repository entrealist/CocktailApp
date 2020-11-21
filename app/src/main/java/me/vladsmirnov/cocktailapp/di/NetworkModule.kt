package me.vladsmirnov.cocktailapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.vladsmirnov.cocktailapp.BuildConfig
import me.vladsmirnov.cocktailapp.data.network.CocktailNetworkService
import me.vladsmirnov.cocktailapp.data.network.Endpoint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 40L

val networkModule = module {
    single { headerInterceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    single { createCocktailAppService(get()) }
}

fun createCocktailAppService(endpoint: Endpoint): CocktailNetworkService =
    CocktailNetworkService(endpoint)

fun apiService(retrofit: Retrofit): Endpoint =
    retrofit.create(Endpoint::class.java)

fun retrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(
                    KotlinJsonAdapterFactory()
                ).build()
            )
        )
        .build()


fun okhttpClient(
    headerInterceptor: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor).addInterceptor(loggingInterceptor())
        .build()

fun headerInterceptor(): Interceptor =
    Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Content-type", "application/json")
            .header("Accept", "application/json")
            .header("Version", "1")
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    when (BuildConfig.DEBUG) {
        true -> this.level = HttpLoggingInterceptor.Level.BODY
        false -> this.level = HttpLoggingInterceptor.Level.NONE
    }
}
