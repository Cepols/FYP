package com.waaztech.mycar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.waaztech.mycar.model.Customer

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val edtName = findViewById<EditText>(R.id.edtUserInput)
        val edtPlate = findViewById<EditText>(R.id.edtNumberPlateInput)
        val edtCarModel = findViewById<EditText>(R.id.edtCarModelInput)
        val edtPass = findViewById<EditText>(R.id.edtPassInput)

        val btnConfirm = findViewById<Button>(R.id.btnConfirmRegister)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val database: DatabaseReference = Firebase.database.reference

        btnConfirm.setOnClickListener {
            val user = Customer(
                edtName.text.toString(), edtPlate.text.toString(),
                edtCarModel.text.toString(), edtPass.text.toString()
            )
            database.child("users").child(edtPlate.text.toString()).setValue(user)
            finish()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}