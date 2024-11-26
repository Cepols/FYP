package com.waaztech.mycar.util

import com.waaztech.mycar.model.Appointment
import com.waaztech.mycar.model.AppointmentMechanic
import com.waaztech.mycar.model.Customer
import com.waaztech.mycar.model.Mechanic

class Storage {

    fun saveUserInfo(user: Customer){
        Stash.put("customer", user)
    }

    fun getUserInfo(): Customer{
        return Stash.getObject<Customer>(
            "customer",
            Customer::class.java
        ) as Customer
    }

    fun saveMechanicInfo(user: Mechanic){
        Stash.put("mechanic", user)
    }

    fun getMechanicInfo(): Mechanic{
        return Stash.getObject<Mechanic>(
            "mechanic",
            Mechanic::class.java
        ) as Mechanic
    }

    fun saveCustAppointmentInfo(appointment: Appointment){
        Stash.put("appointmentCust", appointment)
    }

    fun getCustAppointmentInfo(): Appointment{
        return Stash.getObject<Appointment>(
            "appointmentCust",
            Appointment::class.java
        ) as Appointment
    }

    fun saveAppointmentInfo(appointment: AppointmentMechanic){
        Stash.put("appointment", appointment)
    }

    fun getAppointmentInfo(): AppointmentMechanic{
        return Stash.getObject<AppointmentMechanic>(
            "appointment",
            AppointmentMechanic::class.java
        ) as AppointmentMechanic
    }

}