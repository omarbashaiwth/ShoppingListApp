package com.omarahmed.shoppinglist.features.feature_auth.data.repository

import android.content.Context
import android.util.Log
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_auth.data.remote.AuthApi
import com.omarahmed.shoppinglist.features.feature_auth.data.remote.request.CreateUserRequest
import com.omarahmed.shoppinglist.features.feature_auth.data.remote.request.LoginUserRequest
import com.omarahmed.shoppinglist.features.feature_auth.data.remote.response.LoginResponse
import com.omarahmed.shoppinglist.features.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val dataStoreManager: DataStoreManager,
    private val context: Context
) : AuthRepository {

    override suspend fun createUser(name: String, email: String, password: String): Resource<Unit> {
        return try {
            val request = CreateUserRequest(name, email, password)
            val response = authApi.createUser(request)
            if (response.success) {
                Resource.Success(response.message)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: IOException) {
            Resource.Error(context.getString(R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(context.getString(R.string.something_went_wrong))
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<LoginResponse> {
        return try {
            val request = LoginUserRequest(email, password)
            val response = authApi.loginUser(request)
            if (response.success){
                response.data?.let { loginResponse ->
                    dataStoreManager.saveToken(loginResponse.token)
                    Log.d("TOKEN_ID", loginResponse.token)
                }
                Resource.Success(response.message, response.data)
            } else {
                Resource.Error(response.message)
            }
        } catch (e: IOException){
            Resource.Error(context.getString(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(context.getString(R.string.something_went_wrong))
        }
    }

    override suspend fun authenticate(token: String?): Resource<Unit> {
        return try {
            if (token != null){
                authApi.authenticate(token)
                Resource.Success(null,Unit)
            } else {
                Resource.Error(context.getString(R.string.not_log_in))
            }

        }catch (e:IOException){
            Resource.Error(context.getString(R.string.error_couldnt_reach_server))
        } catch (e: HttpException) {
            Resource.Error(context.getString(R.string.something_went_wrong))
        }
    }

    override suspend fun logout() {
        dataStoreManager.clearDataStore()
    }
}