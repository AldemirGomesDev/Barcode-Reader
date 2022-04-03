package com.aldemir.barcodereader.api

import com.aldemir.barcodereader.api.models.*
import com.aldemir.barcodereader.api.util.BaseResponse
import com.aldemir.barcodereader.data.Resource
import retrofit2.Response

interface ApiHelper {
    suspend fun login(requestLogin: RequestLogin): Response<ResponseLogin>
    suspend fun checkCount(): Response<ResponseCheckCount>
    suspend fun getProducts(
        requestProduct: RequestProduct
    ): Response<ResponseProduct>
    suspend fun register(
        requestRegister: RequestRegister
    ): Response<ResponseRegister>
}