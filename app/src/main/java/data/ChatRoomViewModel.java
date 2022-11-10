package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fongjason.lab01.ChatMessage;
import fongjason.lab01.ChatRoom;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData< >();

}
