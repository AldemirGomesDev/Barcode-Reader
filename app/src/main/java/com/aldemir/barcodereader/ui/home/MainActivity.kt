package com.aldemir.barcodereader.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityMainBinding
import com.aldemir.barcodereader.ui.login.LoginActivity
import com.aldemir.barcodereader.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "return_scan_barcode"
    }
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        listeners()

        observers()
    }

    private fun listeners() {
        binding.btnStartCounting.setOnClickListener {
            showLoading()
            mainViewModel.checkCount()
        }
        binding.btnExit.setOnClickListener {
            startLoginActivity()
        }
    }

    private fun observers() {
        mainViewModel.checkCount.observe(this) { checkCountUiModel ->
            if (checkCountUiModel.status) {
                startRegisterActivity()
            } else {
                Toast.makeText(this, getString(R.string.count_not_allowed), Toast.LENGTH_SHORT).show()
            }
            hideLoading()
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

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.btnStartCounting.isEnabled = true
    }
    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.btnStartCounting.isEnabled = false
    }
}