package com.waaztech.mycar.ui.customer.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson
import com.waaztech.mycar.MainActivity
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentDashboardBinding
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.model.Customer
import com.waaztech.mycar.ui.customer.home.appointment.AppointmentAdapter
import com.waaztech.mycar.util.Storage

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val database: DatabaseReference = Firebase.database.reference
        val user = Storage().getUserInfo()

        database.child("appointment").child(user.plateNumber.toString()).get().addOnSuccessListener { dataSnapshot ->

            if (dataSnapshot.value != null) {
                val appointmentList = dataSnapshot.children.mapNotNull { it.value }
                val appointmentListObj = Gson().fromJson(appointmentList.toString(), Array<Appointment>::class.java)
                binding.recyclerAppointment.adapter = AppointmentAdapter(appointmentListObj.toList(), this)

            } else {
                Toast.makeText(context, "No appointments", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve appointments", Toast.LENGTH_LONG).show()
        }
        return root
    }


    fun navigateToDetails(appointment: Appointment){
        Storage().saveCustAppointmentInfo(appointment)
        findNavController().navigate(R.id.detailFragment2)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}