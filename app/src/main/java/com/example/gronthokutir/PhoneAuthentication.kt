package com.example.gronthokutir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class PhoneAuthentication : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_authentication)

        val intent : Intent = intent
        val username : String = intent.getStringExtra("username")
        val phoneNo : String = intent.getStringExtra("phoneNo")
        val password : String = intent.getStringExtra("password")
        val confirmPassword : String = intent.getStringExtra("confirmPassword")


        Toast.makeText(this,username + phoneNo + password,Toast.LENGTH_SHORT).show()
    }
}
