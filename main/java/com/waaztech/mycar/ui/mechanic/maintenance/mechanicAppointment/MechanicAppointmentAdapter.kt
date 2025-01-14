package com.waaztech.mycar.ui.mechanic.maintenance.mechanicAppointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waaztech.mycar.R
import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.model.AppointmentMechanic
import com.waaztech.mycar.ui.mechanic.maintenance.MaintenanceFragment

class MechanicAppointmentAdapter (private val mList: List<AppointmentMechanic>, val maintenanceFragment: MaintenanceFragment) : RecyclerView.Adapter<MechanicAppointmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment_mechanic, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val appointment = mList[position]

        holder.title.text = appointment.title
        holder.date.text = appointment.date
        holder.status.text = appointment.status
        holder.plate.text = appointment.id.split("|")[1]
        holder.view.setOnClickListener{
            maintenanceFragment.onAppointmentClick(appointment)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val status: TextView = itemView.findViewById(R.id.txtStatus)
        val plate: TextView = itemView.findViewById(R.id.plateNumber)
        val view: RelativeLayout = itemView.findViewById(R.id.layoutAppt)

    }
}