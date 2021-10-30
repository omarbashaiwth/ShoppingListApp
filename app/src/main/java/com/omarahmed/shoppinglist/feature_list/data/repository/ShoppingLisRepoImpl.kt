package com.omarahmed.shoppinglist.feature_list.data.repository

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShoppingLisRepoImpl @Inject constructor(
    private val api: ShoppingListApi
): ShoppingListRepo {

    override suspend fun getAllItems(): Resource<List<ShoppingItem>> {
        return try {
            val shoppingItem = api.getAllItems()
            Resource.Success(shoppingItem)

        } catch (e: IOException){
            Resource.Error("Couldn't reach the server. Try again later")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong")
        }
    }
}