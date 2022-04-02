package com.aldemir.barcodereader.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityScanBinding
import com.aldemir.barcodereader.ui.register.RegisterActivity
import com.aldemir.barcodereader.util.Constants
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import java.util.*

class ScanActivity : Activity(), DecoratedBarcodeView.TorchListener {
    private lateinit var binding: ActivityScanBinding
    private lateinit var capture: CaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var viewfinderView: ViewfinderView
    private lateinit var switchFlashlightButton: ImageView
    private lateinit var enterBarcodeButton: Button
    private var isTorch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        barcodeScannerView = binding.barcodeView
        barcodeScannerView.setTorchListener(this)
        Log.i("ActivityScan", "is focused: ${barcodeScannerView.isAccessibilityFocused}")

        switchFlashlightButton = binding.imgFlash
        enterBarcodeButton = binding.buttonEnterBarcode
        viewfinderView = findViewById(R.id.zxing_viewfinder_view)

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...
        if (!hasFlash()) {
            switchFlashlightButton.visibility = View.GONE
        }

        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)
        capture.decode()

        changeMaskColor(null)
        changeLaserVisibility(true)

        onClickListeners()
    }

    private fun onClickListeners() {
        enterBarcodeButton.setOnClickListener {
            startRegisterActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View?) {
        if (!isTorch) {
            barcodeScannerView.setTorchOn()
        } else {
            barcodeScannerView.setTorchOff()
        }
    }

    private fun changeMaskColor(view: View?) {
        val rnd = Random()
        val color = Color.argb(100, rnd.nextInt(25), rnd.nextInt(25), rnd.nextInt(112))
        viewfinderView.setMaskColor(color)
    }

    private fun changeLaserVisibility(visible: Boolean) {
        viewfinderView.setLaserVisibility(visible)
    }

    override fun onTorchOn() {
        isTorch = true
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_on)
    }

    override fun onTorchOff() {
        isTorch = false
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_off)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra(Constants.KEY_BARCODE, "")
        startActivity(intent)
        finish()
    }
}