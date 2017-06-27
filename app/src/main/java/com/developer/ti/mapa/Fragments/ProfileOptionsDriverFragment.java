package com.developer.ti.mapa.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.ti.mapa.Adapter.AdapterItem;
import com.developer.ti.mapa.Adapter.TestAdapter;
import com.developer.ti.mapa.Model.Item;
import com.developer.ti.mapa.Model.Test;
import com.developer.ti.mapa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfileOptionsDriverFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private List<Test> testList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TestAdapter mAdapter;

    public ProfileOptionsDriverFragment() {
        // Required empty public constructor
    }

    public static ProfileOptionsDriverFragment newInstance(String param1, String param2) {
        ProfileOptionsDriverFragment fragment = new ProfileOptionsDriverFragment();
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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mAdapter = new TestAdapter(testList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareListData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_options_driver, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    private void prepareListData(){

        Integer[] drawableArray = {R.drawable.ic_account_box, R.drawable.ic_settings_applications, R.drawable.ic_account_circle, R.drawable.ic_add_circle};

        //Test test = new Test(drawableArray[0], "Mensajes");
        //testList.add(test);

        mAdapter.notifyDataSetChanged();
    }
}
