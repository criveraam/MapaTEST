package com.developer.ti.mapa.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.ti.mapa.Activities.MainActivity;
import com.developer.ti.mapa.R;

public class OptionTripFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private LinearLayout ll1, ll2;

    public OptionTripFragment() {
        // Required empty public constructor
    }

    public static OptionTripFragment newInstance(String param1, String param2) {
        OptionTripFragment fragment = new OptionTripFragment();
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
        init(view);
    }

    private void init(View view) {
        rootView = view;

        ll1 = (LinearLayout) rootView.findViewById(R.id.ll1);
        ll2 = (LinearLayout) rootView.findViewById(R.id.ll2);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option_trip, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ddd");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll1:
                Search1Fragment f = new Search1Fragment();
                getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.content, f).commit();
                break;
        }
    }
}
