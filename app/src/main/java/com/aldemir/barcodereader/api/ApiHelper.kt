package com.aldemir.barcodereader.api

import com.aldemir.barcodereader.api.models.*
import com.aldemir.barcodereader.data.Resource
import retrofit2.Response

interface ApiHelper {
    suspend fun login(requestLogin: RequestLogin): Response<Resource<ResponseLogin>>
    suspend fun checkCount(): Response<Resource<ResponseCheckCount>>
    suspend fun getProducts(
        requestProduct: RequestProduct
    ): Response<Resource<ResponseProduct>>
    suspend fun register(
        requestRegister: RequestRegister
    ): Response<Resource<ResponseRegister>>
}