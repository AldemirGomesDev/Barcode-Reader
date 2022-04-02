package com.aldemir.barcodereader.ui.home


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.databinding.ActivityMainBinding
import com.aldemir.barcodereader.ui.ScanActivity
import com.aldemir.barcodereader.ui.login.LoginActivity
import com.aldemir.barcodereader.ui.register.RegisterActivity
import com.aldemir.barcodereader.util.Constants
import com.aldemir.barcodereader.util.LogUtils
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "return_scan_barcode"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var result: ScanIntentResult
    private val mainViewModel: MainViewModel by viewModels()

    private val zxingActivityResultLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        this.result = result
        getContent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        listeners()
    }

    private fun listeners() {
        binding.btnCapture.setOnClickListener {
            val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
                ScanActivity::class.java
            ).setCameraId(0)
            zxingActivityResultLauncher.launch(options)
        }
        binding.btnExit.setOnClickListener {
            startLoginActivity()
        }
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
            LogUtils.info(tag = TAG, message = "Scanned: " + result.contents)
            startRegisterActivity(result.contents)
        }
    }

    private fun startRegisterActivity(barcode: String) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra(Constants.KEY_BARCODE, barcode)
        startActivity(intent)
    }

    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}