package fongjason.lab01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import fongjason.lab01.databinding.DetailsLayoutBinding;

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
        binding.DatabaseId.setText("ID = " + selected.id);

        return binding.getRoot();
    }
}
