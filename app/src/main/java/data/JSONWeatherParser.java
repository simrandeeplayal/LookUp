package data;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import model.Place;
import model.Weather;

/**
 * Created by simrandeepsingh on 02/09/17.
 */

public class JSONWeatherParser {

    public static Weather getWeather(String data){
        Weather weather=new Weather();

        //create JSONobject from data

        try {
            JSONObject jsonObject=new JSONObject(data);

            Place place=new Place();

            JSONObject coorObj= Utils.getObject("coord",jsonObject);

            place.setLat(Utils.getFloat("lat",coorObj));

            place.setLon(Utils.getFloat("lon",coorObj));

            //Get the sys object

            JSONObject sysObj= Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place=place;

            //Get the weather info

            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject jsonWeather=jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherID(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject mainobj=Utils.getObject("main",jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity",mainobj));
            weather.currentCondition.setPressure(Utils.getInt("pressure",mainobj));
            weather.currentCondition.setMintemp(Utils.getFloat("temp_min",mainobj));
            weather.currentCondition.setMaxtemp(Utils.getFloat("temp_max",mainobj));
            weather.currentCondition.setTemp(Utils.getDouble("temp",mainobj));


            JSONObject windobj=Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windobj));
            weather.wind.setDeg(Utils.getFloat("deg",windobj));

            JSONObject cloudobj=Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudobj));
            return weather;

        }

        catch (JSONException e) {
            e.printStackTrace();
            Log.d("ohhooo",e.getMessage());
            return null;
        }

    }
}
