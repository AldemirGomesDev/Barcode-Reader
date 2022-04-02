package com.aldemir.barcodereader.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.aldemir.barcodereader.databinding.ActivityRegisterBinding
import com.aldemir.barcodereader.util.Constants
import com.aldemir.barcodereader.util.LogUtils
import com.aldemir.barcodereader.util.Status

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcode = intent.getStringExtra(Constants.KEY_BARCODE)

        binding.textInputBarcode.setText(barcode)
        binding.textInputBarcode.requestFocus()
        binding.buttonEnterBarcode.isEnabled = false

        listeners()

        observers()

    }

    private fun listeners() {
        binding.textInputBarcode.setOnFocusChangeListener { _, _ ->
            viewModel.barcodeDataChanged(binding.textInputBarcode.text.toString())
        }
        binding.textInputBarcode.addTextChangedListener {
            viewModel.barcodeDataChanged(binding.textInputBarcode.text.toString())
        }
        binding.textInputQuantities.addTextChangedListener {
            viewModel.quantityDataChanged(binding.textInputQuantities.text.toString())
        }
        binding.buttonEnterBarcode.setOnClickListener {
            viewModel.register()
            showLoading()
        }

    }

    private fun observers() {
        viewModel.news.observe(this@RegisterActivity, Observer { news ->
            when (news.status) {
                Status.SUCCESS -> {
                    finish()
                    hideLoading()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    finish()
                    hideLoading()
                }
            }
        })
        viewModel.registerForm.observe(this@RegisterActivity, Observer { formState ->
            if(formState.barcodeError != null) {
                binding.textInputBarcode.error = getString(formState.barcodeError)
            } else {
                binding.textInputBarcode.error = null
            }

            if (formState.quantityError != null) {
                binding.textInputQuantities.error = getString(formState.quantityError)
            } else {
                binding.textInputQuantities.error = null
            }
        })

        viewModel.buttonIsEnabled.observe(this@RegisterActivity, Observer { isEnabled ->
            binding.buttonEnterBarcode.isEnabled = isEnabled
        })
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.buttonEnterBarcode.isEnabled = true
    }
    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.buttonEnterBarcode.isEnabled = false
    }
}