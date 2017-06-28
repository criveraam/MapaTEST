package com.developer.ti.mapa.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.developer.ti.mapa.Dialog.DialogCreateUser1;
import com.developer.ti.mapa.Dialog.DialogLogin;
import com.developer.ti.mapa.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvLogin;
    private Button btnCreateUser;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init(){
        tvLogin = (TextView) findViewById(R.id.text_view_login);
        btnCreateUser = (Button) findViewById(R.id.button_create_user);

        tvLogin.setOnClickListener(this);
        btnCreateUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (v.getId()){
            case R.id.text_view_login:
                DialogLogin d1 = new DialogLogin();
                d1.show(ft, "txt_login");
                break;
            case R.id.button_create_user:
                DialogCreateUser1 d2 = new DialogCreateUser1();
                d2.show(ft, "");
                break;
        }
    }
}
