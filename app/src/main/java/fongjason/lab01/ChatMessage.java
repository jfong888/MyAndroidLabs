package fongjason.lab01;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage{

    @PrimaryKey (autoGenerate=true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="MessageColumn")
    public String message;

    public void setMessage(String s){message = s;}
    public String getMessage() {
        return message;
    }

    @ColumnInfo(name="TimeColumn")
    public String time;

    public void setTime(String t){time = t;}
    public String getTime() {
        return time;
    }

    @ColumnInfo(name="isSentColumn")
    public boolean isSent;

    public void setIsSent(boolean sent){isSent = sent;}
    public boolean getIsSent() {
        return isSent;
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
