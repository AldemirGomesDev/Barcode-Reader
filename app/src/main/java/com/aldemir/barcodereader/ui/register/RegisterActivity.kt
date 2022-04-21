package com.aldemir.barcodereader.ui.register

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.data.api.models.RequestProduct
import com.aldemir.barcodereader.data.api.models.RequestRegister
import com.aldemir.barcodereader.databinding.ActivityRegisterBinding
import com.aldemir.barcodereader.extensions.afterTextChanged
import com.aldemir.barcodereader.ui.BaseActivity
import com.aldemir.barcodereader.ui.ScanActivity
import com.aldemir.barcodereader.ui.model.ProductUiModel
import com.aldemir.barcodereader.util.LogUtils
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    companion object {
        const val TAG = "featureRegister"
    }

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

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
                LogUtils.error(
                    tag = TAG,
                    message = "Cancelled scan due to missing camera permission"
                )
            }
        } else {
            setBarcode(result.contents)
            getProduct()
            LogUtils.info(tag = TAG, message = "Scanned: " + result.contents)
        }
    }

    private fun listeners() {
        binding.layoutTextBarcode.isEndIconVisible = false
        binding.textInputBarcode.setOnFocusChangeListener { _, _ ->
            viewModel.barcodeDataChanged(binding.textInputBarcode.text.toString())
        }
        binding.textInputBarcode.afterTextChanged {
            binding.layoutTextBarcode.isEndIconVisible = it.length >= 5
            viewModel.barcodeDataChanged(binding.textInputBarcode.text.toString())
        }
        binding.textInputQuantities.afterTextChanged {
            viewModel.quantityDataChanged(binding.textInputQuantities.text.toString())
        }
        binding.buttonEnterBarcode.setOnClickListener {
            viewModel.register(
                RequestRegister(
                    code = binding.textInputBarcode.text.toString(),
                    quantity = binding.textInputQuantities.text.toString()
                )
            )
            showLoading(binding.loading)
            disableButton(binding.buttonEnterBarcode)
        }
        binding.btnScan.setOnClickListener {
            val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
                ScanActivity::class.java
            ).setCameraId(0)
            zxingActivityResultLauncher.launch(options)
        }

        binding.layoutTextBarcode.setEndIconOnClickListener {
            showLoading(binding.loadingProduct)
            getProduct()
        }

    }

    private fun observers() {
        viewModel.register.observe(this@RegisterActivity) { register ->
            hideLoading(binding.loading)
            enableButton(binding.buttonEnterBarcode)
            clearFields()
            when (register.statusCode) {
                200 -> {
                    showMessageToast(getString(R.string.register_success))
                }
                else -> {
                    finish()
                    showMessageToast(getString(R.string.register_error))
                }
            }
        }
        viewModel.product.observe(this@RegisterActivity) { product ->
            hideLoading(binding.loadingProduct)
            when (product.statusCode) {
                200 -> {
                    setDescriptionProduct(product = product)
                    showMessageToast(message = product.message)
                }
                else -> {
                    showMessageToast(message = "Error: ${product.message}")
                }
            }
        }
        viewModel.registerForm.observe(this@RegisterActivity) { formState ->
            if (formState.barcodeError != null) {
                binding.textInputBarcode.error = getString(formState.barcodeError)
            } else {
                binding.textInputBarcode.error = null
            }

            if (formState.quantityError != null) {
                binding.textInputQuantities.error = getString(formState.quantityError)
            } else {
                binding.textInputQuantities.error = null

            }
        }

        viewModel.buttonIsEnabled.observe(this@RegisterActivity) { isEnabled ->
            binding.buttonEnterBarcode.isEnabled = isEnabled
        }
    }

    private fun getProduct() {
        viewModel.getProduct(RequestProduct(code = binding.textInputBarcode.text.toString()))
    }

    private fun setBarcode(barcode: String) {
        binding.textInputBarcode.setText(barcode)
    }

    private fun setDescriptionProduct(product: ProductUiModel) {
        binding.textDescription.text = product.description
    }

    private fun clearFields() {
        LogUtils.error(tag = TAG, message = "clearFields")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        binding.textInputBarcode.text!!.clear()
        binding.textInputQuantities.text!!.clear()
        binding.textDescription.text = ""
        binding.textInputBarcode.clearFocus()
        binding.container.clearFocus()
    }

}