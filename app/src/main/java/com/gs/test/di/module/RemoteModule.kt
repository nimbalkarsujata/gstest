package com.gs.test.di.module

import com.gs.test.BuildConfig
import com.gs.test.data.api.ApiService
import com.gs.test.data.api.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {
    companion object {
        private const val timeOut: Long = 15
    }
    @Provides
    @Singleton
    fun networkInterceptor(): NetworkInterceptor {
        return NetworkInterceptor()
    }
    @Singleton
    @Provides
    fun providesRetrofitInstance(okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkkHttpClientInstance(networkInterceptor: NetworkInterceptor,): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val certificatePin= CertificatePinner.Builder()
             .add(
                 "*.nasa.org",
                 "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0="
             ).build()

        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(networkInterceptor)
            connectTimeout(timeOut, TimeUnit.SECONDS)
          //  certificatePinner(certificatePin)
        }.build()
    }

    @Singleton
    @Provides
    fun providesTmDBServices(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}