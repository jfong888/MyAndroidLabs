package fongjason.lab09;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fongjason.lab09.databinding.ActivityChatRoomBinding;
import fongjason.lab09.databinding.DetailsLayoutBinding;
import fongjason.lab09.databinding.ReceiveMessageBinding;
import fongjason.lab09.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    private RecyclerView.Adapter<MyRowHolder2> myAdapter;
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDAO;
    TextView messageText;
    Integer position;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() )
        {
            case R.id.item_1:
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
                break;
            case R.id.item_2:
                  Toast toast = Toast.makeText(getApplicationContext(), "Version 1.0 created by Jason Fong", Toast.LENGTH_LONG);
                  toast.show();
                break;
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        Toolbar toolbar = binding.toolbar;

        setSupportActionBar(toolbar);

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

        chatModel.selectedMessage.observe(this, (newValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newValue);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, chatFragment).commit();
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder2>() {
            @NonNull
            @Override
            public MyRowHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder2(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder2(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder2 holder, int pos) {
                ChatMessage obj = messages.get(pos);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int pos) {
                ChatMessage msg = messages.get(pos);
                if (msg.getisSentBtn() == true) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder2 extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder2(@NonNull View itemView2) {
            super(itemView2);

            itemView2.setOnClickListener( click -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);
                chatModel.selectedMessage.postValue(selected);

            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

    }

}





