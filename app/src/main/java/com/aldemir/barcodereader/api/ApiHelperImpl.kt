package com.aldemir.barcodereader.api

import com.aldemir.barcodereader.api.models.*
import com.aldemir.barcodereader.api.util.BaseResponse
import com.aldemir.barcodereader.data.Resource
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(requestLogin: RequestLogin): Response<ResponseLogin>{
        return apiService.login(requestLogin = requestLogin)
    }

    override suspend fun checkCount(): Response<ResponseCheckCount> {
        return apiService.checkCount()
    }

    override suspend fun getProducts(requestProduct: RequestProduct): Response<ResponseProduct> {
        return apiService.getProducts(requestProduct = requestProduct)
    }

    override suspend fun register(requestRegister: RequestRegister): Response<ResponseRegister> {
        return apiService.register(requestRegister = requestRegister)
    }

}