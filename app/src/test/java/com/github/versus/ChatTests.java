package com.github.versus;

import com.github.versus.chats.Chat;
import com.github.versus.chats.Message;
import com.github.versus.posts.Timestamp;

import org.junit.Assert;
import org.junit.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.List;

public class ChatTests {

    @Test
    public void ConstructorYieldsNonNulInstance() {
        Assert.assertNotNull(new Chat());
    }

    @Test
    public void ChatAddsMessage() {
        String From= "Adam Lkharita";
        String To= "Abdess Piquant";
        String Body= "I implemented google Maps";
        Timestamp ts= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Message message = new Message(From,To, Body,ts);
        Chat chat= new Chat();
        chat.addMessage(message);
        Assert.assertTrue(((List<Message>)chat.getAllAttributes().get("messages")).contains(message));
    }
    @Test
    public void EqualityWithSelfHolds(){
        Message message= new Message();
        Assert.assertEquals(message,message);
    }

    @Test
    public void EqualityWithNonRelatedObjectFails(){
        Message message= new Message();
        Assert.assertNotEquals(message,new Object());
    }
    @Test
    public void CorrectIdIsComputed(){
        String From= "Adam Lkharita";
        String To= "Abdess Piquant";
        Assert.assertEquals(From+To,Chat.computeChatId(From,To));
            }

    @Test
    public void CorrectIdIsComputed2(){
        String From= "Adam Lkharita";
        String To= "Abdess Piquant";
        Assert.assertEquals(From+To,Chat.computeChatId(To,From));
    }

}