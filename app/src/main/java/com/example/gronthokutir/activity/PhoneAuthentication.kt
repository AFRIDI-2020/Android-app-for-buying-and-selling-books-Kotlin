package com.example.gronthokutir.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.example.gronthokutir.R
import com.example.gronthokutir.model.RegisteredUser
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_phone_authentication.*

class PhoneAuthentication : AppCompatActivity() {

    lateinit var verificationCode: String
    lateinit var verificationId: String
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var username: String
    lateinit var phoneNo: String
    lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_authentication)

        val verificationCodeTIET: TextInputEditText = findViewById(R.id.verificationCodeTIET)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        val verificationCodeTIL: TextInputLayout = findViewById(R.id.verificationCodeTIL)

        var loader = LinearDotsLoader(this)
        loader.defaultColor = ContextCompat.getColor(this,
            R.color.darkGreen
        )
        loader.selectedColor = ContextCompat.getColor(this, R.color.white)
        loader.isSingleDir = true
        loader.noOfDots = 5
        loader.selRadius = 1
        loader.expandOnSelect = false
        loader.radius = 4
        loader.dotsDistance = 4
        loader.animDur = 300
        loader.firstShadowColor = ContextCompat.getColor(this,
            R.color.darkGreen
        )
        loader.secondShadowColor = ContextCompat.getColor(this,
            R.color.darkGreen
        )
        loader.showRunningShadow = true
        containerLL.addView(loader)

        val intent: Intent = intent
        verificationId = intent.getStringExtra("verificationId")
        username = intent.getStringExtra("username")
        phoneNo = intent.getStringExtra("phoneNo")
        password = intent.getStringExtra("password")


        confirmRegisterBtn.setOnClickListener {

            verificationCode = verificationCodeTIET.text.toString()
            if (verificationCode.length == 6) {
                linearDotLoader.visibility = View.VISIBLE
                verify(verificationCode)
                verificationCodeTIL.error = null
            } else {
                verificationCodeTIL.error = "ম্যাসেজে পাঠানো ৬ ডিজিটের কোডটি লিখুন"
            }
        }


    }

    private fun verify(verificationCode: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, verificationCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val registeredUser =
                    RegisteredUser(username, password)
                databaseReference.child("registeredUsers").child(phoneNo).setValue(registeredUser)
                    .addOnCompleteListener() {
                        if (task.isSuccessful) {
                            Toast.makeText(this, "রেজিস্ট্রেশন নিশ্চিত হয়েছে", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
