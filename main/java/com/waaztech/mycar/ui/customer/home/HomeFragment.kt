package com.waaztech.mycar.ui.customer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentHomeBinding
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.util.Storage
import java.text.SimpleDateFormat

class HomeFragment : Fragment(), OnCalendarDayClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var selectedDate: String = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val database: DatabaseReference = Firebase.database.reference

        val type = resources.getStringArray(R.array.Type)

        val adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_spinner, type
            )
        }
        binding.spinner.adapter = adapter

        binding.calendarView.setOnCalendarDayClickListener(this@HomeFragment)
        binding.calendarView.setHeaderColor(R.color.teal_700)
        binding.btnBook.setOnClickListener {
            val user = Storage().getUserInfo()
            var appointmentAlreadyMade = false

            database.child("appointment").child(user.plateNumber.toString()).get()
                .addOnSuccessListener { dataSnapshot ->

                    if (dataSnapshot.value != null) {
                        val appointmentList = dataSnapshot.children.mapNotNull { it.value }
                        val appointmentListObj = Gson().fromJson(
                            appointmentList.toString(),
                            Array<Appointment>::class.java
                        )

                        for (appointment in appointmentListObj) {
                            if (appointment.date == selectedDate) {
                                Toast.makeText(
                                    context,
                                    "You already made an appointment on this date.",
                                    Toast.LENGTH_LONG
                                ).show()
                                appointmentAlreadyMade = true
                            }
                        }

                        if (!appointmentAlreadyMade) {
                            val appointment = Appointment(
                                selectedDate + "|" + user.plateNumber,
                                binding.spinner.selectedItem.toString().replace(" ", "-"),
                                selectedDate,
                                "Pending","-"
                            )
                            user.plateNumber?.let { it1 ->
                                database.child("appointment").child(it1).child(selectedDate)
                                    .setValue(appointment)
                            }
                            user.plateNumber?.let { it1 ->
                                database.child("mechanicAppointment").child(selectedDate).child(
                                    it1
                                ).setValue(appointment)
                            }

                            Toast.makeText(
                                context,
                                "New appointment made, view under upcoming tab.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        val appointment = Appointment(
                            selectedDate + "|" + user.plateNumber,
                            binding.spinner.selectedItem.toString().replace(" ", "-"),
                            selectedDate,
                            "Pending", "-"
                        )
                        user.plateNumber?.let { it1 ->
                            database.child("appointment").child(it1).child(selectedDate)
                                .setValue(appointment)
                        }
                        user.plateNumber?.let { it1 ->
                            database.child("mechanicAppointment").child(selectedDate).child(
                                it1
                            ).setValue(appointment)
                        }
                        Toast.makeText(
                            context,
                            "New appointment made, view under upcoming tab.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }.addOnFailureListener {
                Toast.makeText(context, "Failed to retrieve appointments", Toast.LENGTH_LONG).show()
            }

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(calendarDay: CalendarDay) {
        val dateFormated = SimpleDateFormat("dd-MM-yyyy").format(calendarDay.calendar.time)
        selectedDate = dateFormated
    }

    override fun onResume() {
        super.onResume()

        val type = resources.getStringArray(R.array.Type)

        val adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_spinner, type
            )
        }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                val selectedItem = parent.getItemAtPosition(position).toString()

                if (selectedItem.contains("Preventive")) {
                    binding.description.text = getString(R.string.prevent_desc)
                } else if (selectedItem.contains("Major")) {
                    binding.description.text = getString(R.string.major_desc)
                } else if (selectedItem.contains("Minor")) {
                    binding.description.text = getString(R.string.minor_desc)
                } else {
                    binding.description.text = getString(R.string.general_desc)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case where no item is selected
            }
        }

    }
}