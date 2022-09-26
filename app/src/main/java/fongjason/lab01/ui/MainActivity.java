package fongjason.lab01.ui;

import android.content.Context;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
            variableBinding.textview.setText("您的编辑文本有：" + s);
        });

        model.isSelected.observe(this, selected -> {
            variableBinding.checkbox1.setChecked(selected);
            variableBinding.checkbox2.setChecked(selected);
            variableBinding.radiobutton1.setChecked(selected);
            variableBinding.radiobutton2.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            variableBinding.switch2.setChecked(selected);
        });

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radiobutton1);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radiobutton2);
        Switch aSwitch1 = (Switch) findViewById(R.id.switch1);
        Switch aSwitch2 = (Switch) findViewById(R.id.switch2);

        checkBox1.setOnCheckedChangeListener((checkbox1, isSelected) -> {
            if(checkBox1.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "Yes, I do drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = MainActivity.this;
                CharSequence text = "option deselected :(";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        });
        checkBox2.setOnCheckedChangeListener((checkbox2, isSelected) -> {
            if(checkBox2.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "No, I do not drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = MainActivity.this;
                CharSequence text = "option deselected :(";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        });
        radiogroup.setOnCheckedChangeListener((radiobutton1, isSelected) -> {
            if(radioButton1.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "Yes, I do drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if(radioButton2.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "No, I do not drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = MainActivity.this;
                CharSequence text = "option deselected :(";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        aSwitch1.setOnCheckedChangeListener((switch1, isSelected) -> {
            if(aSwitch1.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "Yes, I do drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = MainActivity.this;
                CharSequence text = "Option deselected :(";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        aSwitch2.setOnCheckedChangeListener((switch2, isSelected) -> {
            if(aSwitch2.isChecked()) {
                Context context = MainActivity.this;
                CharSequence text = "No, I do not drink Coffee";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = MainActivity.this;
                CharSequence text = "Option deselected :(";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        variableBinding.myimagebutton.setOnClickListener(click -> {
            ImageButton imageButton = (ImageButton)findViewById(R.id.myimagebutton);
            int width = imageButton.getDrawable().getIntrinsicWidth();
            int height = imageButton.getDrawable().getIntrinsicHeight();

            Context context = MainActivity.this;
            CharSequence text = "The width = " + width + " and height = " + height;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

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