package com.developer.ti.mapa.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developer.ti.mapa.Helper.MySharePreferences;
import com.developer.ti.mapa.R;

import java.util.Timer;
import java.util.TimerTask;

public class FullscreenActivity extends AppCompatActivity {

    private static final String TAG = FullscreenActivity.class.getSimpleName();
    private MySharePreferences mySharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        // TODO: Mantener el estado de la pantalla Vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mySharePreferences = MySharePreferences.getInstance(getApplicationContext());

        boolean redirect = mySharePreferences.getInit();

        if(redirect == true){
            Intent mainIntent = new Intent().setClass(FullscreenActivity.this, WelcomeActivity.class);
            startActivity(mainIntent);
            finish();
        }else {

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // Comenzara la siguiente Actividad
                    mySharePreferences.setInit(true);
                    Intent mainIntent = new Intent().setClass(FullscreenActivity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    // Cierra la actividad para que el usuario no pueda volver atrás
                    // Actividad pulsando el botón Atrás
                    finish();
                }
            };

            // Simula un proceso de carga largo en el asesor_fragmento_inicio de la aplicación.
            Timer timer = new Timer();
            timer.schedule(task, 5000);
        }
    }
}
