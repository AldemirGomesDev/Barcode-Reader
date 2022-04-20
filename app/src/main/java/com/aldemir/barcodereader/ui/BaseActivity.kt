package com.aldemir.barcodereader.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.aldemir.barcodereader.util.LogUtils
import com.google.android.material.button.MaterialButton

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    fun hideLoading(loading: ProgressBar, btnStart: MaterialButton) {
        LogUtils.info(message = "hideLoading")
        loading.visibility = View.GONE
        btnStart.isEnabled = true
    }

    fun showLoading(loading: ProgressBar, btnStart: MaterialButton) {
        LogUtils.info(message = "showLoading")
        loading.visibility = View.VISIBLE
        btnStart.isEnabled = false
    }

    fun showMessageToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

