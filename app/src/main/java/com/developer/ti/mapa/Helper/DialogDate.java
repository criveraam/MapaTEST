package com.developer.ti.mapa.Helper;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.developer.ti.mapa.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tecnicoairmovil on 19/06/17.
 */

public class DialogDate {
    private static final String TAG = DialogDate.class.getSimpleName();
    private static MaterialCalendarView materialCalendarView;
    private static Button btnConfirm;
    private static TextView textViewDate;
    private static DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private static String sDate;
    public static String date(Context context){
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_date);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        btnConfirm = (Button) dialog.findViewById(R.id.button_dialog_confirm);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int day = date.getDay();
                int month = date.getMonth();
                int year = date.getYear();
                Log.e(TAG, "-->" + FORMATTER.format(date.getDate()));
                sDate = day + "-" + month + "-" + year;
                textViewDate.setText(sDate);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return sDate;
    }
}
