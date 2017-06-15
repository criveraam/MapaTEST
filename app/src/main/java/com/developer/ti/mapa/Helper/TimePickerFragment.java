package com.developer.ti.mapa.Helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import com.developer.ti.mapa.R;

/**
 * Created by tecnicoairmovil on 15/06/17.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private int direct;

    public TimePickerFragment(int direct) {
        this.direct = direct;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(direct == 1) {
            TextView tv1 = (TextView) getActivity().findViewById(R.id.text_view_houre1);
            tv1.setText("" + view.getCurrentHour() + ":" + view.getCurrentMinute());
        }

        if(direct == 2){
            TextView tv1 = (TextView) getActivity().findViewById(R.id.text_view_houre2);
            tv1.setText("" + view.getCurrentHour() + ":" + view.getCurrentMinute());
        }
    }
}
