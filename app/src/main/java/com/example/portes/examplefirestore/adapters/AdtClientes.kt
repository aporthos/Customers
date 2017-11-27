package com.example.portes.examplefirestore.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.portes.examplefirestore.R
import com.example.portes.examplefirestore.models.Customer
import com.example.portes.examplefirestore.utils.inflate
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_customer.view.*

/**
 * Created by portes on 19/11/17.
 */
open class AdtClientes(val mContext: Context, mQuery: Query, val listener: (Customer) -> Unit): FirestoreAdapter<AdtClientes.ViewHolder>(mQuery) {
    companion object {
        val TAG = "AdtClientes"
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.item_customer))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            val TAG = "ViewHolder"
        }
        fun bind(mSnapshot: DocumentSnapshot) = with(itemView) {
            val mCustomer:Customer = mSnapshot.toObject(Customer::class.java)
            lblId.text = mSnapshot.id
            lblName.text = mCustomer.name
            lblAge.text = mCustomer.age.toString()
            lblEmail.text = mCustomer.email
            //setOnClickListener { listener(mCustomer) }
        }
    }
}