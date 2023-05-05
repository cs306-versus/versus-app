package com.github.versus.chats;
import com.github.versus.user.DummyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class used to model a Chat in the app
 */
public class Chat {

    private String chatId;
    private DummyUser user1;
    private DummyUser user2;
    private List<Message> messages;

    /**
     * main constructor of a Chat class
     * @param user1  first user contributing to the chat
     * @param user2 second user contributing to the chat
     * @param chatId the unique id used to identify the chat
     */
    public Chat(DummyUser user1, DummyUser user2, String chatId) {
        this.chatId = chatId;
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<>();
    }
    public Chat(){
        this(null, null, "");
    }

    /**
     *
     * @return first user contributing to the chat
     */
    public DummyUser getUser1() {
        return user1;
    }
    /**
     *
     * @return second user contributing to the chat
     */
    public DummyUser getUser2() {
        return user2;
    }
    /**
     *
     * @return caht messages
     */
    public List<Message> getMessages() {
        return messages;
    }
     /**
     *
     * @return chatId of teh chat
     */
    public String getChatId() {
        return chatId;
    }

    /**
     * adds the message given as argument to the chat
     * @param message
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     *
     * @return all attributes of the chat in map "fieldName -> Value" format
     */
    public Map<String, Object>getAllAttributes(){
        Map<String, Object> res =  new HashMap<String, Object>();
        res.put("user1", getUser1());
        res.put("user2", getUser2());
        res.put("messages", getMessages());
        res.put("chatId", getChatId());
        return res;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Chat)) {
            return false;
        }

        Chat other = (Chat) obj;
        return this.getChatId().equals(other.getChatId()) &&
                this.getUser1().getUID().equals(other.getUser1().getUID())&&
                this.getUser2().getUID().equals(other.getUser2().getUID())&&
                this.getMessages().equals(other.getMessages());
    }
}