package com.aldemir.barcodereader.ui.home


import android.os.Bundle
import androidx.activity.viewModels
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityMainBinding
import com.aldemir.barcodereader.extensions.openActivity
import com.aldemir.barcodereader.ui.BaseActivity
import com.aldemir.barcodereader.ui.login.LoginActivity
import com.aldemir.barcodereader.ui.register.RegisterActivity
import com.aldemir.barcodereader.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val TAG = "return_scan_barcode"
    }
    private val mainViewModel: MainViewModel by viewModels()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listeners()

        observers()
    }

    private fun listeners() {

        binding.btnStartCounting.setOnClickListener {
            showLoading(binding.loading)
            disableButton(binding.btnStartCounting)
            mainViewModel.checkCount()
        }
        binding.btnExit.setOnClickListener {
            openActivity(LoginActivity::class.java)
            finish()
        }
    }

    private fun observers() {
        mainViewModel.checkCount.observe(this) { checkCountUiModel ->
            if (checkCountUiModel.status) {
                LogUtils.info(TAG, "Call register activity")
                openActivity(RegisterActivity::class.java)
            } else {
                showMessageToast(message = getString(R.string.count_not_allowed))
            }
            hideLoading(binding.loading)
            enableButton(binding.btnStartCounting)
        }
    }

}