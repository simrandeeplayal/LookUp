package com.example.simrandeepsingh.lookup;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpsConnection;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.StringTokenizer;

import Util.Utils;
import data.CityPreference;
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

        CityPreference cityPreference=new CityPreference(MainActivity.this);

        renderWeatherData(cityPreference.getCity());

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


   private void showInputDialog(){
       AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
       builder.setTitle("Change City");

       final EditText cityInput=new EditText(MainActivity.this);
       cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
       cityInput.setHint("Portland,US");
       builder.setView(cityInput);
       builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

           @Override
           public void onClick(DialogInterface dialog, int which) {
               CityPreference cityPreference=new CityPreference(MainActivity.this);
               cityPreference.setCity(cityInput.getText().toString());
               String newCity=cityPreference.getCity();
               renderWeatherData(newCity);
           }
       });
       builder.show();
   }



       public boolean onOptionsItemSelected(MenuItem item){

           int id=item.getItemId();

           if(id == R.id.action_settings){
               showInputDialog();
           }
           return super.onOptionsItemSelected(item);
}

    public void renderWeatherData(String city)
    {

        WeatherTask weatherTask=new WeatherTask();
        weatherTask.execute(new String[]{city + "&APPID="+ "ce9c638895e50499cc96b6dc79c90034" + "&units=metric"});
    }



   /* private class DownloadImageAsyncTask extends AsyncTask<String ,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }



        @Override
        protected void onPostExecute(Bitmap bitmap){
            iconView.setImageBitmap(bitmap);

        }

        private  Bitmap downloadImage(String code){
             final DefaultHttpClient client=new DefaultHttpClient();
            final HttpGet getRequest=new HttpGet(Utils.ICON_URL+code+".png");

            try{
                HttpResponse response=client.execute(getRequest);
                final int statusCode=response.getStatusLine().getStatusCode();
                if(statusCode != HttpStatus.SC_OK)
                {
                    Log.e("DownloadImage","Error:" + statusCode);
                    return null;
                }
                final HttpEntity entity=response.getEntity();
                if (entity !=null)
                {

                    InputStream inputStream=null;
                    inputStream=entity.getContent();
                     final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    return bitmap;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
*/


    private class WeatherTask extends AsyncTask<String,Void, Weather>{

        @Override
        protected Weather doInBackground(String... params) {
            String data=( (new WeatherHttpClient()).getWeatherData(params[0]));
            weather.iconData=weather.currentCondition.getIcon();
            weather = JSONWeatherParser.getWeather(data);

           // new DownloadImageAsyncTask().execute(weather.iconData);


            return weather;
            }




        @Override
        protected void onPostExecute(Weather weather){
            super.onPostExecute(weather);

            DateFormat df=DateFormat.getTimeInstance();
            String sunriseDate=df.format(new Date(weather.place.getSunrise()));
            String sunsetDAte=df.format(new Date(weather.place.getSunset()));
            String updateDate=df.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat=new DecimalFormat("#.#");
            String tempFormat=decimalFormat.format(weather.currentCondition.getTemp());

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText(""+ tempFormat+"°C");
            humidity.setText("Humidity:" + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure:" + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind:" + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise:" + sunriseDate);
            sunset.setText("Sunset:" + sunsetDAte);
            updated.setText("Last Updated" + updateDate);
            description.setText("Condition" + weather.currentCondition.getCondition() + "(" +
             weather.currentCondition.getDescription() + ")");



        }
    }


}
