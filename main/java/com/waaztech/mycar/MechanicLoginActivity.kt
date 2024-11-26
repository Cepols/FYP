package com.waaztech.mycar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.waaztech.mycar.model.Customer
import com.waaztech.mycar.model.Mechanic
import com.waaztech.mycar.util.Storage

class MechanicLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mechanic_login)

        val btnLoginCustomer = findViewById<Button>(R.id.btnLoginCustomer)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val edtMech = findViewById<EditText>(R.id.edtMechInput)
        val database: DatabaseReference = Firebase.database.reference

        btnLoginCustomer.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {

            database.child("mechanics").child(edtMech.text.toString()).get().addOnSuccessListener {

                if (it.value != null && edtMech.text.toString().isNotEmpty()) {
                    val mechanic: Mechanic? = it.getValue(Mechanic::class.java)
                    if (mechanic != null) {
                        Storage().saveMechanicInfo(mechanic)
                    }
                    val intent = Intent(this, MainMechanicActivity::class.java)
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

    }
}