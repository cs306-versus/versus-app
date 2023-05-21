package com.github.versus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.versus.chats.Chat
import com.github.versus.db.FsChatManager
import com.github.versus.db.FsUserManager
import com.github.versus.user.User
import com.github.versus.user.UserAdapter
import com.github.versus.user.VersusUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.CompletableFuture

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
        val view = inflater.inflate(R.layout.chat_users_layout, container, false)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseFirestore.getInstance().collection("user")

        userList = ArrayList()
        adapter = UserAdapter(requireContext(), userList)

        userRecyclerView = view.findViewById(R.id.usersRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        userRecyclerView.adapter = adapter

        // getting the current user from the database
        //TO DO: this next line has to change
        val currUserUID = VersusUser.computeUID("jane.doe@versus.ch")

        val fman = FsUserManager(FirebaseFirestore.getInstance())
        val future = fman.fetch(currUserUID) as CompletableFuture<User>
        future.thenAccept { currUser  ->
            val currUserV = currUser as VersusUser
            currUserV.friends.forEach { uid ->
                val userFuture = fman.fetch(uid) as CompletableFuture<User>
                userFuture.thenAccept{
                    friend ->
                    userList.add(friend as VersusUser)
                    adapter.notifyDataSetChanged()
                }
            }
        }



        return view
    }
}