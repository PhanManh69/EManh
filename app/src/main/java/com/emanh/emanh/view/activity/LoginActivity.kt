package com.emanh.emanh.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityLoginBinding
import com.emanh.emanh.viewModel.LoginViewModel

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        intiButton()
        initViewModel()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun intiButton() {
        binding.editTextPassword.setOnTouchListener { _, event ->
            val drawableEnd = binding.editTextPassword.compoundDrawables[2]
            if (event.action == MotionEvent.ACTION_UP && drawableEnd != null) {
                if (event.rawX >= (binding.editTextPassword.right - drawableEnd.bounds.width())) {
                    loginViewModel.togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.buttonLogin.setOnClickListener {
            loginViewModel.validateLogin(
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.textViewForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

        binding.editTextPassword.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.validateLogin(
                    binding.editTextUsername.text.toString(),
                    binding.editTextPassword.text.toString()
                )
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initViewModel() {
        loginViewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}