package com.example.portes.examplefirestore.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

/**
 * Created by portes on 19/11/17.
 */
@IgnoreExtraProperties
data class Customer(val name:String = "", val user:String = "", val lastName:String = "", val age:Int = 0, val email:String = "", @get:Exclude var id: String = "")