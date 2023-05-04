package com.github.versus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView

class UserChatActivity : AppCompatActivity(){

    private lateinit var chatRecyclerView : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)


    }

}