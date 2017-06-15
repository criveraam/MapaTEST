package com.developer.ti.mapa.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.developer.ti.mapa.Helper.TimePickerFragment;
import com.developer.ti.mapa.R;

import java.util.Calendar;

public class ConfirmRouteFragment extends Fragment {
    private static final String TAG = ConfirmRouteFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Switch _sTrip;
    private View rootView;
    private LinearLayout _llContentDate1, _llContentDate2;
    private TextView _tvDate1, _tvDate2, _tvHoure1, _tvHoure2;

    public ConfirmRouteFragment() {
        // Required empty public constructor
    }

    public static ConfirmRouteFragment newInstance(String param1, String param2) {
        ConfirmRouteFragment fragment = new ConfirmRouteFragment();
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
        _tvDate1 = (TextView) rootView.findViewById(R.id.text_view_date1);
        _tvDate2 = (TextView) rootView.findViewById(R.id.text_view_date2);
        _sTrip = (Switch) rootView.findViewById(R.id.switch_trip);

        activeReturnTrip(_sTrip);
        date(_llContentDate1, _tvDate1, 1);
        date(_llContentDate2, _tvDate2, 2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_route, container, false);
    }

    private void date(final LinearLayout linearLayout, final TextView textView, final int val ){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                Calendar calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                DialogFragment newFragment = new TimePickerFragment(val);
                                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                            }
                        }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    private void activeReturnTrip(Switch switchActive){
        switchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                if(isChecked == false){
                    Log.d(TAG, " --> " + isChecked);
                    _llContentDate2.setVisibility(View.GONE);
                }else{
                    Log.d(TAG, " -->  -" + isChecked);
                    _llContentDate2.setVisibility(View.VISIBLE);
                }
            }
        });
    }



}
