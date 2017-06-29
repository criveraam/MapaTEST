package com.developer.ti.mapa.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
    private ImageView _ivIcon;
    private Button _btnSearchDestination;
    private LinearLayout _llSearchOrigin;
    private int checkOrigin = 0, checkDestination = 0;

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

        if (mGoogleMap == null) {
            mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_origin);
            mMapFragment.getMapAsync(this);
        }

        _ivIcon = (ImageView) rootView.findViewById(R.id.image_view_icon);
        _tvPlace = (TextView) rootView.findViewById(R.id.text_view_place);
        _tvAddres = (TextView) rootView.findViewById(R.id.text_view_address);
        _tvOrigin = (TextView) rootView.findViewById(R.id.text_view_origin);
        _llSearchOrigin = (LinearLayout) rootView.findViewById(R.id.linear_layout_location);
        _btnSearchDestination = (Button) rootView.findViewById(R.id.button_search_destination);

        _llSearchOrigin.setOnClickListener(this);
        _btnSearchDestination.setOnClickListener(this);

        //setToolbarTitle();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_origin, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_layout_location:
                _llSearchOrigin.startAnimation(Config.animationIn());
                checkOrigin = 1;
                findPlace();
                break;
            case R.id.button_search_destination:
                _btnSearchDestination.startAnimation(Config.animationOut());
                _btnSearchDestination.startAnimation(Config.animationIn());
                checkDestination = 2;
                findPlace();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Config.RESULT_OK) {

                LatLng hcmus = new LatLng(PlaceAutocomplete.getPlace(getContext(), data).getLatLng().latitude, PlaceAutocomplete.getPlace(getContext(), data).getLatLng().longitude);
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                String lat = String.valueOf(place.getLatLng().latitude);
                String lng = String.valueOf(place.getLatLng().longitude);
                try{
                    if(checkOrigin == 1){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
                        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                        try {
                            String NEW = createUrl(lat,lng);
                            send(true, NEW);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        checkOrigin = 0;
                    }

                    if(checkDestination == 2){
                        Log.e(TAG, "Cambio de fragmento");
                        Bundle bundle = new Bundle();
                        bundle.putString("addressOrigin", _tvAddres.getText().toString());
                        bundle.putString("locationOrigin", _tvPlace.getText().toString());
                        bundle.putString("latDestination", lat);
                        bundle.putString("lngDestination", lng);
                        DriverDestinationFragment fragment2 = new DriverDestinationFragment();
                        fragment2.setArguments(bundle);
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.content, fragment2).commit();
                        checkDestination = 0;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.e(TAG, status.getStatusMessage());
            } else if (resultCode == Config.RESULT_CANCELED) {
                Log.e(TAG, "resultCanceled: " + resultCode);
            }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        final LatLng hcmus = new LatLng(gps.getLatitude(), gps.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)).title("Ubicación").position(hcmus));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            String NEW_URL_API_MAP = createUrl(String.valueOf(gps.getLatitude()), String.valueOf(gps.getLongitude()));
            send(true, NEW_URL_API_MAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


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
                        _tvOrigin.startAnimation(Config.animationIn());
                        _tvOrigin.setText("Origen");
                        _tvAddres.startAnimation(Config.animationIn());
                        _tvAddres.setText("" + val);
                        _ivIcon.startAnimation(Config.animationIn());
                        _ivIcon.setVisibility(View.VISIBLE);
                    }
                    if(i == array.length()-3){
                        _tvPlace.setVisibility(View.VISIBLE);
                        _tvPlace.startAnimation(Config.animationIn());
                        _tvPlace.setText(val);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void findPlace() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter).build(getActivity());
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void setToolbarTitle(){
        TextView _titleTop;
        ImageView _arrowBack;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        /*actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.top_title_center);
        _titleTop = (TextView) actionBar.getCustomView().findViewById(R.id.text_view_title);
        _arrowBack = (ImageView) actionBar.getCustomView().findViewById(R.id.image_view_back_navigation);
        _titleTop.setText("Crear ruta");
        _arrowBack.setOnClickListener(this);*/
    }

}
