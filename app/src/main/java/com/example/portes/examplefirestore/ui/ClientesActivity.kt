package com.example.portes.examplefirestore.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.portes.examplefirestore.models.Customer
import com.example.portes.examplefirestore.R
import com.example.portes.examplefirestore.utils.COLL_CUSTOMER
import com.example.portes.examplefirestore.utils.showTask

import kotlinx.android.synthetic.main.activity_clientes.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_clientes.*

class ClientesActivity : AppCompatActivity() {
    val TAG = ClientesActivity::class.java.simpleName
    val mDbReference = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
        fabNewCustomer.setOnClickListener { createCustomer() }
    }
    fun createCustomer() {
        val mCustomer = Customer(lblId.text.toString(),
                lblUser.text.toString(),
                lblLastaName.text.toString(),
                lblAge.text.toString().toInt(),
                lblEmail.text.toString())

        mDbReference.collection(COLL_CUSTOMER)
                .add(mCustomer)
                .addOnSuccessListener {
                    clearItems()
                    showTask("Cliente creado")
                }
                .addOnFailureListener {
                    exception -> Log.e(TAG, ":${exception.localizedMessage}")
                }

    }
    fun clearItems() {
        lblId.text.clear()
        lblUser.text.clear()
        lblLastaName.text.clear()
        lblAge.text.clear()
        lblEmail.text.clear()
    }

}
