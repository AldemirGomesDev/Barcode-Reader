package com.aldemir.barcodereader.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.data.Resource
import com.aldemir.barcodereader.data.api.models.RequestProduct
import com.aldemir.barcodereader.data.api.models.RequestRegister
import com.aldemir.barcodereader.domain.model.toProductUiModel
import com.aldemir.barcodereader.domain.model.toRegisterUiModel
import com.aldemir.barcodereader.domain.usecase.ProductUseCase
import com.aldemir.barcodereader.domain.usecase.RegisterUseCase
import com.aldemir.barcodereader.ui.model.ProductUiModel
import com.aldemir.barcodereader.ui.model.RegisterUiModel
import com.aldemir.barcodereader.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "featureRegister"
    }

    private var isBarcodeValid = false
    private var isQuantityValid = false

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerForm: LiveData<RegisterFormState> = _registerForm

    private val _buttonIsEnabled = MutableLiveData<Boolean>()
    val buttonIsEnabled: LiveData<Boolean> = _buttonIsEnabled

    private val _register = MutableLiveData<RegisterUiModel>()
    val register: LiveData<RegisterUiModel> = _register

    private val _product = MutableLiveData<ProductUiModel>()
    var product: LiveData<ProductUiModel> = _product

    fun register(requestRegister: RequestRegister) {
        viewModelScope.launch {
            val result = registerUseCase(requestRegister = requestRegister)
            _register.postValue(result.toRegisterUiModel())
        }
    }

    fun getProduct(requestProduct: RequestProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = productUseCase(requestProduct = requestProduct)
            LogUtils.debug(tag = TAG, message = "result: ${result.toProductUiModel()}")
            _product.postValue(result.toProductUiModel())
        }
    }

    fun barcodeDataChanged(barcode: String) {
        if (!isBarcodeValid(barcode)) {
            isBarcodeValid = false
            _registerForm.value = RegisterFormState(barcodeError  = R.string.invalid_barcode)
        } else {
            isBarcodeValid = true
            _registerForm.value = RegisterFormState(isBarcodeValid = true)
        }
        _buttonIsEnabled.postValue(buttonIsEnabled())
    }

    fun quantityDataChanged(quantity: String) {
        if (!isQuantityValid(quantity)) {
            isQuantityValid = false
            _registerForm.value = RegisterFormState(quantityError  = R.string.invalid_input)
        } else {
            isQuantityValid = true
            _registerForm.value = RegisterFormState(isQuantityValid = true)
        }
        _buttonIsEnabled.postValue(buttonIsEnabled())
    }

    private fun isBarcodeValid(barcode: String): Boolean {
        return barcode.length > 4
    }

    private fun isQuantityValid(quantity: String): Boolean {
        return quantity.isNotEmpty()
    }

    private fun buttonIsEnabled(): Boolean {
        return isBarcodeValid && isQuantityValid
    }

}