package com.github.versus;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log
import android.view.MenuItem
import android.widget.EditText;
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.versus.chats.Chat
import com.github.versus.chats.Message
import com.github.versus.chats.MessageAdapter
import com.github.versus.db.FsChatManager
import com.github.versus.posts.Timestamp
import com.github.versus.user.DummyUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Month

class UserChatActivity : AppCompatActivity(){

    private lateinit var chatRecyclerView : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);


        val name = intent.getStringExtra("UserToChatName")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


/*
      //adding the message to the database
      val chatmanager = FsChatManager(FirebaseFirestore.getInstance())
    /*  chatId = ""
      when {
          receiverUid == null && senderUid == null -> println("receiverUid is null")
          receiverUid == null -> println("First string is null")
          senderUid == null -> println("Second string is null")
          receiverUid <= senderUid -> chatId = receiverUid+"-"+senderUid
          receiverUid > senderUid -> chatId = senderUid+"-"+receiverUid
      }
     */
 */
        val u1 = DummyUser(senderUid)
        val u2 =  DummyUser(receiverUid)

        val chat = Chat(DummyUser(senderUid), DummyUser(receiverUid), "idddd")


        messageList.add(Message(u1, u2, "Yo boii what's up", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))
        messageList.add(Message(u2, u1, "hey man", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))
        messageList.add(Message(u1, u2, "game tomorrow, you down ?", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))
        messageList.add(Message(u2, u1, "of couuuuurse man you know me ", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))
        messageList.add(Message(u1, u2, "All right see you at 9, usual place", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))
        messageList.add(Message(u2, u1, "bet", Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)))


        sendButton.setOnClickListener(){
            val message = Message(  DummyUser(senderUid), DummyUser(receiverUid) , messageBox.text.toString(), Timestamp(2023, Month.MAY, 14, 9, 30, Timestamp.Meridiem.AM ))
            messageList.add(message)
            messageAdapter.notifyDataSetChanged()
            messageBox.setText("")

        }
          val toolbar = findViewById<Toolbar>(R.id.toolbar)
          setSupportActionBar(toolbar)
          supportActionBar?.title = name
          supportActionBar?.setDisplayHomeAsUpEnabled(true)

            toolbar.setNavigationOnClickListener {
            // start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}






