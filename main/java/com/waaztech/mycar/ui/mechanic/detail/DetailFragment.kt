package com.waaztech.mycar.ui.mechanic.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentDetailBinding
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.model.AppointmentMechanic
import com.waaztech.mycar.util.Storage

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val database: DatabaseReference = Firebase.database.reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val type = resources.getStringArray(R.array.Action)
        val appointment = Storage().getAppointmentInfo()

        val adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_spinner, type
            )
        }
        binding.spinnerStatus.adapter = adapter

        binding.dateAppointment.text = appointment.date
        binding.numberPlate.text = appointment.id.split("|")[1]
        binding.edtPartsInput.setText(appointment.remarks.toString())

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnConfirm.setOnClickListener {
            database.child("appointment").child(appointment.id.split("|")[1]).child(
                appointment.date
            ).setValue(Appointment(appointment.id, appointment.title, appointment.date, binding.spinnerStatus.selectedItem.toString(), binding.edtPartsInput.text.toString().replace(" ", "").trim()))

            database.child("mechanicAppointment").child(appointment.date).child(
                appointment.id.split("|")[1]
            ).setValue(
                AppointmentMechanic(
                    appointment.id,
                    appointment.title,
                    appointment.date,
                    appointment.id.split("|")[1],
                    binding.spinnerStatus.selectedItem.toString(),
                    binding.edtPartsInput.text.toString().replace(" ", "").trim()
                )
            ).addOnSuccessListener {
                database.child("mechanicAppointment").child(appointment.date).get()
                    .addOnSuccessListener { dataSnapshot ->
                        Toast.makeText(context, "Updated appointment status", Toast.LENGTH_LONG)
                            .show()
                    }
            }.addOnFailureListener {
                Toast.makeText(context, "Failed update appointment status", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}