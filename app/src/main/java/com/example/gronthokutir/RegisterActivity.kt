package com.example.gronthokutir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

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
        val registerBtn : Button = findViewById(R.id.registerBtn)

        val containerLL: LinearLayout = findViewById(R.id.containerLL)

        var username : String? = null
        var phoneNo : String? = null
        var password : String? = null
        var confirmPassword : String? = null

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

        var linearDotsLoader : LinearDotsLoader = findViewById(R.id.linearDotLoader)

        registerBtn.setOnClickListener {
            if(!validateUsername() || !validateMobileNo() || !validatePassword() || !validateConfirmPassword()){
                return@setOnClickListener
            }
            else{

                linearDotsLoader.visibility = View.VISIBLE

                Handler().postDelayed({

                    username = usernameTIET.text.toString()
                    phoneNo = mobileNoTIET.text.toString()
                    password = passwordTIET.text.toString()
                    confirmPassword = passwordTIET.text.toString()


                    val intent : Intent = Intent(this,PhoneAuthentication::class.java)
                    intent.putExtra("username",username)
                    intent.putExtra("phoneNo",phoneNo)
                    intent.putExtra("password",password)
                    intent.putExtra("confirmPassword",confirmPassword)
                    startActivity(intent)
                },3000)



            }
        }

    }


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
        }
        else{
            confirmPasswordTIL.error = null
            return true
        }
    }



}
