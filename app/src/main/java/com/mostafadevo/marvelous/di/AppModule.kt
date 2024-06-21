package com.mostafadevo.marvelous.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mostafadevo.marvelous.data.local.MarvelDao
import com.mostafadevo.marvelous.data.local.MarvelDatabase
import com.mostafadevo.marvelous.data.remote.AuthInterceptor
import com.mostafadevo.marvelous.data.remote.MarvelApi
import com.mostafadevo.marvelous.domain.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMarvelApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMarvelRepository(marvelApi: MarvelApi,marvelDao: MarvelDao): MarvelRepository {
        return MarvelRepository(marvelApi,marvelDao)
    }

    @Singleton
    @Provides
    fun provideMarvelDatabase(
        @ApplicationContext context: Context
    ): MarvelDatabase {
        return Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            "marvel_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMarvelDao(marvelDatabase: MarvelDatabase): MarvelDao {
        return marvelDatabase.marvelDao()
    }
}