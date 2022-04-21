package com.aldemir.barcodereader.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
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

    fun hideLoading(loading: ProgressBar) {
        loading.visibility = View.GONE
    }

    fun showLoading(loading: ProgressBar) {
        loading.visibility = View.VISIBLE
    }

    fun disableButton(button: MaterialButton) {
        button.isEnabled = false
    }

    fun enableButton(button: MaterialButton) {
        button.isEnabled = true
    }

    fun showMessageToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

