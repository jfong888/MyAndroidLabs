package fongjason.lab01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

import fongjason.lab01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected String cityName;
    protected RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.forecastbutton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                                + URLEncoder.encode(cityName,"utf-8")
                                + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null, (response) -> {}, (error) -> {} );
            queue.add(request);

        });
    }
}
