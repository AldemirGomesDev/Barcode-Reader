package com.aldemir.barcodereader.ui.home


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityMainBinding
import com.aldemir.barcodereader.ui.BaseActivity
import com.aldemir.barcodereader.ui.login.LoginActivity
import com.aldemir.barcodereader.ui.register.RegisterActivity
import com.aldemir.barcodereader.util.LogUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : BaseActivity<ActivityMainBinding>() {

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
            showLoading(binding.loading, binding.btnStartCounting)
            mainViewModel.checkCount()
        }
        binding.btnExit.setOnClickListener {
            startLoginActivity()
        }
    }

    private fun observers() {
        mainViewModel.checkCount.observe(this) { checkCountUiModel ->
            if (checkCountUiModel.status) {
                LogUtils.info(TAG, "Call register activity")
                startRegisterActivity()
            } else {
                Toast.makeText(this, getString(R.string.count_not_allowed), Toast.LENGTH_SHORT).show()
            }
            hideLoading(binding.loading, binding.btnStartCounting)
        }
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

}