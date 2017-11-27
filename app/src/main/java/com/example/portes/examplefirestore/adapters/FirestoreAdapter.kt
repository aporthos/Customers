package com.example.portes.examplefirestore.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.firestore.*

/**
 * Created by portes on 20/11/17.
 */
abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(var mQuery: Query) : RecyclerView.Adapter<VH>(),
        EventListener<QuerySnapshot> {
    val mSnapArrayList:ArrayList<DocumentSnapshot> = ArrayList()
    var mRegistration:ListenerRegistration? = null



    companion object {
        val TAG = "FirestoreAdapter"
    }
    
    override fun onEvent(documentSnapshots: QuerySnapshot, e: FirebaseFirestoreException?) {
        Log.i(TAG, "onEvent: ${documentSnapshots.documentChanges.size}")
        if (e != null) {
            Log.w(TAG, "onEvent:error", e)
            onError(e)
            return
        }
        for (mChange in documentSnapshots.documentChanges) {
            when (mChange.type) {
                DocumentChange.Type.ADDED -> onDocumentAdded(mChange)
                DocumentChange.Type.MODIFIED -> onDocumentModified(mChange)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(mChange)
            }
        }
        onDataChanged()
    }

    protected fun getSnapshot(index: Int): DocumentSnapshot = mSnapArrayList[index]
    fun startListening() {
        if (mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this)
        }
    }
    fun stopListening() {
        if (mRegistration != null) {
            mRegistration!!.remove()
            mRegistration = null
        }
        mSnapArrayList.clear()
        notifyDataSetChanged()
    }
    open fun setQuery(mQuery: Query){
        stopListening()
        mSnapArrayList.clear()
        notifyDataSetChanged()

        this.mQuery = mQuery
        startListening()
    }

    fun onDocumentAdded(change: DocumentChange) {
        mSnapArrayList.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mSnapArrayList.set(change.oldIndex, change.document)
            notifyItemChanged(change.oldIndex)
        } else {
            // Item changed and changed position
            mSnapArrayList.removeAt(change.oldIndex)
            mSnapArrayList.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    protected fun onDocumentRemoved(change: DocumentChange) {
        mSnapArrayList.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

    override fun getItemCount(): Int = mSnapArrayList.size

    protected open fun onError(e: FirebaseFirestoreException) {}
    protected open fun onDataChanged() {}
}