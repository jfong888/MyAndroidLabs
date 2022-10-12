/**
 * @author Jason Fong
 * @version 1.0
 */
package fongjason.lab01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import fongjason.lab01.databinding.ActivityMainBinding;
import java.util.regex.Pattern;

/**
 * This class verifies the passwords entered by user
 * @author Jason Fong
 * @see fongjason.lab01.databinding.ActivityMainBinding
 * @see android.widget.Toast
 * @see androidx.appcompat.app.AppCompatActivity
 * @see android.os.Bundle
 * @see java.util.regex.Pattern
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Holds the ActivityMainBinding, Pattern variable
     */
    private ActivityMainBinding binding;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?])" + ".{10,}" + "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener( clk -> {
            String password = binding.password.getText().toString().trim();
            checkPasswordComplexity(password);
        });
    }

    /**
     * Checks the password complexity that the user enters
     * @param pw The String object that we are checking
     * @return Returns true if ....
     */
    private boolean checkPasswordComplexity(String pw){

        if(pw.isEmpty()){
            binding.title.setText("Field Cannot Be Empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pw).matches()){
            binding.title.setText("You shall not pass!");
            return false;
        } else {
            binding.title.setText("Your password meets the requirements");
            return true;
        }
    }
}
