package com.emanh.emanh.viewModel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.text.InputType
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanh.emanh.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class LoginViewModel : ViewModel() {
    private val _passwordIconStart = MutableLiveData<Int>()
    val passwordIconStart: LiveData<Int> get() = _passwordIconStart

    private val _passwordIconEnd = MutableLiveData<Int>()
    val passwordIconEnd: LiveData<Int> get() = _passwordIconEnd

    private val _inputType = MutableLiveData<Int>()
    val inputType: LiveData<Int> get() = _inputType

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> get() = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _phoneNumberError = MutableLiveData<String?>()
    val phoneNumberError: LiveData<String?> get() = _phoneNumberError

    private val _emailAddressError = MutableLiveData<String?>()
    val emailAddressError: LiveData<String?> get() = _emailAddressError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> get() = _confirmPasswordError

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    private val _birthday = MutableLiveData<String>()
    val birthday: LiveData<String> get() = _birthday

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _address = MutableLiveData<ArrayAdapter<String>>()
    val address: LiveData<ArrayAdapter<String>> get() = _address

    private val _firstNameError = MutableLiveData<String?>()
    val firstNameError: LiveData<String?> get() = _firstNameError

    private val _lastNameError = MutableLiveData<String?>()
    val lastNameError: LiveData<String?> get() = _lastNameError

    private val _addressError = MutableLiveData<String?>()
    val addressError: LiveData<String?> get() = _addressError

    private val _birthdayError = MutableLiveData<String?>()
    val birthdayError: LiveData<String?> get() = _birthdayError

    private val _genderError = MutableLiveData<String?>()
    val genderError: LiveData<String?> get() = _genderError

    private val _selectAvatarEvent = MutableLiveData<Unit>()
    val selectAvatarEvent: LiveData<Unit> get() = _selectAvatarEvent

    private val _avatarUri = MutableLiveData<Uri>()
    val avatarUri: LiveData<Uri> get() = _avatarUri

    init {
        _passwordIconStart.value = R.drawable.icon_password
        _passwordIconEnd.value = R.drawable.icon_hidden_pass
        _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        _isFormValid.value = false
    }

    fun togglePasswordVisibility() {
        if (_inputType.value == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            _passwordIconEnd.value = R.drawable.icon_show_pass
        } else {
            _inputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            _passwordIconEnd.value = R.drawable.icon_hidden_pass
        }
    }

    fun validateLogin(username: String, password: String) {
        _usernameError.value = if (username.isEmpty()) "Tên đăng nhập không được để trống" else null
        _passwordError.value = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Độ dài mật khẩu phải lớn hơn 6"
            else -> null
        }
        _isFormValid.value = _usernameError.value == null && _passwordError.value == null
    }

    fun validateRegister(username: String, phoneNumber: String, emailAddress: String, password: String, confirmPassword: String) {
        _usernameError.value = if (username.isEmpty()) "Tên đăng nhập không được để trống" else null
        _phoneNumberError.value = when {
            phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
            phoneNumber.length != 10 -> "Định dạng số điện thoại không hợp lệ"
            else -> null
        }
        _emailAddressError.value = when {
            emailAddress.isEmpty() -> "Địa chỉ email không được để trống"
            !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> "Định dạng địa chỉ email không hợp lệ"
            else -> null
        }
        _passwordError.value = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Độ dài mật khẩu phải lớn hơn 6"
            else -> null
        }
        _confirmPasswordError.value = when {
            confirmPassword.isEmpty() -> "Mật khẩu không được để trống"
            confirmPassword.length < 6 -> "Độ dài mật khẩu phải lớn hơn 6"
            confirmPassword != password -> "Mật khẩu không trùng khớp nhau"
            else -> null
        }
        _isFormValid.value = _usernameError.value == null
                && _phoneNumberError.value == null
                && _emailAddressError.value == null
                && _passwordError.value == null
                && _confirmPasswordError.value == null
    }

    fun showDatePickerDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                _birthday.value = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    @SuppressLint("InflateParams")
    fun showGenderSelectionDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_item_gender, null)
        val dialog = Dialog(context, R.style.DialogTheme)
        dialog.setContentView(dialogView)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)

        val male = dialogView.findViewById<TextView>(R.id.textViewMale)
        val female = dialogView.findViewById<TextView>(R.id.textViewFemale)
        val other = dialogView.findViewById<TextView>(R.id.textViewOther)
        val leave = dialogView.findViewById<TextView>(R.id.textViewLeave)

        male.setOnClickListener {
            _gender.value = male.text.toString()
            dialog.dismiss()
        }

        female.setOnClickListener {
            _gender.value = female.text.toString()
            dialog.dismiss()
        }

        other.setOnClickListener {
            _gender.value = other.text.toString()
            dialog.dismiss()
        }

        leave.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun provinceCityAdapter(context: Context) {
        val items: Array<String> = context.resources.getStringArray(R.array.provinceCity)
        val provinceCityAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)
        _address.value = provinceCityAdapter
    }

    fun validateEnterUserInformation(firstName: String, lastName: String, address: String, birthday: String, gender: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        val birthdayDate: Date?

        try {
            birthdayDate = dateFormat.parse(birthday)
        } catch (e: ParseException) {
            _birthdayError.value = "Ngày sinh không hợp lệ"
            return
        }

        val birthdayCalendar = Calendar.getInstance().apply {
            if (birthdayDate != null) {
                time = birthdayDate
            }
        }
        var age = currentDate.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR)

        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthdayCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        _firstNameError.value = if (firstName.isEmpty()) "Họ và tên đệm không được để trống" else null
        _lastNameError.value = if (lastName.isEmpty()) "Tên không được để trống" else null
        _addressError.value = if (address.isEmpty()) "Địa chỉ hiện tại không được để trống" else null
        _birthdayError.value = when {
            birthday.isEmpty() -> "Ngày sinh nhật không được để trống"
            age < 14 -> "Bạn phải lớn hơn 14 tuổi"
            else -> null
        }
        _genderError.value = if (gender.isEmpty()) "Giới tính không được để trống" else null

        _isFormValid.value = _firstNameError.value == null
                && _lastNameError.value == null
                && _addressError.value == null
                && _birthdayError.value == null
                && _genderError.value == null
    }

    fun validateForgetPassword(phoneNumber: String, emailAddress: String) {
        _phoneNumberError.value = when {
            phoneNumber.isEmpty() -> "Số điện thoại không được để trống"
            phoneNumber.length != 10 -> "Định dạng số điện thoại không hợp lệ"
            else -> null
        }
        _emailAddressError.value = when {
            emailAddress.isEmpty() -> "Địa chỉ email không được để trống"
            !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> "Định dạng địa chỉ email không hợp lệ"
            else -> null
        }

        _isFormValid.value = _phoneNumberError.value == null
                && _emailAddressError.value == null
    }

    fun validateResetPassword(password: String, confirmPassword: String) {
        _passwordError.value = when {
            password.isEmpty() -> "Mật khẩu không được để trống"
            password.length < 6 -> "Độ dài mật khẩu phải lớn hơn 6"
            else -> null
        }
        _confirmPasswordError.value = when {
            confirmPassword.isEmpty() -> "Mật khẩu không được để trống"
            confirmPassword.length < 6 -> "Độ dài mật khẩu phải lớn hơn 6"
            confirmPassword != password -> "Mật khẩu không trùng khớp nhau"
            else -> null
        }

        _isFormValid.value = _passwordError.value == null
                && _confirmPasswordError.value == null
    }

    fun onAvatarClicked() {
        _selectAvatarEvent.value = Unit
    }

    fun setAvatarUri(uri: Uri) {
        _avatarUri.value = uri
    }

    fun sendEmail(receiverEmail: String, codeConfirm: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val senderEmail = "phankhacmanh6903@gmail.com"
            val senderPassword = "kejx grwa fwxx zkcj"
            val stringHost = "smtp.gmail.com"

            val properties = Properties().apply {
                put("mail.smtp.host", stringHost)
                put("mail.smtp.port", "465")
                put("mail.smtp.ssl.enable", "true")
                put("mail.smtp.auth", "true")
            }

            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(senderEmail, senderPassword)
                }
            })

            try {
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(senderEmail))
                    addRecipient(Message.RecipientType.TO, InternetAddress(receiverEmail))
                    subject = "Mã xác nhân đăng ký tài khoản EMANH"
                    setText("Bạn đã yêu cầu xác minh qua email $receiverEmail. \nMã xác nhận của bạn là $codeConfirm.")
                }

                Transport.send(message)
                println("Email sent successfully!")
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

    fun createCodeConfirm(): String {
        val randomCode = (100000..999999).random()
        return randomCode.toString()
    }
}