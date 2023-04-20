package chats;
import com.github.versus.posts.Post;
import com.github.versus.user.DummyUser;
import com.github.versus.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {

    private String chatId;
    private DummyUser user1;
    private DummyUser user2;
    private List<Message> messages;


    public Chat(DummyUser user1, DummyUser user2, String chatId) {
        this.chatId = chatId;
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<>();
    }
    public Chat(){
        this(null, null, "");
    }

    public DummyUser getUser1() {
        return user1;
    }

    public DummyUser getUser2() {
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