package com.github.versus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.versus.user.UserAdapter
import com.github.versus.user.VersusUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : Fragment() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<VersusUser>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : CollectionReference

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_chat, container, false)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseFirestore.getInstance().collection("user")

        userList = ArrayList()
        adapter = UserAdapter(requireContext(), userList)

        userRecyclerView = view.findViewById(R.id.recyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        userRecyclerView.adapter = adapter

        userList.add(VersusUser.Builder("asdfghjklö").setFirstName("Abdess").setLastName("Derouich").build())
        userList.add(VersusUser.Builder("qwertzuiop").setFirstName("Aymane").setLastName("Lamyaghri").build())
        userList.add(VersusUser.Builder("stevzdbpg ").setFirstName("Adam").setLastName("Mernissi").build())
        userList.add(VersusUser.Builder("hehehehe").setFirstName("De Bruyne").setLastName("hamada").build())
        userList.add(VersusUser.Builder("nibbisbvfd").setFirstName("Gustavo").setLastName("Peperoni").build())

        /* code to use once complete link to user database is made
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
        */

        return view
    }
}