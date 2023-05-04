package com.github.versus.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.versus.R
import com.github.versus.UserChatActivity

class UserAdapter(val context : Context, val userList : ArrayList<VersusUser>):
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        val fullName = currentUser.firstName +" "+ currentUser.lastName
        holder.textName.text = fullName
        holder.itemView.setOnClickListener{
            val intent = Intent(context, UserChatActivity::class.java)
            intent.putExtra("name", currentUser.firstName)

            context.startActivity(intent)
        }


    }
}


