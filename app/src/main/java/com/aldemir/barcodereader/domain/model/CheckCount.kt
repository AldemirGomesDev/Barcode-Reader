package com.aldemir.barcodereader.domain.model

import com.aldemir.barcodereader.ui.model.CheckCountUiModel

data class CheckCount(
    var status: Boolean = false
)

fun CheckCount.toCheckCountUiModel() = CheckCountUiModel(
    status = this.status
)
