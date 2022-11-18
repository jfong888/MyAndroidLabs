package fongjason.lab01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fongjason.lab01.databinding.ActivityChatRoomBinding;
import fongjason.lab01.databinding.ReceiveMessageBinding;
import fongjason.lab01.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<ChatMessage> messages;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    ChatRoomViewModel chatModel;

    ChatMessageDAO mDAO;

    FragmentManager fMgr = getSupportFragmentManager();
    FragmentTransaction tx = fMgr.beginTransaction();

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


        if(messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<>());

            //load everything:
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                //whatever is in here runs on another processor.

                messages.addAll( mDAO.getAllMessages() );
                //now you can load the RecyclerVIew:

                runOnUiThread(() -> binding.recycleView.setAdapter( myAdapter ) );

            });
        }

        binding.send.setOnClickListener(click -> {
            // get message typed
            String input = binding.message.getText().toString();

            // get current date
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(input, currentDateandTime, true);
            messages.add(cm);

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.message.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                mDAO.insertMessage(cm);
            });
        });

        binding.receive.setOnClickListener(click -> {
            // get message typed
            String input = binding.message.getText().toString();

            // get current date
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(input, currentDateandTime, false);
            messages.add(cm);

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.message.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                mDAO.insertMessage(cm);
            });
        });

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {

        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
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
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage msg = messages.get(position);
                if (msg.getisSentBtn() == true) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
              int position = getAbsoluteAdapterPosition();
              ChatMessage selected = messages.get(position);
              chatModel.selectedMessage.postValue(selected);

            //which row was click
           /* int position = getAdapterPosition();
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
            builder.create().show();*/
        });

        messageText = itemView.findViewById(R.id.message);
        timeText = itemView.findViewById(R.id.time);
        }
    }
}


