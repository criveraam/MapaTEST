package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.developer.ti.mapa.Helper.Config;
import com.developer.ti.mapa.R;

/**
 * Created by tecnicoairmovil on 16/06/17.
 */

public class DialogDays extends Fragment{
    private static final String TAG = DialogDays.class.getSimpleName();
    private static Switch switchAllDay, switch1, switch2, switch3, switch4, switch5, switch6, switch7 ;
    private static ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7;
    private static Button buttonConfirm;


    public DialogDays() {
    }

    public static void dialog(final Context context){
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

                Config.DAYS = text;

                Log.e(TAG, "--> " + text);
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
