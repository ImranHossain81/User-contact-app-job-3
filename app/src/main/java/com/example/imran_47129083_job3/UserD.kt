package com.example.imran_47129083_job3

import com.google.firebase.Timestamp


data class UserD(

    var id:String?=null,
    var name:String?=null,
    var email:String?=null,
    val phone:String?=null,
    val address: String? =null,
    val timeStamp: Timestamp?=null
)
