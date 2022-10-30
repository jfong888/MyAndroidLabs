package fongjason.lab01;

public class ChatMessage {
    String message;
    String timeSent;
    boolean isSentButton;

    ChatMessage(String m,String t, boolean sent){
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getTimeSent(){
        return timeSent;
    }
    public void setTimeSent(String timeSent){
        this.timeSent = timeSent;
    }
    public boolean getisSentButton(){
        return isSentButton;
    }
    public void setisSentButton(boolean isSentButton){
        this.isSentButton = isSentButton;
    }
}
