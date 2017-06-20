package com.developer.ti.mapa.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.developer.ti.mapa.Helper.Config;
import com.developer.ti.mapa.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class DriverDatasRouteFragment extends Fragment {
    private static final String TAG = DriverDatasRouteFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Switch _switchRoudeTrip;
    private LinearLayout _llContentDate1, _llContentDate2;

    public DriverDatasRouteFragment() {
        // Required empty public constructor
    }

    public static DriverDatasRouteFragment newInstance(String param1, String param2) {
        DriverDatasRouteFragment fragment = new DriverDatasRouteFragment();
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

        _llContentDate1 = (LinearLayout) rootView.findViewById(R.id.linear_layout_content_date1);
        _llContentDate2 = (LinearLayout) rootView.findViewById(R.id.linear_layout_content_date2);
        _switchRoudeTrip = (Switch) rootView.findViewById(R.id.switch_round_trip);


        // verifica si el conductor desea un viaje redondo
        activeReturnTrip(_switchRoudeTrip);
        // Dialog picker de fecha ida
        getDate1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_datas_route, container, false);
    }

    private void activeReturnTrip(Switch switchActive){
        switchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false){
                    _llContentDate2.startAnimation(Config.animationOut());
                    _llContentDate2.setVisibility(View.GONE);
                }else{
                    _llContentDate2.startAnimation(Config.animationIn());
                    _llContentDate2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getDate1(){
        final Dialog dialog;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_date);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final TextView _tvDialogDate = (TextView) dialog.findViewById(R.id.text_view_dialog_date);
        MaterialCalendarView _mcvDte = (MaterialCalendarView) dialog.findViewById(R.id.calendar_view_dialog_date);
        Button _btnDialogConfirm = (Button) dialog.findViewById(R.id.button_dialog_confirm);

        _mcvDte.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                _tvDialogDate.setText(String.valueOf(date.getDay()) + "-" + String.valueOf(date.getMonth()) + "-" + String.valueOf(date.getYear()) );
            }
        });

        _btnDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!_tvDialogDate.getText().toString().isEmpty()){

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
