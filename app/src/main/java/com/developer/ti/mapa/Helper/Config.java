package com.developer.ti.mapa.Helper;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by tecnicoairmovil on 14/06/17.
 */

public class Config {
    public static final String URL = "http://maps.googleapis.com/maps/api/geocode/json?";
    public static final String URL_DIRECTION = "https://maps.googleapis.com/maps/api/directions/json?";

    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCELED = 0;


    public static Animation animationIn(){
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1600);
        return in;
    }

    public static Animation animationOut(){
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1600);
        return out;
    }
}
