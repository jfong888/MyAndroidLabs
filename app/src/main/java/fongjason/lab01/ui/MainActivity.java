package fongjason.lab01.ui;

import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fongjason.lab01.R;
import fongjason.lab01.data.MainViewModel;
import fongjason.lab01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        //setContentView(variableBinding.getRoot());

        changeText();

        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //model.editString.observe(this, s -> {
                   // variableBinding.textview.setText("Your edit text has:" + s);
                //});
            }
        });
    }
        private void changeText() {

            Button mybuton = (Button) findViewById(R.id.mybutton);
            TextView mytext = (TextView) findViewById(R.id.textview);
            EditText myedit = (EditText) findViewById(R.id.myedittext);

            mybuton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editString = myedit.getText().toString();
                    mytext.setText("Your edit text has:" + editString);
                }
            });
        }
}