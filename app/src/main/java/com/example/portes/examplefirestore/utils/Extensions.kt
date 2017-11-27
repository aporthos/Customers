package com.example.portes.examplefirestore.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by portes on 19/11/17.
 */
fun Context.showTask (message: String, duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, message, duration).show()
}
fun ViewGroup.inflate(resource: Int): View = LayoutInflater.from(context).inflate(resource, this, false)
