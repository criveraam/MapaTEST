package com.developer.ti.mapa.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.developer.ti.mapa.Dialog.CustomBottomSheetDialogFragment;
import com.developer.ti.mapa.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class Search1Fragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button btnSubtraction;
    private Button btnSum;
    private TextView tvPlacesCar;
    private View rootView;
    private String val;
    BottomSheetLayout bottomSheetLayout;
    private Menu menu;

    public Search1Fragment() {
        // Required empty public constructor
    }

    public static Search1Fragment newInstance(String param1, String param2) {
        Search1Fragment fragment = new Search1Fragment();
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

        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.MyToolbar);

        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle("My Toolbar Tittle");

        //init(view);
        //setToolbarTitle();
        // TODO: Inclucion de lugares
        //places();
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search1, container, false);
    }

    private void init(View view){
        rootView = view;

        //btnSum = (Button) rootView.findViewById(R.id.button_sum);
        //btnSubtraction = (Button) rootView.findViewById(R.id.button_subtraction);
        //tvPlacesCar = (TextView) rootView.findViewById(R.id.text_view_places_car);


        /*View showModalBottomSheet = rootView.findViewById(R.id.as_modal);
        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initializing a bottom sheet
                CustomBottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
                //show it
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    private void setToolbarTitle(){
        TextView _titleTop;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.top_title_center);
        _titleTop = (TextView) actionBar.getCustomView().findViewById(R.id.text_view_title);
        _titleTop.setText("Buscar");
    }

    @Override
    public void onResume() {
        super.onResume();
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    /*
    private void places(){
        btnSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = tvPlacesCar.getText().toString();
                int x = Integer.parseInt(val);
                int y = 0;
                if(x != 0){
                    y = x - 1;
                    if(y != 0){
                        tvPlacesCar.setText(String.valueOf(y));
                    }
                }

            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = tvPlacesCar.getText().toString();
                int x = Integer.parseInt(val);
                if(Integer.parseInt(val) < 4){
                    x = Integer.parseInt(val) + 1;
                }
                tvPlacesCar.setText(String.valueOf(x));
            }
        });
    }

    */
}
