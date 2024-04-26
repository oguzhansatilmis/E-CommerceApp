package com.android.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.android.ecommerceapp.db.CommerceDatabase
import com.android.ecommerceapp.db.ModuleDao
import com.android.ecommerceapp.service.ApiService
import com.android.ecommerceapp.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)



    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CommerceDatabase =
        Room.databaseBuilder(
            context,
            CommerceDatabase::class.java, "commerce_database"
        ).build()

    @Singleton
    @Provides
    fun provideDao(database: CommerceDatabase): ModuleDao = database.moduleDao()


}