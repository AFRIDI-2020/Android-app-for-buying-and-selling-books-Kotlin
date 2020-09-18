package com.example.gronthokutir

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var username: String
    lateinit var phoneNo: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val usernameTIL: TextInputLayout = findViewById(R.id.usernameTIL)
        val usernameTIET: TextInputEditText = findViewById(R.id.usernameTIET)
        val mobileNoTIL: TextInputLayout = findViewById(R.id.mobileNoTIL)
        val mobileNoTIET: TextInputEditText = findViewById(R.id.mobileNoTIET)
        val passwordTIL: TextInputLayout = findViewById(R.id.passwordTIL)
        val passwordTIET: TextInputEditText = findViewById(R.id.passwordTIET)
        val confirmPasswordTIL: TextInputLayout = findViewById(R.id.confirmPasswordTIL)
        val confirmPasswordTIET: TextInputEditText = findViewById(R.id.confirmPasswordTIET)
        val registerBtn: Button = findViewById(R.id.registerBtn)
        var verificationId: String? = null

        val containerLL: LinearLayout = findViewById(R.id.containerLL)
        firebaseAuth = FirebaseAuth.getInstance()

        var loader = LinearDotsLoader(this)
        loader.defaultColor = ContextCompat.getColor(this, R.color.darkGreen)
        loader.selectedColor = ContextCompat.getColor(this, R.color.white)
        loader.isSingleDir = true
        loader.noOfDots = 5
        loader.selRadius = 1
        loader.expandOnSelect = false
        loader.radius = 4
        loader.dotsDistance = 4
        loader.animDur = 300
        loader.firstShadowColor = ContextCompat.getColor(this, R.color.darkGreen)
        loader.secondShadowColor = ContextCompat.getColor(this, R.color.darkGreen)
        loader.showRunningShadow = true
        containerLL.addView(loader)

        var linearDotsLoader: LinearDotsLoader = findViewById(R.id.linearDotLoader)

        registerBtn.setOnClickListener {
            if (!validateUsername() || !validateMobileNo() || !validatePassword() || !validateConfirmPassword()) {
                return@setOnClickListener
            } else {

                if (!isInternetConnected(this)) {

                    showDialog()
                } else {
                    linearDotsLoader.visibility = View.VISIBLE

                    username = usernameTIET.text.toString()
                    phoneNo = mobileNoTIET.text.toString()
                    password = passwordTIET.text.toString()
                    confirmPassword = passwordTIET.text.toString()

                    sendOTP()
                }

            }
        }

    }


    //for phone number authentication

    private fun sendOTP() {

        verificationCallBack()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+88$phoneNo",
            60,
            TimeUnit.SECONDS,
            this,
            callbacks
        )


    }

    private fun verificationCallBack() {


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {

                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verification: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                val verificationId: String = verification
                val intent: Intent = Intent(this@RegisterActivity, PhoneAuthentication::class.java)
                intent.putExtra("verificationId", verificationId)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }
    }

    //for user inputs validity

    private fun validateUsername(): Boolean {
        if (usernameTIET.text.toString().isEmpty()) {
            usernameTIL.error = "আপনার নাম লিখুন"
            return false
        } else {
            usernameTIL.error = null
            return true
        }
    }

    private fun validateMobileNo(): Boolean {
        if (mobileNoTIET.text.toString().isEmpty()) {
            mobileNoTIL.error = "মোবাইল নম্বর লিখুন"
            return false
        } else if ((mobileNoTIET.text.toString().length) != 11) {
            mobileNoTIL.error = "১১ ডিজিটের মোবাইল নম্বর লিখুন"
            return false
        } else {
            mobileNoTIL.error = null
            return true
        }
    }

    private fun validatePassword(): Boolean {
        if (passwordTIET.text.toString().isEmpty()) {
            passwordTIL.error = "পাসওয়ার্ড লিখুন"
            return false
        } else if (passwordTIET.text.toString().length < 6) {
            passwordTIL.error = "কমপক্ষে ৬ ডিজিটের পাসওয়ার্ড দিন"
            return false
        } else {
            passwordTIL.error = null
            return true
        }
    }

    private fun validateConfirmPassword(): Boolean {
        if (confirmPasswordTIET.text.toString().isEmpty()) {
            confirmPasswordTIL.error = "পাসওয়ার্ড পুনরায় লিখুন"
            return false
        } else if (!confirmPasswordTIET.text.toString().equals(passwordTIET.text.toString())) {
            confirmPasswordTIL.error = "পাসওয়ার্ড নিশিচত করুন"
            return false
        } else {
            confirmPasswordTIL.error = null
            return true
        }
    }

    //to check internet connection

    private fun isInternetConnected(registerActivity: RegisterActivity): Boolean {

        var result = false
        val connectivityManager: ConnectivityManager =
            registerActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return result
    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(this@RegisterActivity)
        builder.setTitle(null)
        builder.setMessage("আপনার ইন্টারনেট সংযোগ চালু করুন।")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->

            dialogInterface.cancel()
        }
        builder.setCancelable(false)
        builder.create().show()

    }


}
