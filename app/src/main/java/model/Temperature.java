package model;

/**
 * Created by simrandeepsingh on 02/09/17.
 */

public class Temperature {

    private double temp;
    private float mintemp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public float getMintemp() {
        return mintemp;
    }

    public void setMintemp(float mintemp) {
        this.mintemp = mintemp;
    }

    public float getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(float maxtemp) {
        this.maxtemp = maxtemp;
    }

    private float maxtemp;
}
