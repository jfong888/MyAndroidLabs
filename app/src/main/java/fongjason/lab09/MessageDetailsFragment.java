package fongjason.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import fongjason.lab09.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment{

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m){

        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.Message.setText(selected.message);
        binding.Time.setText(selected.timeSent);

        if (selected.isSentBtn == true){
            String sent = "Message sent By Jennifer";
            binding.SendReceive.setText(sent);
        } else {
            String receive = "Message Received By John Wick";
            binding.SendReceive.setText(receive);
        }

        binding.DatabaseId.setText("ID = " + selected.id);

        return binding.getRoot();

    }

}
