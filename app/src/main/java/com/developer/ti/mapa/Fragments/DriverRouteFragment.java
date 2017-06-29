package com.developer.ti.mapa.Fragments;

import android.Manifest;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.ti.mapa.Helper.Config;
import com.developer.ti.mapa.Helper.DirectionFinder;
import com.developer.ti.mapa.Helper.GPS;
import com.developer.ti.mapa.Interfaces.DirectionFinderListener;
import com.developer.ti.mapa.Model.Route;
import com.developer.ti.mapa.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class DriverRouteFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DirectionFinderListener{
    private static final String TAG = DriverRouteFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "originAddress";
    private static final String ARG_PARAM2 = "destinationAddress";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private TextView _tvOrigin, _tvDestination;
    private TextView _tvOriginAddress, _tvDestinationAddress;
    private ImageView _ivIcon1, _ivIcon2;
    private Button _btnRouteConfirm;
    private LinearLayout _llOrigin, _llDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private GPS gps;

    public DriverRouteFragment() {
        // Required empty public constructor
    }

    public static DriverRouteFragment newInstance(String param1, String param2) {
        DriverRouteFragment fragment = new DriverRouteFragment();
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
            Log.e(TAG, "Argumentos -->" + getArguments().toString());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        gps = new GPS(getContext());
        assigment();
        // setToolbarTitle();
        getArgument();
        mMap();
        rqtDirection();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_route, container, false);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back_navigation:
                DriverDestinationFragment f1 = new DriverDestinationFragment();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.content, f1).commit();
                break;
            case R.id.button_route_confirm:
                ConfirmRouteFragment f2 = new ConfirmRouteFragment();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng hcmus = new LatLng(gps.getLatitude(), gps.getLongitude());
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        mGoogleMap.setBuildingsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onDirectionFinderStart() {
        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (final Route route1 : route) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route1.startLocation, 16));

            final Marker markerOrigin = mGoogleMap.addMarker(new MarkerOptions()
                    .position(route1.startLocation)
                    .title(route1.startAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntoa))
                    .snippet("Origen"));
            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .position(route1.startLocation)
                    .title(route1.startAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntoa))
                    .snippet("Origen")));

            final Marker markerDestination = mGoogleMap.addMarker(new MarkerOptions()
                    .title(route1.endAddress)
                    .position(route1.endLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntob))
                    .snippet("Destino"));


            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.puntob))
                    .title(route1.endAddress)
                    .position(route1.endLocation)));

            _llOrigin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                    markerOrigin.showInfoWindow();
                }
            });

            _llDestination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                    markerDestination.showInfoWindow();
                }
            });

            try {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(getResources().getColor(R.color.colorPrimary)).
                        width(10);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(markerOrigin.getPosition());
                builder.include(markerDestination.getPosition());
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.30); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mGoogleMap.animateCamera(cu);

                for (int i = 0; i < route1.points.size(); i++)
                    polylineOptions.add(route1.points.get(i));

                polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void assigment(){
        _ivIcon1 = (ImageView) rootView.findViewById(R.id.image_view_route_icon1);
        _ivIcon2 = (ImageView) rootView.findViewById(R.id.image_view_route_icon2);
        _tvOrigin = (TextView) rootView.findViewById(R.id.text_view_route_origin);
        _tvDestination = (TextView) rootView.findViewById(R.id.text_view_route_destination);
        _tvOriginAddress = (TextView) rootView.findViewById(R.id.text_view_route_origin_address);
        _tvDestinationAddress = (TextView) rootView.findViewById(R.id.text_view_route_destination_address);
        _llOrigin = (LinearLayout) rootView.findViewById(R.id.linear_layout_origin);
        _llDestination = (LinearLayout) rootView.findViewById(R.id.linear_layout_destination);
        _btnRouteConfirm = (Button) rootView.findViewById(R.id.button_route_confirm);

        _btnRouteConfirm.setOnClickListener(this);
    }

    private void setToolbarTitle(){
        TextView _titleTop;
        ImageView _arrowBack;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.top_title_center);
        _titleTop = (TextView) actionBar.getCustomView().findViewById(R.id.text_view_title);
        _arrowBack = (ImageView) actionBar.getCustomView().findViewById(R.id.image_view_back_navigation);
        _titleTop.setText("Confirmar ruta");
        _arrowBack.setVisibility(View.VISIBLE);
        _arrowBack.setOnClickListener(this);
    }

    private void getArgument(){
        if(getArguments() != null){
            _ivIcon1.startAnimation(Config.animationIn());
            _ivIcon2.startAnimation(Config.animationIn());
            _tvOrigin.startAnimation(Config.animationIn());
            _tvDestination.startAnimation(Config.animationIn());
            _tvOriginAddress.startAnimation(Config.animationIn());
            _tvOriginAddress.setText(getArguments().getString(ARG_PARAM1));
            _tvDestinationAddress.startAnimation(Config.animationIn());
            _tvDestinationAddress.setText(getArguments().getString(ARG_PARAM2));
        }
    }

    private void mMap(){
        if (mGoogleMap == null) {
            mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_routes);
            mMapFragment.getMapAsync(this);
        }
    }

    private void rqtDirection(){
        try {
            if (getArguments() != null){
                new DirectionFinder(this, getArguments().getString(ARG_PARAM1), getArguments().getString(ARG_PARAM2)).execute();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
