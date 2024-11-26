package com.waaztech.mycar.ui.customer.home.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waaztech.mycar.R
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.ui.customer.dashboard.DashboardFragment
import com.waaztech.mycar.ui.customer.home.HomeFragment

class AppointmentAdapter (private val mList: List<Appointment>, val dashboardFragment: DashboardFragment) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val appointment = mList[position]

        holder.title.text = appointment.title.replace("-", " ")
        holder.date.text = appointment.date
        holder.status.text = appointment.status
        holder.view.setOnClickListener {
            dashboardFragment.navigateToDetails(appointment)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val status: TextView = itemView.findViewById(R.id.status)
        val view: RelativeLayout = itemView.findViewById(R.id.apptLayout)

    }
}