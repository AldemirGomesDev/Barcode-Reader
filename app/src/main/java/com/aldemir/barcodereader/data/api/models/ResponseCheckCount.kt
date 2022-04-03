package com.aldemir.barcodereader.data.api.models

import com.aldemir.barcodereader.domain.model.CheckCount

data class ResponseCheckCount(
    var status: Boolean = false,
    var message: String = "",
    var statusCode: Int = 0
)

fun ResponseCheckCount.toCheckCount() = CheckCount(
    status = this.status
)
