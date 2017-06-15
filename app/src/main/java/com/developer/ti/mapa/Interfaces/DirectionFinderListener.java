package com.developer.ti.mapa.Interfaces;

import com.developer.ti.mapa.Model.Route;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
