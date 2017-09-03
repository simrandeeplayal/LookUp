package com.example.simrandeepsingh.lookup;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;

    Weather weather=new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=(TextView) findViewById(R.id.cityText);
        iconView=(ImageView) findViewById(R.id.thumnail);
        temp=(TextView) findViewById(R.id.temptext);
        description=(TextView) findViewById(R.id.cloudtext);
        humidity=(TextView) findViewById(R.id.humidtext);
        pressure=(TextView) findViewById(R.id.pressuretext);
        wind=(TextView) findViewById(R.id.windtext);
        sunrise=(TextView) findViewById(R.id.risetext);
        sunset=(TextView) findViewById(R.id.settext);
        updated=(TextView) findViewById(R.id.updatetext);

        renderWeatherData("Spokane,US");

    }

    public void renderWeatherData(String city)
    {

        WeatherTask weatherTask=new WeatherTask();
        weatherTask.execute(new String[]{city + "&APPID="+ "ce9c638895e50499cc96b6dc79c90034" + "&units=metric"});
    }

    private class WeatherTask extends AsyncTask<String,Void, Weather>{

        @Override
        protected Weather doInBackground(String... params) {
            String data=( (new WeatherHttpClient()).getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(data);
            Log.v("Data:",weather.currentCondition.getDescription());
            return weather;
            }



        @Override
        protected void onPostExecute(Weather weather){
            super.onPostExecute(weather);
            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText(""+ weather.currentCondition.getTemp()+"C");


        }
    }


}
