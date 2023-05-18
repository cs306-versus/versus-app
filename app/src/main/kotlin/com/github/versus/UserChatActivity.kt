package com.github.versus;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText;
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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

    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter

    //top of the layout
    private lateinit var textName : TextView
    private lateinit var infoButton : ImageView
    private lateinit var backButton : ImageView

    //middle of the layout
    private lateinit var chatRecyclerView : RecyclerView

    //bottom of the layout
    private lateinit var messageBox : TextView
    private lateinit var sendButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        //getting info on the user we are currently messaging
        val name = intent.getStringExtra("UserToChatName")
        val receiverUid = intent.getStringExtra("uid")
        //getting info on the current user
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        //initializing field
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.inputMessage)
        //initializing the buttons
        sendButton = findViewById(R.id.imageSend)
        infoButton = findViewById(R.id.imageInfo)
        backButton = findViewById(R.id.imageBack)
        //-------------------------
        messageList = ArrayList()
        textName = findViewById(R.id.textName)
        messageAdapter = MessageAdapter(this, messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //setting the name of the user we chat with
        textName.text = name

        //------------------------------------------------------------------------
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

        //------------------------------------------------------------------------

        //handling the sending of messages on button click
        sendButton.setOnClickListener(){
            val message = Message(  u1, u2 , messageBox.text.toString(), Timestamp(2023, Month.MAY, 14, 9, 30, Timestamp.Meridiem.AM ))
            messageList.add(message)
            messageAdapter.notifyDataSetChanged()
            messageBox.text = ""
        }
        backButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

}






