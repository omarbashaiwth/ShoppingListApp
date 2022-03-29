package com.omarahmed.shoppinglist.features.feature_icon_search.data.repository

import android.content.Context
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.IconApi
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.Icon
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.IconResponse
import com.omarahmed.shoppinglist.features.feature_icon_search.domain.repository.IconRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class IconRepositoryImpl @Inject constructor(
    private val api: IconApi,
    private val context: Context
): IconRepository {
    override suspend fun getIcons(query: String): Resource<IconResponse> {
        return try {
            val response = api.searchIcon(query)
            Resource.Success(null,response)
        } catch (e: IOException){
            Resource.Error(context.getString(R.string.error_couldnt_reach_server))
        } catch (e:HttpException){
            Resource.Error(context.getString(R.string.something_went_wrong))
        }
    }
}