package fongjason.lab01;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage{

    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="SendOrReceive")
    protected boolean isSentBtn;

    @ColumnInfo(name="Time")
    protected String time;

    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name="id")
    public int id;

    public ChatMessage(){    }

    public ChatMessage(String msg, String ts, boolean isSent) {
        message = msg;
        time = ts;
        isSentBtn = isSent;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean getIsSent(){
        return isSentBtn;
   }
}

//import android.content.Context;
//
//public class ChatMessage {
//
//    String message;
//    String timeSent;
//    boolean isSentButton;
//    Context applicationContext;
//
//    ChatMessage(String message, String timeSent, boolean isSentButton){
//        this.message = message;
//        this.timeSent = timeSent;
//        this.isSentButton = isSentButton;
//    }
//
//    public ChatMessage(Context applicationContext, String message, String timeSent, Boolean isSentButton) {
//        this.applicationContext = applicationContext;
//        this.message = message;
//        this.timeSent = timeSent;
//        this.isSentButton = isSentButton;
//    }
//    public Context getContext(){
//
//        return applicationContext;
//
//    }
//    public String getMessage(){
//
//        return message;
//
//    }
//
//    public String getTimeSent(){
//
//        return timeSent;
//    }
//
//    public boolean getIsSentButton(){
//
//        return isSentButton;
//    }
//
//}
