package com.aldemir.barcodereader.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldemir.barcodereader.domain.model.toCheckCountUiModel
import com.aldemir.barcodereader.domain.usecase.CheckCountUseCase
import com.aldemir.barcodereader.ui.model.CheckCountUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var checkCountUseCase: CheckCountUseCase
) : ViewModel()  {

    private val _checkCount = MutableLiveData<CheckCountUiModel>()
    val checkCount: LiveData<CheckCountUiModel> = _checkCount

    fun  checkCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val checkCountDomain = checkCountUseCase()
            _checkCount.postValue(checkCountDomain.toCheckCountUiModel())
        }
    }
}