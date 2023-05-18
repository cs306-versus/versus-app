package com.github.versus;

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.versus.chats.Chat
import com.github.versus.chats.Message
import com.github.versus.chats.MessageAdapter
import com.github.versus.posts.Timestamp
import com.github.versus.user.DummyUser
import com.google.firebase.auth.FirebaseAuth
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
            val itemCount: Int = messageAdapter.itemCount
            chatRecyclerView.smoothScrollToPosition(itemCount - 1)
        }
        backButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private val rect = Rect()

            override fun onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(rect)
                val screenHeight = decorView.height
                val keypadHeight = screenHeight - rect.bottom

                if (keypadHeight > screenHeight * 0.15) {
                    // Keyboard is visible, scroll to the last item
                    val lastItemPosition = messageAdapter.itemCount - 1
                    chatRecyclerView.smoothScrollToPosition(lastItemPosition)
                }
            }
        })
    }

}






