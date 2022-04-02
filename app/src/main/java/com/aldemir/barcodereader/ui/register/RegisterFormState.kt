package com.aldemir.barcodereader.ui.register

data class RegisterFormState(
    val barcodeError: Int? = null,
    val quantityError: Int? = null,
    val isBarcodeValid: Boolean = false,
    val isQuantityValid: Boolean = false
)