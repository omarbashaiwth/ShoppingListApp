package com.omarahmed.shoppinglist.feature_list.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.presentation.util.getFileName
import com.omarahmed.shoppinglist.core.util.Constants
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.data.dto.request.AddItemRequest
import com.omarahmed.shoppinglist.feature_list.data.dto.request.UpdateItemRequest
import com.omarahmed.shoppinglist.feature_list.data.dto.response.SimpleResponse
import com.omarahmed.shoppinglist.feature_list.data.paging.ItemsSource
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val appContext: Context
) : ShoppingListRepo {

    override val allItems: Flow<PagingData<ShoppingItem>>
        get() = Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true,
            )
        ) {
            ItemsSource(api)
        }.flow

    override suspend fun addNewItem(
        itemName: String,
        imageUri: Uri
    ): Resource<Unit> {
        val request = AddItemRequest(itemName)
        val file  = withContext(Dispatchers.IO){
            appContext.contentResolver.openFileDescriptor(imageUri,"r")?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(imageUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        } ?: return Resource.Error("The file couldn't be found")

        return try {
            val response = api.addNewItem(
                postData = MultipartBody.Part
                    .createFormData(
                        "adding_item_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                       name = "post_image",
                       filename = file.name,
                       body = file.asRequestBody()
                    )
            )
            if (response.success){
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message)
            }
        } catch (e:IOException){
            Resource.Error("Oops! Couldn't reach the server. Check your Internet connection")
        } catch (e: HttpException){
            Resource.Error("Oops! Something went wrong. Please, try again")
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
                Resource.Success(response.data)
            } else {
                Resource.Error(response.message)
            }

        } catch (e:IOException){
            Resource.Error("Oops! Couldn't reach the server. Check your Internet connection")
        } catch (e: HttpException){
            Resource.Error("Oops! Something went wrong. Please, try again")
        }
    }
}