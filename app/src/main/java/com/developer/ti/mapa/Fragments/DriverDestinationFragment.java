package com.developer.ti.mapa.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developer.ti.mapa.Helper.Config;
import com.developer.ti.mapa.Helper.GPS;
import com.developer.ti.mapa.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DriverDestinationFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = DriverDestinationFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "addressOrigin";
    private static final String ARG_PARAM2 = "locationOrigin";
    private static final String ARG_PARAM3 = "latDestination";
    private static final String ARG_PARAM4 = "lngDestination";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private ImageView _ivIcon1, _ivIcon2;
    private TextView _tvOrigin, _tvOriginAddress, _tvOriginPlace;
    private TextView _tvDestination, _tvDestinationAddress, _tvDestinationPlace;
    private Button _btnConfirm;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private GPS gps;


    public DriverDestinationFragment() {
        // Required empty public constructor
    }

    public static DriverDestinationFragment newInstance(String param1, String param2) {
        DriverDestinationFragment fragment = new DriverDestinationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        gps = new GPS(getContext());
        _ivIcon1 = (ImageView) rootView.findViewById(R.id.image_view_icon1_);
        _tvOrigin = (TextView) rootView.findViewById(R.id.text_view_origin_);
        _tvOriginAddress = (TextView) rootView.findViewById(R.id.text_view_origin_address_);
        _tvOriginPlace = (TextView) rootView.findViewById(R.id.text_view_origin_place_);
        _ivIcon2 = (ImageView) rootView.findViewById(R.id.image_view_icon2_);
        _tvDestination = (TextView) rootView.findViewById(R.id.text_view_destination_);
        _tvDestinationAddress = (TextView) rootView. findViewById(R.id.text_view_destination_address_);
        _tvDestinationPlace = (TextView) rootView.findViewById(R.id.text_view_destination_place_);
        _btnConfirm = (Button) rootView.findViewById(R.id.button_confirm_route_);

        _btnConfirm.setOnClickListener(this);


        if (mGoogleMap == null) {
            mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_destination_);
            mMapFragment.getMapAsync(this);
        }

        if(getArguments() != null){
            _ivIcon1.startAnimation(Config.animationIn());
            _tvOrigin.startAnimation(Config.animationIn());
            _tvOriginAddress.startAnimation(Config.animationIn());
            _tvOriginAddress.setText(getArguments().getString(ARG_PARAM1));
            _tvOriginPlace.startAnimation(Config.animationIn());
            _tvOriginPlace.setText(getArguments().getString(ARG_PARAM2));
        }

        //setToolbarTitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_destination, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(getArguments() != null){
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Double lat = Double.valueOf(getArguments().getString(ARG_PARAM3));
            Double lng = Double.valueOf(getArguments().getString(ARG_PARAM4));
            String NEW_URL =  Config.URL + "latlng=" + String.valueOf(lat) + "," + String.valueOf(lng)+ "&region=es&sensor=true";
            send(true, NEW_URL);
            LatLng latLng = new LatLng(lat, lng);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                    .title("Destino")
                    .position(latLng)
            );
            mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    String URL = Config.URL + "latlng=" + String.valueOf(gps.getLatitude()) + "," + String.valueOf(gps.getLongitude())+ "&region=es&sensor=true";
                    send(true, URL);
                    return false;
                }
            });
            mGoogleMap.setMyLocationEnabled(true);

            mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(final CameraPosition cameraPosition) {
                    final LatLng location= mGoogleMap.getCameraPosition().target;
                    MarkerOptions marker = new MarkerOptions().position(location).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador));
                    mGoogleMap.clear();
                    mGoogleMap.addMarker(marker);
                    LatLng latLng = new LatLng(location.latitude, location.longitude);
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            // Log.e(TAG, "2");
                        }
                        @Override
                        public void onCancel() {
                            mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
                            String URL = Config.URL + "latlng=" + String.valueOf(location.latitude) + "," + String.valueOf(location.longitude)+ "&region=es&sensor=true";
                            send(true, URL);
                        }
                    });
                }
            });
            mGoogleMap.stopAnimation();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back_navigation:
                DriverOriginFragment f1 = new DriverOriginFragment();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.content, f1).commit();
                break;
            case R.id.button_confirm_route_:
                DriverRouteFragment f2 = new DriverRouteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("originAddress",_tvOriginAddress.getText().toString());
                bundle.putString("destinationAddress", _tvDestinationAddress.getText().toString());
                f2.setArguments(bundle);
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.content, f2).commit();
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void send(boolean peticion, String newUrl){
        try {
            RequestQueue cola = Volley.newRequestQueue(getContext());
            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, newUrl, new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    response(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "---" + error);
                }
            });
            cola.add(stringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void response(final JSONObject obj){
        //Log.e(TAG, "1: \n" + obj + "\n");
        try{
            JSONArray array = obj.getJSONArray("results");
            for (int i = 0; i < array.length(); i++){
                JSONObject json = null;
                try{
                    JSONObject a = array.getJSONObject(i);
                    String val =  a.getString("formatted_address");
                    if(i == 0){
                        _tvDestination.startAnimation(Config.animationIn());
                        _tvDestination.setText("Destino");
                        _tvDestinationAddress.startAnimation(Config.animationIn());
                        _tvDestinationAddress.setText("" + val);
                        _ivIcon2.startAnimation(Config.animationIn());
                        _ivIcon2.setVisibility(View.VISIBLE);
                    }
                    if(i == array.length()-3){
                        _tvDestinationPlace.setVisibility(View.VISIBLE);
                        _tvDestinationPlace.startAnimation(Config.animationIn());
                        _tvDestinationPlace.setText(val);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void setToolbarTitle(){
        TextView _titleTop;
        ImageView _arrowBack;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.top_title_center);
        _titleTop = (TextView) actionBar.getCustomView().findViewById(R.id.text_view_title);
        _arrowBack = (ImageView) actionBar.getCustomView().findViewById(R.id.image_view_back_navigation);
        _titleTop.setText("Crear ruta");
        _arrowBack.setVisibility(View.VISIBLE);
        _arrowBack.setOnClickListener(this);
    }
}
