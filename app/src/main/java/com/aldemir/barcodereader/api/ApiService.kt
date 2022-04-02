package com.aldemir.barcodereader.api

import com.aldemir.barcodereader.api.models.*
import com.aldemir.barcodereader.data.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("v1/client/auth/login")
    suspend fun login(
        @Body requestLogin: RequestLogin
    ): Response<Resource<ResponseLogin>>

    @GET("v1/client/checkcount")
    suspend fun checkCount(): Response<Resource<ResponseCheckCount>>

    @GET("v1/client/products")
    suspend fun getProducts(
        @Body requestProduct: RequestProduct
    ): Response<Resource<ResponseProduct>>

    @POST("v1/client/register")
    suspend fun register(
        @Body requestRegister: RequestRegister
    ): Response<Resource<ResponseRegister>>
}