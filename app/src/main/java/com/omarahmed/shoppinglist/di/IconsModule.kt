package com.omarahmed.shoppinglist.di

import android.content.Context
import com.omarahmed.shoppinglist.core.util.Constants.ICONS_API_KEY
import com.omarahmed.shoppinglist.core.util.Constants.ICONS_BASE_URL
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.IconApi
import com.omarahmed.shoppinglist.features.feature_icon_search.data.repository.IconRepositoryImpl
import com.omarahmed.shoppinglist.features.feature_icon_search.domain.repository.IconRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IconsModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor{
                val modifiedRequest =  it.request().newBuilder()
                    .addHeader("Authorization","Bearer $ICONS_API_KEY")
                    .addHeader("Accept", "application/json")
                    .build()
                it.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideIconSearchApi(client: OkHttpClient): IconApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(ICONS_BASE_URL)
            .build()
            .create(IconApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIconRepository(
        iconApi: IconApi,
        @ApplicationContext context: Context
    ): IconRepository {
        return IconRepositoryImpl(iconApi,context)
    }


}