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
import fongjason.lab01.databinding.ReceiveMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<String> messages;

    private RecyclerView.Adapter<SenderViewHolder> myAdapter;

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

            chatModel.messages.getValue().add(binding.message.getText().toString());
            myAdapter.notifyItemInserted( messages.size()-1 );
            //clear the previous text:
            binding.message.setText("");
        });

        //Set a layout manager for the rows to be aligned vertically using only 1 column.
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<SenderViewHolder>() {
            @NonNull
            @Override
            public SenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new SenderViewHolder(  binding.getRoot() );
            }

            @Override
            public void onBindViewHolder(@NonNull SenderViewHolder holder, int position) {

                   SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm:ss a");
                   String currentDateandTime = sdf.format(new Date());
                   holder.textViewmessaage.setText(messages.get(position));
                   holder.timeofmessage.setText(currentDateandTime);

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });

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