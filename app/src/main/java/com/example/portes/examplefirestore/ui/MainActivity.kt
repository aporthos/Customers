package com.example.portes.examplefirestore.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.portes.examplefirestore.R
import com.example.portes.examplefirestore.adapters.AdtClientes
import com.example.portes.examplefirestore.utils.COLL_CUSTOMER
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    lateinit var mDbReference: FirebaseFirestore
    lateinit var  mAdtClientes: AdtClientes
    companion object {
        val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        FirebaseFirestore.setLoggingEnabled(true)
        mDbReference = FirebaseFirestore.getInstance()

        fab.setOnClickListener {
            val mIntent = Intent(this, ClientesActivity::class.java)
            startActivity(mIntent)
        }
        inicializarView()
    }

    override fun inicializarView() {
        rViewCustomer.layoutManager = LinearLayoutManager(this)
        rViewCustomer.setHasFixedSize(true)
        val mQuery:Query = mDbReference.collection(COLL_CUSTOMER).limit(50)
        mAdtClientes = object: AdtClientes(applicationContext, mQuery, {
            //showTask("El customer ${it.id}")
        }) {
            override fun onDataChanged() {
                super.onDataChanged()
                Log.i(TAG, "onDataChanged: $itemCount")
            }
            override fun onError(e: FirebaseFirestoreException) {
                super.onError(e)
                Log.i(TAG, "onError:")
            }
        }
        rViewCustomer.adapter = mAdtClientes
    }

    override fun onStart() {
        super.onStart()
        mAdtClientes.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdtClientes.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
