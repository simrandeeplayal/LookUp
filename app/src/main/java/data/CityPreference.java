package data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.StringBuilderPrinter;

/**
 * Created by simrandeepsingh on 03/09/17.
 */

public class CityPreference {
    SharedPreferences preferences;

    public CityPreference(Activity activity)
    {
        preferences=activity.getPreferences(Activity.MODE_PRIVATE);

    }

    public String getCity(){
        return preferences.getString("city","Spokane,US");
    }

    public void setCity(String city){
        preferences.edit().putString("city",city).commit();
    }

}
