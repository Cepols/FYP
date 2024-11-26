package com.waaztech.mycar.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Customer(val name: String? = null, val plateNumber: String? = null, val carModel: String? = null, val password: String? = null)
