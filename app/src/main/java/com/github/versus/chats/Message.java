package com.github.versus.chats;

import com.github.versus.posts.Timestamp;
import com.github.versus.user.DummyUser;
import com.github.versus.user.User;

/**
 * class used to model a message in the app
 */
public class Message {

    private final DummyUser sender;
    private final DummyUser recipient;
    private final String body;
    private final Timestamp timestamp;

    /**
     * main constructor for the message class
     * @param sender user sending the messsage
     * @param recipient recipient of the messsage
     * @param body body of the messsage
     * @param timestamp time of the messsage
     */
    public Message(DummyUser sender, DummyUser recipient, String body, Timestamp timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timestamp = timestamp;
    }
    public Message() {
        this(null, null, null, null);
    }

    /**
     *
     * @return the sender of the message
     */
    public User getSender() {
        return sender;
    }
    /**
     *
     * @return the recipient of the message
     */
    public User getRecipient() {
        return recipient;
    }

    /**
     *
     * @return the body of the message
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @return the timestamp of the message
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    // toString method
    @Override
    public String toString() {
        return "From: " + sender + "\n" +
                "To: " + recipient + "\n" +
                "Date: " + timestamp.toString() + "\n" +
                "Body: " + body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Message)) {
            return false;
        }

        Message other = (Message) obj;
        return this.getSender().getUID().equals(other.getSender().getUID())&&
                this.getRecipient().getUID().equals(other.getRecipient().getUID())&&
                this.getBody().equals(other.getBody())&&
                this.getTimestamp().equals(other.getTimestamp());
    }
}