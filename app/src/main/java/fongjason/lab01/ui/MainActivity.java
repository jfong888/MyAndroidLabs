package fongjason.lab01.ui;

import android.content.Context;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.view.View.OnClickListener;
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
        View view = variableBinding.getRoot();
        //setContentView(R.layout.activity_main);
        setContentView(view);

        changeText();

        variableBinding.mybutton.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });


        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has:" + s);
        });

        model.isSelected.observe(this, selected -> {
            variableBinding.checkbox1.setChecked(selected);
            variableBinding.checkbox2.setChecked(selected);
            variableBinding.radiobutton1.setChecked(selected);
            variableBinding.radiobutton2.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            variableBinding.switch2.setChecked(selected);
        });

        //Context context = MainActivity.this;
        //CharSequence text = "The Value is now: " + selected;
        //int duration = Toast.LENGTH_SHORT;

        //Toast toast = Toast.makeText(context, text, duration);
        //toast.show();
    }
        private void changeText() {

            Button mybuton = (Button) findViewById(R.id.mybutton);
            TextView mytext = (TextView) findViewById(R.id.textview);
            EditText myedit = (EditText) findViewById(R.id.myedittext);

            mybuton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String editString = myedit.getText().toString();
                    mytext.setText("Your edit text has:" + editString);
                }
            });
        }
}