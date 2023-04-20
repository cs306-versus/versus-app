package chats;
import com.github.versus.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {

    private String chatId;
    private User user1;
    private User user2;
    private List<Message> messages;


    public Chat(User user1, User user2, String chatId) {
        this.chatId = chatId;
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<>();

    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public String getChatId() {
        return chatId;
    }

    // Add message to chat
    public void addMessage(Message message) {
        messages.add(message);
    }

    public Map<String, Object>getAllAttributes(){
        Map<String, Object> res =  new HashMap<String, Object>();
        res.put("user1", user1);
        res.put("user2", user2);
        res.put("messages", messages);
        res.put("chatId", chatId);
        return res;
    }
}