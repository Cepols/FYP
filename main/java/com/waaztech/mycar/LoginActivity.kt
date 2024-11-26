package com.waaztech.mycar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson
import com.waaztech.mycar.model.Customer
import com.waaztech.mycar.util.Storage

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val edtPlate = findViewById<EditText>(R.id.edtPlateInput)
        val edtPass = findViewById<EditText>(R.id.edtPassInput)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnLoginMechanic = findViewById<Button>(R.id.btnLoginMechanic)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val database: DatabaseReference = Firebase.database.reference

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            database.child("users").child(edtPlate.text.toString()).get().addOnSuccessListener {

                if (it.value != null && edtPlate.text.toString().isNotEmpty()) {
                    val customer: Customer? = it.getValue(Customer::class.java)
                    if (customer != null) {
                        Storage().saveUserInfo(customer)
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Login Info", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Invalid login info", Toast.LENGTH_LONG).show()
            }
        }

        btnLoginMechanic.setOnClickListener {
            val intent = Intent(this, MechanicLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}