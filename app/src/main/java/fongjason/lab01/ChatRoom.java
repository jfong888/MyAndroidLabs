package fongjason.lab01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fongjason.lab01.databinding.ActivityChatRoomBinding;
import fongjason.lab01.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    Context context;
    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        binding.send.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add(binding.message.getText().toString(),m);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.message.setText("");

        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0){
                    View view = LayoutInflater.from(context).inflate(R.layout.sent_message,parent,false);
                    return new SenderViewHolder(view);
                } else {
                    View view= LayoutInflater.from(context).inflate(R.layout.receive_message,parent,false);
                    return new RecieverViewHolder(view);
                }

            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ChatMessage message = messages.get(position);
                if (holder.getClass()==SenderViewHolder.class){
                    SenderViewHolder viewHolder=(SenderViewHolder)holder;
                    viewHolder.textViewmessaage.setText(message.getMessage());
                    viewHolder.timeofmessage.setText(message.getTimeSent());
                } else {
                    RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
                    viewHolder.textViewmessaage.setText(message.getMessage());
                    viewHolder.timeofmessage.setText(message.getTimeSent());
                }

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                return 0;
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;
        TextView timeofmessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.message);
            timeofmessage=itemView.findViewById(R.id.time);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;
        TextView timeofmessage;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.message);
            timeofmessage=itemView.findViewById(R.id.time);
        }
    }
}