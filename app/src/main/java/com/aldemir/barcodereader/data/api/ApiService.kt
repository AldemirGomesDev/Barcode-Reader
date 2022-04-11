package com.aldemir.barcodereader.data.api

import com.aldemir.barcodereader.data.api.models.*
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

    @POST("products")
    suspend fun getProducts(
        @Body requestProduct: RequestProduct
    ): Response<ResponseProduct>

    @POST("register")
    suspend fun register(
        @Body requestRegister: RequestRegister
    ): Response<ResponseRegister>
}