package com.emanh.emanh.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.emanh.emanh.R
import com.emanh.emanh.databinding.ActivityEnterUserInformationBinding
import com.emanh.emanh.viewModel.LoginViewModel

class EnterUserInformationActivity : BaseActivity() {
    private lateinit var binding: ActivityEnterUserInformationBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_user_information)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        initViewModel()
        initButton()
    }

    private fun initViewModel() {
        loginViewModel.birthday.observe(this) {
            binding.editTextBirthday.setText(it)
        }

        loginViewModel.gender.observe(this) {
            binding.editTextGender.setText(it)
        }

        loginViewModel.provinceCityAdapter(this)

        loginViewModel.address.observe(this) {
            binding.editTextAddress.setAdapter(it)
        }

        loginViewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                startActivity(Intent(this, SetAvatarActivity::class.java))
            }
        }
    }

    private fun initButton() {
        binding.editTextBirthday.setOnClickListener {
            loginViewModel.showDatePickerDialog(this)
        }

        binding.editTextGender.setOnClickListener {
            loginViewModel.showGenderSelectionDialog(this)
        }

        binding.textViewBack.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.textViewNext.setOnClickListener {
            loginViewModel.validateEnterUserInformation(
                binding.editTextFirstName.text.toString(),
                binding.editTextLastName.text.toString(),
                binding.editTextAddress.text.toString(),
                binding.editTextBirthday.text.toString(),
                binding.editTextGender.text.toString()
            )
        }
    }
}