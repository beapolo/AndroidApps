package com.example.showtracker.data.remote.network
import com.example.showtracker.BuildConfig
import com.example.showtracker.data.common.Constants
import com.example.showtracker.data.remote.api.ActorsAPI
import com.example.showtracker.data.remote.api.EpisodesAPI
import com.example.showtracker.data.remote.api.TVShowsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object Retrofit {
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideShowsAPI(retrofit: Retrofit): TVShowsAPI {
        return retrofit.create(TVShowsAPI::class.java)
    }

    @Provides
    fun provideActorsAPI(retrofit: Retrofit): ActorsAPI {
        return retrofit.create(ActorsAPI::class.java)
    }

    @Provides
    fun provideEpisodesAPI(retrofit: Retrofit): EpisodesAPI {
        return retrofit.create(EpisodesAPI::class.java)
    }
}