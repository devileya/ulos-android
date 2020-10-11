package com.devileya.ulos.domain.module

import com.devileya.ulos.domain.network.ApiInteractor
import com.devileya.ulos.domain.repository.UlosRepository
import com.devileya.ulos.domain.repository.UlosRepositoryImpl
import com.devileya.ulos.view_model.UlosViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
val appModules = module {

    factory {
        createWebService<ApiInteractor>(
            okHttpClient = createHttpClient(),
            factory = CoroutineCallAdapterFactory(),
            baseUrl = "https://ulosapi.herokuapp.com/"
        )
    }

    // Ulos
    factory<UlosRepository> { UlosRepositoryImpl(get()) }
    viewModel { UlosViewModel(get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(2 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}