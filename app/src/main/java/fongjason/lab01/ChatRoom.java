package fongjason.lab01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fongjason.lab01.databinding.ActivityChatRoomBinding;
import fongjason.lab01.databinding.SentMessageBinding;
import fongjason.lab01.databinding.ReceiveMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages;
    Context context;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    private Object ChatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();


        if(messages == null)
        {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        binding.send.setOnClickListener(click -> {

            String msg = binding.message.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage = new ChatMessage(getApplicationContext(), new ChatMessage(msg, currentDateandTime,true).getMessage(), new ChatMessage(msg, currentDateandTime,true).getTimeSent(), new ChatMessage(msg, currentDateandTime,true).getIsSentButton() );
            chatModel.messages.getValue().add((fongjason.lab01.ChatMessage) ChatMessage);
            myAdapter.notifyItemInserted( messages.size() - 1 );
            //clear the previous text:
            binding.message.setText("");
        });

        binding.receive.setOnClickListener(click -> {

            String msg = binding.message.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm:ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage = new ChatMessage(getApplicationContext(), new ChatMessage(msg, currentDateandTime,false).getMessage(), new ChatMessage(msg, currentDateandTime,true).getTimeSent(), new ChatMessage(msg, currentDateandTime,true).getIsSentButton() );
            chatModel.messages.getValue().add((fongjason.lab01.ChatMessage) ChatMessage);
            myAdapter.notifyItemInserted( messages.size() - 1 );
            //clear the previous text:
            binding.message.setText("");
        });

        //Set a layout manager for the rows to be aligned vertically using only 1 column.
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
//                return new MyRowHolder(  binding.getRoot() );
                if (viewType == 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, true);
                    return new MyRowHolder(view);
                } else {
                    View view = LayoutInflater.from(context).inflate(R.layout.receive_message, parent, true);
                    return new MyRowHolder(view);
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage msg2 = messages.get(position);
                holder.messageText.setText(msg2.getMessage());
                holder.timeText.setText(msg2.getTimeSent());
            }

            @Override
            public int getItemCount() {

                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage msg3 = messages.get(position);
               if (msg3.getIsSentButton() == true){
                   return 0;
               } else {
                   return 1;
               }
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}