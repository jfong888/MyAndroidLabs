package fongjason.lab01;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fongjason.lab01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    protected String cityName;
    protected RequestQueue queue = null;
    String iconName = null;
    ImageRequest imgReq;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());


        binding.forecastbutton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName) + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {

                        try {
                            JSONObject coord = response.getJSONObject( "coord" );
                            iconName = response.getJSONArray("weather").getJSONObject(0).getString("icon");


                            String pathname = getFilesDir() + "/" + iconName + ".png";
                            File file = new File(pathname);
                            if(file.exists())
                            {
                                image = BitmapFactory.decodeFile(pathname);
                            }
                            else {
                                imgReq = new ImageRequest("https://openweathermap.org/img/w/" + iconName + ".png", new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        try {
                                            // Do something with loaded bitmap...
                                            image = bitmap;
                                            image.compress(Bitmap.CompressFormat.PNG, 300, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                        }
                                        catch(Exception e){

                                        }
                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {   });
                            }

                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            String description = position0.getString("description");

                            JSONObject mainObject = response.getJSONObject("main");
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");

                            binding.temp.setText("The current temperature is " + current + " Degree Celsius");
                            binding.temp.setVisibility(View.VISIBLE);

                            binding.maxTemp.setText("The max temperature is " + max + " Degree Celsius");
                            binding.maxTemp.setVisibility(View.VISIBLE);

                            binding.minTemp.setText("The min temperature is " + min + " Degree Celsius");
                            binding.minTemp.setVisibility(View.VISIBLE);

                            binding.humitidy.setText("Humitidy is " + humidity + " % ");
                            binding.humitidy.setVisibility(View.VISIBLE);

                            binding.icon.setImageBitmap(image);
                            binding.icon.setVisibility(View.VISIBLE);

                            binding.description.setText(description);
                            binding.description.setVisibility(View.VISIBLE);

                            queue.add(imgReq);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    },
                    (error) -> {
                        Log.e("Error", "error");
                    } );
            queue.add(request);
        });
    }
}