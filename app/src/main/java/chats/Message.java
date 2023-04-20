package chats;

import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.user.User;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private final User sender;
    private final User recipient;
    private final String body;
    private final Timestamp timestamp;

    // Constructor
    public Message(User sender, User recipient, String body, Timestamp timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public User getSender() {
        return sender;
    }
    public User getRecipient() {
        return recipient;
    }


    public String getBody() {
        return body;
    }


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