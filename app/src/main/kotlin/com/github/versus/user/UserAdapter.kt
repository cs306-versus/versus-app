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
        val textName = itemView.findViewById<TextView>(R.id.textName)
        //val textEmail = itemView.findViewById<TextView>(R.id.textEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_container_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userTemp = userList[position]
        val fullName = userTemp.firstName +" "+ userTemp.lastName
        holder.textName.text = fullName
        //holder.textEmail.text = userTemp.mail
        holder.itemView.setOnClickListener{
            val intent = Intent(context, UserChatActivity::class.java)
            intent.putExtra("UserToChatName", fullName)
            intent.putExtra("uid", userTemp.uid)
            context.startActivity(intent)
        }



    }
}


