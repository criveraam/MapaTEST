package com.developer.ti.mapa.Model;

import com.developer.ti.mapa.Helper.Distance;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by tecnicoairmovil on 12/06/17.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
