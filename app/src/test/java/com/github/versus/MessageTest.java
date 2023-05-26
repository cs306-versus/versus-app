package com.github.versus;

import com.github.versus.chats.Message;
import com.github.versus.posts.Timestamp;

import org.junit.Assert;
import org.junit.Test;

import java.time.Month;
import java.util.Calendar;

public class MessageTest {

    @Test
    public void ConstructorYieldsNonNulInstance(){
        Assert.assertNotNull(new MessageTest());
    }
    @Test
    public void ToStringYieldsCorrectResults(){
        String From= "Adam Lkharita";
        String To= "Abdess Piquant";
        String Body= "I implemented google Maps";
        Timestamp ts= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Message message = new Message(From,To, Body,ts);
        String expected= "From: " +From+"\n"+"To: "+To+"\n"+"Date: "+ts.toString()+"\n"+"Body: "+Body;
        Assert.assertEquals(expected,message.toString());
    }
    @Test
    public void EqualityWithSelfHolds(){
        Message message= new Message();
        Assert.assertEquals(message,message);
    }
    @Test
    public void EqualityWithOtherObjectFails(){
        Assert.assertNotEquals(new Message(),new Object());
    }

    @Test
    public void EqualitySimilarObjectWorks(){
        String From= "Adam Lkharita";
        String To= "Abdess Piquant";
        String Body= "I implemented google Maps";
        Timestamp ts= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Message message = new Message(From,To, Body,ts);
        Message similar= new Message(From,To,Body,ts);
        Assert.assertEquals(message,similar);
    }
}
