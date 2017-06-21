package com.developer.ti.mapa.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.developer.ti.mapa.Helper.Config;
import com.developer.ti.mapa.Dialog.DialogDate;
import com.developer.ti.mapa.Dialog.DialogDays;
import com.developer.ti.mapa.Helper.TimePickerFragment;
import com.developer.ti.mapa.R;

import java.util.Calendar;

public class ConfirmRouteFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ConfirmRouteFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Switch _sTrip, _mDays;
    private View rootView;
    private LinearLayout _llContentDate1, _llContentDate2;
    private TextView _tvDate1, _tvDate2, _tvHoure1, _tvHoure2;

    private static Switch switch1, switch2, switch3, switch4, switch5, switch6, switch7 ;
    private static ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7;
    private static Button buttonConfirm;

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
        _mDays = (Switch) rootView.findViewById(R.id.switch_days);

        activeReturnTrip(_sTrip);
        //date(_llContentDate1, _tvDate1, 1);
        //date(_llContentDate2, _tvDate2, 2);

        _llContentDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = DialogDate.date(getContext());

                Log.e(TAG, "***** >>> " + a);
                _tvDate1.setText(a);
            }
        });

        _llContentDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = DialogDate.date(getContext());
                _tvDate2.setText(a);
            }
        });

        _mDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dialog(getContext(), _mDays);
                }else{
                    _mDays.setText("Ingrese días de ruta");
                }
            }
        });

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

    private void getDays(){
        _mDays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DialogDays.dialog(getContext());
                }else{
                    Config.DAYS = null;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_days:
                //DialogDays.dialog(getContext());
                break;
        }
    }

    public static void dialog(final Context context, final Switch switchA){
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.days);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        buttonConfirm = (Button) dialog.findViewById(R.id.button_confirm_days);
        switch1 = (Switch) dialog.findViewById(R.id.switch_day1);
        switch2 = (Switch) dialog.findViewById(R.id.switch_day2);
        switch3 = (Switch) dialog.findViewById(R.id.switch_day3);
        switch4 = (Switch) dialog.findViewById(R.id.switch_day4);
        switch5 = (Switch) dialog.findViewById(R.id.switch_day5);
        switch6 = (Switch) dialog.findViewById(R.id.switch_day6);
        switch7 = (Switch) dialog.findViewById(R.id.switch_day7);
        imageView1 = (ImageView) dialog.findViewById(R.id.image_view_1);
        imageView2 = (ImageView) dialog.findViewById(R.id.image_view_2);
        imageView3 = (ImageView) dialog.findViewById(R.id.image_view_3);
        imageView4 = (ImageView) dialog.findViewById(R.id.image_view_4);
        imageView5 = (ImageView) dialog.findViewById(R.id.image_view_5);
        imageView6 = (ImageView) dialog.findViewById(R.id.image_view_6);
        imageView7 = (ImageView) dialog.findViewById(R.id.image_view_7);

        getDay(switch1, imageView1, context);
        getDay(switch2, imageView2, context);
        getDay(switch3, imageView3, context);
        getDay(switch4, imageView4, context);
        getDay(switch5, imageView5, context);
        getDay(switch6, imageView6, context);
        getDay(switch7, imageView7, context);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean s1 = switch1.isChecked();
                boolean s2 = switch2.isChecked();
                boolean s3 = switch3.isChecked();
                boolean s4 = switch4.isChecked();
                boolean s5 = switch5.isChecked();
                boolean s6 = switch6.isChecked();
                boolean s7 = switch7.isChecked();
                StringBuffer text = new StringBuffer();
                if(s1){ text.append("Lu");}
                if(s2){ text.append(" Ma");}
                if(s3){ text.append(" Mi");}
                if(s4){ text.append(" Ju");}
                if(s5){ text.append(" Vi");}
                if(s6){ text.append(" Sa");}
                if(s7){ text.append(" Do");}

                switchA.setText("Días de ruta: " + text);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void getDay(Switch switcha, final ImageView imageView, final Context context){
        switcha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false){
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cancel));
                }else{
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_circle));
                }
            }
        });
    }
}
