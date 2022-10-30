package fongjason.lab01;

public class ChatMessage {
    String message;
    String timeSent;
    boolean isSentButton;

    ChatMessage(String message, String timeSent, boolean isSentButton){
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
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
    public boolean getIsSentButton(){

        return isSentButton;
    }
    public void setIsSentButton(boolean isSentButton){

        this.isSentButton = isSentButton;
    }
}
