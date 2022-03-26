package com.omarahmed.shoppinglist.features.feature_list.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.util.getFileName
import com.omarahmed.shoppinglist.features.feature_list.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Constants
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_list.data.remote.request.AddItemRequest
import com.omarahmed.shoppinglist.features.feature_list.data.remote.request.UpdateItemRequest
import com.omarahmed.shoppinglist.features.feature_list.data.paging.ItemsSource
import com.omarahmed.shoppinglist.features.feature_list.domain.repository.ShoppingListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ShoppingLisRepoImpl @Inject constructor(
    private val api: ShoppingListApi,
    private val gson: Gson,
    private val appContext: Context,
    private val dataStoreManager: DataStoreManager
) : ShoppingListRepo {

    override val allItems: Flow<PagingData<ShoppingItem>>
        get() = Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true,
            )
        ) {
            ItemsSource(api,dataStoreManager)
        }.flow

    override suspend fun addNewItem(
        itemName: String,
        itemIconUrl: String
    ): Resource<Unit> {
        val request = AddItemRequest(itemName,itemIconUrl)
//        val file  = withContext(Dispatchers.IO){
//            appContext.contentResolver.openFileDescriptor(imageUri,"r")?.let { fd ->
//                val inputStream = FileInputStream(fd.fileDescriptor)
//                val file = File(
//                    appContext.cacheDir,
//                    appContext.contentResolver.getFileName(imageUri)
//                )
//                val outputStream = FileOutputStream(file)
//                inputStream.copyTo(outputStream)
//                file
//            }
//        } ?: return Resource.Error(appContext.getString(R.string.file_couldnt_found))

        return try {
            val response = api.addNewItem(
                token = "Bearer ${dataStoreManager.getToken.first()}",
                request = request
            )
            if (response.success){
                Resource.Success(response.message)
            } else {
                Resource.Error(response.message)
            }
        } catch (e:IOException){
            Resource.Error(appContext.getString(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(appContext.getString(R.string.something_went_wrong))
        }
    }

    override suspend fun updateItem(
        itemId: String,
        isAddedToCart: Boolean
    ): Resource<ShoppingItem> {
        val request = UpdateItemRequest(isAddedToCart)
        return try {
            val response = api.updateItem(itemId = itemId, request = request)
            if (response.success){
                Resource.Success(response.message,response.data)
            } else {
                Resource.Error(response.message)
            }

        } catch (e:IOException){
            Resource.Error(appContext.getString(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(appContext.getString(R.string.something_went_wrong))
        }
    }
}