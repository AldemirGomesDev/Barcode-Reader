package com.aldemir.barcodereader.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityRegisterBinding
import com.aldemir.barcodereader.ui.ScanActivity
import com.aldemir.barcodereader.ui.home.MainActivity
import com.aldemir.barcodereader.util.Constants
import com.aldemir.barcodereader.util.LogUtils
import com.aldemir.barcodereader.util.Status
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "return_scan_barcode"
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var result: ScanIntentResult
    private val viewModel: RegisterViewModel by viewModels()

    private val zxingActivityResultLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        this.result = result
        getContent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textInputBarcode.clearFocus()
        binding.container.requestFocus()
        binding.buttonEnterBarcode.isEnabled = false

        listeners()

        observers()
    }

    private fun getContent() {
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                LogUtils.error(tag = TAG, message = "Cancelled scan")
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                LogUtils.error(tag = TAG, message = "Cancelled scan due to missing camera permission")
            }
        } else {
            binding.textInputBarcode.setText(result.contents)
            LogUtils.info(tag = TAG, message = "Scanned: " + result.contents)
        }
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
        binding.btnScan.setOnClickListener {
            val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
                ScanActivity::class.java
            ).setCameraId(0)
            zxingActivityResultLauncher.launch(options)
        }

    }

    private fun observers() {
        viewModel.news.observe(this@RegisterActivity, Observer { news ->
            when (news.status) {
                Status.SUCCESS -> {
                    clearFields()
                    hideLoading()
                    showMessageToast(getString(R.string.register_success))
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    finish()
                    hideLoading()
                    showMessageToast(getString(R.string.register_error))
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

    private fun clearFields() {
        LogUtils.error(tag = TAG, message = "clearFields")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        binding.textInputBarcode.text!!.clear()
        binding.textInputQuantities.text!!.clear()
        binding.textDescription.text = ""
        binding.textInputBarcode.clearFocus()
        binding.container.requestFocus()
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.buttonEnterBarcode.isEnabled = true
    }
    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.buttonEnterBarcode.isEnabled = false
    }

    private fun showMessageToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}