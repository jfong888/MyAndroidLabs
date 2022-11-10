package lab01;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;
    Context applicationContext;

    ChatMessage(String message, String timeSent, boolean isSentButton){
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public ChatMessage(Context applicationContext, String message, String timeSent, Boolean isSentButton) {
        this.applicationContext = applicationContext;
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }
    public Context getContext(){

        return applicationContext;

    }
    public String getMessage(){

        return message;

    }

    public String getTimeSent(){

        return timeSent;
    }

    public boolean getIsSentButton(){

        return isSentButton;
    }

}
