package com.aldemir.barcodereader.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.databinding.ActivityLoginBinding
import com.aldemir.barcodereader.databinding.ActivityRegisterBinding
import com.aldemir.barcodereader.ui.BaseActivity
import com.aldemir.barcodereader.ui.home.MainActivity
import com.aldemir.barcodereader.util.LogUtils
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

        listeners()

        observers()

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

            binding.login.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
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
            if (userSession.statusCode == 200) {
                startMainActivity()
            } else {
                showLoginFailed(R.string.login_failed)
            }
        }

        loginViewModel.loginFormState.observe(this) { loginState ->

            binding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        }
    }

    private fun startMainActivity() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("keyIdentifier", "value")
        startActivity(intent)
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