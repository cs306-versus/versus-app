package com.github.versus

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.versus.user.UserAdapter
import com.github.versus.user.VersusUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ChatsActivity : AppCompatActivity() {

    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<VersusUser>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_chat)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseFirestore.getInstance().collection("user")

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.recyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userRecyclerView.adapter = adapter


        // Add a listener to the "user" collection
        mDbRef.addSnapshotListener { snapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
            if (error != null) {
                // Handle errors here
                return@addSnapshotListener
            }

            // Clear the userList before adding new data
            userList.clear()

            // Loop through each document in the "user" collection
            for (document in snapshot?.documents!!) {
                // Convert the document to a VersusUser object and add it to the userList
                val currentUser = document.toObject(VersusUser::class.java)
                userList.add(currentUser!!)
            }
            adapter.notifyDataSetChanged()
        }

    }

}