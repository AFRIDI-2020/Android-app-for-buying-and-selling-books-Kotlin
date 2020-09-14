package com.example.gronthokutir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mobileNoTIET: TextInputEditText = findViewById(R.id.mobileNoTIET)
        val mobileNoTIL: TextInputLayout = findViewById(R.id.mobileNoTIL)
        val passwordTIL: TextInputLayout = findViewById(R.id.passwordTIL)
        val passwordTIET: TextInputEditText = findViewById(R.id.passwordTIET)
        val logInBtn: Button = findViewById(R.id.logInBtn);
        val forgetPasswordTV: TextView = findViewById(R.id.forgetPasswordTV)
        val doRegisterTV: TextView = findViewById(R.id.doRegisterTV)
        val termsAndConditionTV: TextView = findViewById(R.id.termsAndConditionTV)


        logInBtn.setOnClickListener {
            if (!validateMobileNo() || !validatePassword()) {
                return@setOnClickListener
            } else {
                mobileNoTIL.error = null
                passwordTIL.error = null
                Toast.makeText(this, "ঠিকাছে", Toast.LENGTH_SHORT).show()
            }
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
        } else
            return true
    }

}
