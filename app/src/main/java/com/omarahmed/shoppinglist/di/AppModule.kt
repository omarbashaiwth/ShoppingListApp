package com.omarahmed.shoppinglist.di

import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Constants.BASE_URL
import com.omarahmed.shoppinglist.feature_list.data.repository.ShoppingLisRepoImpl
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import com.omarahmed.shoppinglist.feature_search.data.repository.SearchRepoImpl
import com.omarahmed.shoppinglist.feature_search.domain.repository.SearchRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingListApi(): ShoppingListApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ShoppingListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: ShoppingListApi): ShoppingListRepo {
        return ShoppingLisRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(api: ShoppingListApi): SearchRepo {
        return SearchRepoImpl(api)
    }
}