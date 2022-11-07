package fongjason.lab01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fongjason.lab01.databinding.ActivityChatRoomBinding;
import fongjason.lab01.databinding.ReceiveMessageBinding;
import fongjason.lab01.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages;
    private RecyclerView.Adapter<SenderViewHolder> myAdapter;
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
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage = new ChatMessage(getApplicationContext(), new ChatMessage(msg, currentDateandTime,true).getMessage(), new ChatMessage(msg, currentDateandTime,true).getTimeSent(), new ChatMessage(msg, currentDateandTime,true).getIsSentButton() );
            chatModel.messages.getValue().add((fongjason.lab01.ChatMessage) ChatMessage);
            myAdapter.notifyItemInserted( messages.size() - 1 );
            //clear the previous text:
            binding.message.setText("");
        });

        binding.receive.setOnClickListener(click -> {

            String msg = binding.message.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage = new ChatMessage(getApplicationContext(), new ChatMessage(msg, currentDateandTime,false).getMessage(), new ChatMessage(msg, currentDateandTime,false).getTimeSent(), new ChatMessage(msg, currentDateandTime,false).getIsSentButton() );
            chatModel.messages.getValue().add((fongjason.lab01.ChatMessage) ChatMessage);
            myAdapter.notifyItemInserted( messages.size() - 1 );
            //clear the previous text:
            binding.message.setText("");
        });

        //Set a layout manager for the rows to be aligned vertically using only 1 column.
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<SenderViewHolder>() {
            @NonNull
            @Override
            public SenderViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                  View view = null;
                  if (viewType == 0) {
                      SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
//                      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                      return new SenderViewHolder(binding.getRoot());
                  } else {
                      ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
//                      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message, parent, false);
                      return new SenderViewHolder(binding.getRoot());
                  }
            }

            @Override
            public void onBindViewHolder(@NonNull SenderViewHolder  holder, int position) {
                ChatMessage msg2 = messages.get(position);
                holder.messageText.setText(msg2.getMessage());
                holder.timeText.setText(msg2.getTimeSent());
//                int type = getItemViewType(position);
//                if  (type == 0){
//                    ((SenderViewHolder)holder).bind(position);
//                } else {
//                    ((SenderViewHolder)holder).bind(position);
//                }

            }

            @Override
            public int getItemCount() {

                return messages.size();

            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage msg3 = messages.get(position);

               if (msg3.isSentButton == true){
                   return 0;
               } else {
                   return 1;
               }
            }
        });
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText=itemView.findViewById(R.id.message);
            timeText=itemView.findViewById(R.id.time);
        }
//        public void bind (int position){
//            ChatMessage msg2 = messages.get(position);
//            messageText.setText(msg2.getMessage());
//            timeText.setText(msg2.getTimeSent());
//        }
    }

//    class RecieverViewHolder extends RecyclerView.ViewHolder {
//        TextView messageTextR;
//        TextView timeTextR;
//
//        public RecieverViewHolder(@NonNull View itemView) {
//            super(itemView);
//            messageTextR=itemView.findViewById(R.id.message);
//            timeTextR=itemView.findViewById(R.id.time);
//        }
//        public void bind (int position){
//            ChatMessage msg6 = messages.get(position);
//            messageTextR.setText(msg6.getMessage());
//            timeTextR.setText(msg6.getTimeSent());
//        }
//    }
}