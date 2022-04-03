package com.aldemir.barcodereader.api

import com.aldemir.barcodereader.api.models.*
import com.aldemir.barcodereader.api.util.BaseResponse
import com.aldemir.barcodereader.data.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body requestLogin: RequestLogin
    ): Response<ResponseLogin>

    @GET("checkcount")
    suspend fun checkCount(): Response<ResponseCheckCount>

    @GET("products")
    suspend fun getProducts(
        @Body requestProduct: RequestProduct
    ): Response<ResponseProduct>

    @POST("register")
    suspend fun register(
        @Body requestRegister: RequestRegister
    ): Response<ResponseRegister>
}