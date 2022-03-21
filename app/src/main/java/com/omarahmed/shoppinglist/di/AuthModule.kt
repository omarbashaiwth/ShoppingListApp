package com.omarahmed.shoppinglist.di

import android.content.Context
import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.util.Constants.BASE_URL
import com.omarahmed.shoppinglist.feature_auth.data.remote.AuthApi
import com.omarahmed.shoppinglist.feature_auth.data.repository.AuthRepositoryImpl
import com.omarahmed.shoppinglist.feature_auth.domain.repository.AuthRepository
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
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        datastoreManager: DataStoreManager,
        @ApplicationContext context: Context
    ): AuthRepository {
        return AuthRepositoryImpl(api,datastoreManager,context)
    }
}