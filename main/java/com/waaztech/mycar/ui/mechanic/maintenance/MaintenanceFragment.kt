package com.waaztech.mycar.ui.mechanic.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentMaintenanceBinding
import com.waaztech.mycar.model.AppointmentMechanic
import com.waaztech.mycar.ui.customer.dashboard.DashboardViewModel
import com.waaztech.mycar.ui.mechanic.maintenance.mechanicAppointment.MechanicAppointmentAdapter
import com.waaztech.mycar.util.Storage
import java.text.SimpleDateFormat

class MaintenanceFragment : Fragment(), OnCalendarDayClickListener {

    companion object {
        fun newInstance() = MaintenanceFragment()
    }

    private val viewModel: MaintenanceViewModel by viewModels()
    private var _binding: FragmentMaintenanceBinding? = null
    private val binding get() = _binding!!
    private val database: DatabaseReference = Firebase.database.reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentMaintenanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mechanicAppointmentList = mutableListOf<AppointmentMechanic>()

        binding.calendarView.setOnCalendarDayClickListener(this@MaintenanceFragment)
        val dateFormated = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
        database.child("mechanicAppointment").child(dateFormated).get().addOnSuccessListener { dataSnapshot ->

            if (dataSnapshot.value != null) {
                val appointments = dataSnapshot.children.mapNotNull { it.value }
                val appointmentListObj =
                    Gson().fromJson(appointments.toString(), Array<AppointmentMechanic>::class.java)
                for (singleApp in appointmentListObj) {
                    if (singleApp.date == SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())) {
                        mechanicAppointmentList.add(singleApp)
                    }
                }
                binding.recyclerAppointment.adapter =
                    MechanicAppointmentAdapter(mechanicAppointmentList, this)
            } else {
                Toast.makeText(context, "No appointments", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve appointments", Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(calendarDay: CalendarDay) {
        val mechanicAppointmentList = mutableListOf<AppointmentMechanic>()
        val dateFormated = SimpleDateFormat("dd-MM-yyyy").format(calendarDay.calendar.time)
        database.child("mechanicAppointment").child(dateFormated).get().addOnSuccessListener { dataSnapshot ->

            if (dataSnapshot.value != null) {
                val appointments = dataSnapshot.children.mapNotNull { it.value }
                val appointmentListObj =
                    Gson().fromJson(appointments.toString(), Array<AppointmentMechanic>::class.java)

                for (singleApp in appointmentListObj) {
                    if (singleApp.date == dateFormated) {
                        mechanicAppointmentList.add(singleApp)
                    }
                }
                binding.recyclerAppointment.adapter = MechanicAppointmentAdapter(mechanicAppointmentList, this)
            } else {
                Toast.makeText(context, "No appointments", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve appointments", Toast.LENGTH_LONG).show()
        }
    }

    fun onAppointmentClick(mechanic: AppointmentMechanic){
        Storage().saveAppointmentInfo(mechanic)
        findNavController().navigate(R.id.detailFragment)
    }
}