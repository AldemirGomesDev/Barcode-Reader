package com.aldemir.barcodereader.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityLoginBinding
import com.aldemir.barcodereader.extensions.afterTextChanged
import com.aldemir.barcodereader.extensions.openActivity
import com.aldemir.barcodereader.ui.BaseActivity
import com.aldemir.barcodereader.ui.home.MainActivity
import com.aldemir.barcodereader.util.LogUtils
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    companion object {
        const val TAG = "ActivityLogin"
    }

    private val loginViewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addInitialDataListener()

        listeners()

        observers()

    }

    private fun addInitialDataListener() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            return@addOnPreDrawListener loginViewModel.isAppReady.value ?: false
        }
    }

    private fun listeners() {
        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            binding.btnLogin.setOnClickListener {
                showLoading(binding.loading)
                disableButton(binding.btnLogin as MaterialButton)
                loginViewModel.login(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
    }

    private fun observers() {
        loginViewModel.userSession.observe(this) { userSession ->
            LogUtils.info(TAG, "userSession: $userSession")
            hideLoading(binding.loading)
            if (userSession.statusCode == 200) {
                openActivity(MainActivity::class.java)
                finish()
            } else {
                enableButton(binding.btnLogin as MaterialButton)
                showMessageToast(getString(R.string.login_failed))
            }
        }

        loginViewModel.loginFormState.observe(this) { loginState ->

            binding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        }
    }
}