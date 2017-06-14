package com.developer.ti.mapa.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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


public class DriverOriginFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = DriverOriginFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private View rootView;
    private GPS gps;
    private TextView _tvAddres, _tvPlace, _tvOrigin;

    public DriverOriginFragment() {
        // Required empty public constructor
    }

    public static DriverOriginFragment newInstance(String param1, String param2) {
        DriverOriginFragment fragment = new DriverOriginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (mGoogleMap == null) {
            mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_origin);
            mMapFragment.getMapAsync(this);
        }

        _tvOrigin = (TextView) rootView.findViewById(R.id.text_view_origin);
        _tvAddres = (TextView) rootView.findViewById(R.id.text_view_address);
        _tvPlace = (TextView) rootView.findViewById(R.id.text_view_place);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_origin, container, false);
    }

    @Override
    public void onClick(View v) {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        final LatLng hcmus = new LatLng(gps.getLatitude(), gps.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        mGoogleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
                .title("Ubicación")
                .position(hcmus)
        );



        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);

        try {
            String NEW_URL_API_MAP = createUrl(String.valueOf(gps.getLatitude()), String.valueOf(gps.getLongitude()));
            send(true, NEW_URL_API_MAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(final CameraPosition cameraPosition) {
                final LatLng location= mGoogleMap.getCameraPosition().target;
                MarkerOptions marker = new MarkerOptions().position(location).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador));
                mGoogleMap.clear();
                mGoogleMap.addMarker(marker);
                //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
                //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(location));
                LatLng latLng = new LatLng(location.latitude, location.longitude);
                //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onCancel() {
                        //Log.e(TAG, "2");
                    }
                });

                mGoogleMap.stopAnimation();

            }
        });
    }

    private String createUrl(String val1, String val2) throws UnsupportedEncodingException {
        return Config.URL + "latlng=" + val1 + "," + val2 + "&region=es&sensor=true";
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

    private void response(JSONObject obj){
        //Log.e(TAG, "1: \n" + obj + "\n");
        try {
            JSONArray array = obj.getJSONArray("results");
            String direcion = array.getString(0);
            for (int i = 0; i < array.length(); i++){
                JSONObject json = null;
                try{
                    JSONObject a = array.getJSONObject(i);
                    String val =  a.getString("formatted_address");
                    if(i == 0){
                        _tvOrigin.setText("Origen");
                        _tvAddres.setText("" + val);
                    }

                    if(i == array.length()-3){
                        _tvPlace.setText(val);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
