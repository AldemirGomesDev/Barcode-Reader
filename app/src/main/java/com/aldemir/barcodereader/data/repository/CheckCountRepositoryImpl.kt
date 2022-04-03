package com.aldemir.barcodereader.data.repository

import com.aldemir.barcodereader.api.ApiHelper
import com.aldemir.barcodereader.api.models.toCheckCount
import com.aldemir.barcodereader.api.util.Output
import com.aldemir.barcodereader.api.util.parseResponse
import com.aldemir.barcodereader.domain.model.CheckCount
import com.aldemir.barcodereader.util.LogUtils
import javax.inject.Inject

class CheckCountRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
) : CheckCountRepository {
    override suspend fun checkCount(): CheckCount {
        return when (val result = apiHelper.checkCount().parseResponse()) {
            is Output.Success -> {
                LogUtils.debug(message = "response checkCount -> ${result.value}")
                result.value.toCheckCount()
            }
            is Output.Failure -> CheckCount(status = false)
        }
    }
}

interface CheckCountRepository {
    suspend fun checkCount() : CheckCount
}