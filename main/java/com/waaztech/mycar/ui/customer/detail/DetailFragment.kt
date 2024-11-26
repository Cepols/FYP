package com.waaztech.mycar.ui.customer.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentDetail2Binding
import com.waaztech.mycar.databinding.FragmentDetailBinding
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.model.AppointmentMechanic
import com.waaztech.mycar.ui.mechanic.detail.DetailViewModel
import com.waaztech.mycar.util.Storage

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetail2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        _binding = FragmentDetail2Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val type = resources.getStringArray(R.array.Action)
        val appointment = Storage().getCustAppointmentInfo()

        binding.dateAppointment.text = appointment.date
        binding.numberPlate.text = appointment.id.split("|")[1]
        binding.status.text = appointment.status
        binding.remark.text = appointment.remarks?.replace("|", "\n\n")

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}