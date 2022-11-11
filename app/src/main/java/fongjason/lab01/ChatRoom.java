package fongjason.lab01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.ChatRoomViewModel;
import fongjason.lab01.databinding.ActivityChatRoomBinding;
import fongjason.lab01.databinding.ReceiveMessageBinding;
import fongjason.lab01.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages;

    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages = chatModel.messages.getValue();

        //load from the database:
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        mDAO = db.cmDAO();

        if(messages == null)
        {
            chatModel.messages.postValue(messages = new ArrayList<>());

            //load everything:
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                //whatever is in here runs on another processor.

                messages.addAll( mDAO.getAllMessages() );
                //now you can load the RecyclerVIew:

                runOnUiThread(() -> binding.recycleView.setAdapter( myAdapter ) );

            }  );
        }

        binding.send.setOnClickListener(click -> {
            String msg = binding.message.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(msg,currentDateandTime,true);
            chatModel.messages.getValue().add(newMessage);

            myAdapter.notifyItemInserted( messages.size()-1 );
            //clear the previous text:
            binding.message.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                mDAO.insertMessage(newMessage);
            });
        });

        binding.receive.setOnClickListener(click -> {
            String msg2 = binding.message.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, yyyy-MMM-dd hh:mm a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(msg2,currentDateandTime,false);
            chatModel.messages.getValue().add(newMessage);

            myAdapter.notifyItemInserted( messages.size()-1 );
            //clear the previous text:
            binding.message.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                mDAO.insertMessage(newMessage);
            });
        });

        //Set a layout manager for the rows to be aligned vertically using only 1 column.
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                   return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                   return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage msg2 = messages.get(position);
                holder.messageText.setText(msg2.getMessage());
                holder.timeText.setText(msg2.getTime());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage msg3 = messages.get(position);

                if (msg3.getIsSent() == true){
                    return 0;
                } else {
                    return 1;
                }
            }
        };
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{

                //which row was click
                int position = getAdapterPosition();
                ChatMessage thisMessage = messages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage(  thisMessage.message );

                builder.setTitle("Do you want to delete this? ");

                builder.setNegativeButton("No", (a, b)->{   });
                builder.setPositiveButton("Yes", (a, b)->{

                    Snackbar.make( messageText, "You deleted position #" + position, Snackbar.LENGTH_INDEFINITE)
                            .setAction( "Undo", click1-> {

                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute( () -> {
                                    mDAO.insertMessage(thisMessage);
                                });
                                chatModel.messages.getValue().add(thisMessage);
                                myAdapter.notifyItemInserted( position );

                            } )  .show();

                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute( () -> {
                        mDAO.deleteMessage(thisMessage);
                    });

                    myAdapter.notifyItemRemoved( position );
                    chatModel.messages.getValue().remove(position);

                });
                builder.create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }


}


