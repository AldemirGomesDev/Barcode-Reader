package com.aldemir.barcodereader.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.data.Resource
import com.aldemir.barcodereader.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "featureRegister"
    }

    private var isBarcodeValid = false
    private var isQuantityValid = false

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerForm: LiveData<RegisterFormState> = _registerForm

    private val _buttonIsEnabled = MutableLiveData<Boolean>()
    val buttonIsEnabled: LiveData<Boolean> = _buttonIsEnabled

    private val _news = MutableLiveData<Resource<String>>((Resource.loading(null)))
    var news: LiveData<Resource<String>> = _news

    fun register() {
        viewModelScope.launch {
            LogUtils.debug(tag = TAG, message = "Registrado com Sucesso!")
            LogUtils.info(tag = TAG, message = "Registrado com Sucesso!")
            LogUtils.warning(tag = TAG, message = "Registrado com Sucesso!")
            delay(3000)
            _news.postValue(Resource.success("Sucesso!"))
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
        return barcode.length > 5
    }

    private fun isQuantityValid(quantity: String): Boolean {
        return quantity.isNotEmpty()
    }

    private fun buttonIsEnabled(): Boolean {
        return isBarcodeValid && isQuantityValid
    }

}