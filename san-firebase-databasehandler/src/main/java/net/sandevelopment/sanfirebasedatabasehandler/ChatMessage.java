package net.sandevelopment.sanfirebasedatabasehandler;

public final class ChatMessage extends SanFireDbResult<ChatMessage>{
    private String chatFromUserUid;
    private String chatToUserUid;
    private String chatMessage;
    private String chatFromUserName;
    private String chatToUserName;
    private String chatTime=System.currentTimeMillis()+"";

    public ChatMessage(String chatFromUserUid, String chatToUserUid, String chatMessage, String chatFromUserName, String chatToUserName) {
        this.chatFromUserUid = chatFromUserUid;
        this.chatToUserUid = chatToUserUid;
        this.chatMessage = chatMessage;
        this.chatFromUserName = chatFromUserName;
        this.chatToUserName = chatToUserName;
        //this.chatTime = chatTime;
    }

    public ChatMessage() {
    }

    public String getChatFromUserUid() {
        return chatFromUserUid;
    }

    public void setChatFromUserUid(String chatFromUserUid) {
        this.chatFromUserUid = chatFromUserUid;
    }

    public String getChatToUserUid() {
        return chatToUserUid;
    }

    public void setChatToUserUid(String chatToUserUid) {
        this.chatToUserUid = chatToUserUid;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatFromUserName() {
        return chatFromUserName;
    }

    public void setChatFromUserName(String chatFromUserName) {
        this.chatFromUserName = chatFromUserName;
    }

    public String getChatToUserName() {
        return chatToUserName;
    }

    public void setChatToUserName(String chatToUserName) {
        this.chatToUserName = chatToUserName;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
}
