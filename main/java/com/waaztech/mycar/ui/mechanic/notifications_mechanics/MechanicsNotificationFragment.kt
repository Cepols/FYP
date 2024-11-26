package com.waaztech.mycar.ui.mechanic.notifications_mechanics

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.waaztech.mycar.LoginActivity
import com.waaztech.mycar.MechanicLoginActivity
import com.waaztech.mycar.R
import com.waaztech.mycar.databinding.FragmentMaintenanceBinding
import com.waaztech.mycar.databinding.FragmentMechanicsNotificationBinding
import com.waaztech.mycar.ui.customer.dashboard.DashboardViewModel
import com.waaztech.mycar.ui.mechanic.maintenance.MaintenanceViewModel
import com.waaztech.mycar.util.Storage

class MechanicsNotificationFragment : Fragment() {

    companion object {
        fun newInstance() = MechanicsNotificationFragment()
    }

    private val viewModel: MechanicsNotificationViewModel by viewModels()
    private var _binding: FragmentMechanicsNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentMechanicsNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mechInfo = Storage().getMechanicInfo()

        binding.txtName.text = "Mechanic ID: ${mechInfo.id}"
        binding.txtEmail.text = mechInfo.scope
        binding.txtPhoneNumber.text = mechInfo.name

        binding.btnLogout.setOnClickListener {
            val intent = Intent(context, MechanicLoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}