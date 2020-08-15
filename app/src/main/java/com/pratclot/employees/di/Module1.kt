package com.pratclot.employees.di

import android.content.Context
import androidx.room.Room
import com.pratclot.employees.data.db.EmplDb
import com.pratclot.employees.data.db.EmplDbDao
import com.pratclot.employees.service.EmployeeApi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://gitlab.65apps.com/"

@Module
@InstallIn(ApplicationComponent::class)
class Module1 {
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(retrofit: Retrofit): EmployeeApi {
        return retrofit.create(EmployeeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(emplDb: EmplDb): EmplDbDao {
        return emplDb.emplDbDao
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): EmplDb {
        return Room.databaseBuilder(
            context,
            EmplDb::class.java,
            "employeedb"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePicasso(): Picasso {
        return Picasso.get()
    }
}