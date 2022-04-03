package com.aldemir.barcodereader.data.api

import com.aldemir.barcodereader.data.api.models.*
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