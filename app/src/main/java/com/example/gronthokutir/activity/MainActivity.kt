package com.example.gronthokutir.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
import com.example.gronthokutir.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mobileNoTIET: TextInputEditText = findViewById(R.id.mobileNoTIET)
        val passwordTIL: TextInputLayout = findViewById(R.id.passwordTIL)
        val passwordTIET: TextInputEditText = findViewById(R.id.passwordTIET)
        val logInBtn: Button = findViewById(R.id.logInBtn);
        val forgetPasswordTV: TextView = findViewById(R.id.forgetPasswordTV)
        val doRegisterTV: TextView = findViewById(R.id.doRegisterTV)
        val termsAndConditionTV: TextView = findViewById(R.id.termsAndConditionTV)
        val containerLL: LinearLayout = findViewById(R.id.containerLL)
        val linearDotsLoader: LinearDotsLoader = findViewById(R.id.linearDotLoader)

        var loader = LinearDotsLoader(this)
        loader.defaultColor = ContextCompat.getColor(this, R.color.green)
        loader.selectedColor = ContextCompat.getColor(this,
            R.color.darkGreen
        )
        loader.isSingleDir = true
        loader.noOfDots = 5
        loader.selRadius = 1
        loader.expandOnSelect = false
        loader.radius = 4
        loader.dotsDistance = 4
        loader.animDur = 300
        loader.firstShadowColor = ContextCompat.getColor(this,
            R.color.green
        )
        loader.secondShadowColor = ContextCompat.getColor(this,
            R.color.green
        )
        loader.showRunningShadow = true
        containerLL.addView(loader)


        logInBtn.setOnClickListener {
            if (!validateMobileNo() || !validatePassword()) {
                return@setOnClickListener
            } else {
                linearDotsLoader.visibility = View.VISIBLE

                mobileNoTIL.error = null
                passwordTIL.error = null
                var mobileNo = mobileNoTIET.text.toString().trim()
                var password = passwordTIET.text.toString().trim()
                val registeredUserReference =
                    FirebaseDatabase.getInstance().reference.child("registeredUsers")
                        .child(mobileNo)
                registeredUserReference.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            var dbPassword = snapshot.child("password").value.toString()
                            if (password == dbPassword) {
                                linearDotsLoader.visibility = View.INVISIBLE
                                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                linearDotsLoader.visibility = View.INVISIBLE
                                Toast.makeText(
                                    this@MainActivity,
                                    "ভুল পাসওয়ার্ড",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            linearDotsLoader.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@MainActivity,
                                "অনুগ্রহ করে আপনার মোবাইল নম্বরটি রেজিস্টার করুন।",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
            }
        }

        doRegisterTV.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
