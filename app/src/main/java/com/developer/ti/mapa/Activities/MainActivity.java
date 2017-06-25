package com.developer.ti.mapa.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.developer.ti.mapa.Fragments.ConfirmRouteFragment;
import com.developer.ti.mapa.Fragments.DriverDatasRouteFragment;
import com.developer.ti.mapa.Fragments.DriverDestinationFragment;
import com.developer.ti.mapa.Fragments.DriverOriginFragment;
import com.developer.ti.mapa.Fragments.HomeFragment;
import com.developer.ti.mapa.Fragments.ProfileDriverFragment;
import com.developer.ti.mapa.Fragments.TestFragment;
import com.developer.ti.mapa.Helper.MySharePreferences;
import com.developer.ti.mapa.Helper.OnClearFromRecentService;
import com.developer.ti.mapa.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView _titleTop;
    private BottomNavigationView _navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment(item);
                    return true;
                case R.id.navigation_running:
                    selectFragment(item);
                    return true;
                case R.id.navigation_ride:
                    return true;
                case R.id.navigation_list:
                    selectFragment(item);
                    return true;
                case R.id.navigation_dashboard:
                    selectFragment(item);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _navigation = (BottomNavigationView) findViewById(R.id.navigation);
        _navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        selectFragment(_navigation.getMenu().getItem(0));
    }

    private void selectFragment(MenuItem item){
        Fragment fragmentoGenerico = null;
        switch (item.getItemId()){
            case R.id.navigation_home:
                fragmentoGenerico = new HomeFragment();
                break;
            case R.id.navigation_running:
                fragmentoGenerico = new DriverOriginFragment();
                break;
            case R.id.navigation_dashboard:
                fragmentoGenerico = new ProfileDriverFragment();
                break;
            case R.id.navigation_list:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
        }

        if(fragmentoGenerico != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.content, fragmentoGenerico);
            transaction.commit();
        }
    }
}
