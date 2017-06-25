package com.developer.ti.mapa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.ti.mapa.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

public class TestFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    BottomSheetLayout bottomSheetLayout;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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

        bottomSheetLayout = (BottomSheetLayout) view.findViewById(R.id.bottomsheet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_custom_view:
                bottomSheetLayout.showWithSheetView(getActivity().getLayoutInflater().inflate(R.layout.custom_view, bottomSheetLayout, false));
                break;
            case R.id.btn_expand_me:
                bottomSheetLayout.expandSheet();
                break;
            case R.id.btn_intent:
                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Rhesoft tutorial");
                shareIntent.setType("text/plain");

                IntentPickerSheetView intentPickerSheetView = new IntentPickerSheetView(getContext(), shareIntent, "Share",
                        new IntentPickerSheetView.OnIntentPickedListener() {
                            @Override
                            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                                bottomSheetLayout.dismissSheet();
                                startActivity(activityInfo.getConcreteIntent(shareIntent));
                            }
                        });

                bottomSheetLayout.showWithSheetView(intentPickerSheetView);
                break;
        }
    }
}
