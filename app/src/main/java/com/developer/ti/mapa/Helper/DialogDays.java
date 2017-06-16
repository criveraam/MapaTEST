package com.developer.ti.mapa.Helper;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.developer.ti.mapa.R;

/**
 * Created by tecnicoairmovil on 16/06/17.
 */

public class DialogDays {

    private static Switch switchAllDay, switch1, switch2, switch3, switch4, switch5, switch6, switch7 ;
    private static ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7;
    private static Button buttonConfirm;

    public static void dialog(Context context){
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.days);

        buttonConfirm = (Button) dialog.findViewById(R.id.button_confirm_days);
        switchAllDay = (Switch) dialog.findViewById(R.id.switch_all_day);
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


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == false){
                    imageView1.setVisibility(View.GONE);
                }else{
                    imageView1.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void getAllDays(){
        switchAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    public void getDay(Switch switch1, final ImageView imageView){

    }
}
