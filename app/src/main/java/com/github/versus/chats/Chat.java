package com.github.versus.chats;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class used to model a Chat in the app
 */
public class Chat {

    private String chatId;
    private String user1Uid;
    private String user2Uid;
    private List<Message> messages;

    public static String computeChatId(String u1, String u2){
        return u1.compareTo(u2) >= 0 ? u1+u2 : u2 + u1 ;
    }

    /**
     * main constructor of a Chat class
     * @param user1Uid  first user contributing to the chat
     * @param user2Uid second user contributing to the chat
     * @param chatId the unique id used to identify the chat
     */
    public Chat(String user1Uid, String user2Uid, String chatId) {
        this.chatId = chatId;
        this.user1Uid = user1Uid;
        this.user2Uid= user2Uid;
        this.messages = new ArrayList<>();
    }
    public Chat(){
        this(null, null, "");
    }

    /**
     *
     * @return first user contributing to the chat
     */
    public String getUser1Uid() {
        return user1Uid;
    }
    /**
     *
     * @return second user contributing to the chat
     */
    public String getUser2Uid() {
        return user2Uid;
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
        res.put("user1Uid", getUser1Uid());
        res.put("user2Uid", getUser2Uid());
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
                this.getUser1Uid().equals(other.getUser1Uid())&&
                this.getUser2Uid().equals(other.getUser2Uid())&&
                this.getMessages().equals(other.getMessages());
    }
}