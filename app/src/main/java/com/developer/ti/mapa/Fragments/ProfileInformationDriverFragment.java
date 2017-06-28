package com.developer.ti.mapa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.ti.mapa.Dialog.DialogInformacionINEIFE;
import com.developer.ti.mapa.Dialog.DialogPicture;
import com.developer.ti.mapa.Dialog.DialogProfileAutobiography;
import com.developer.ti.mapa.Dialog.DialogValidatePhone;
import com.developer.ti.mapa.R;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

public class ProfileInformationDriverFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private ImageView _ivPicture;
    private LinearLayout _llAutobiography;
    private TextView _tvIneIfe, _tvValidateNumberPhone;
    private FragmentTransaction ft;

    BottomSheetLayout bottomSheetLayout;

    public ProfileInformationDriverFragment() {
        // Required empty public constructor
    }

    public static ProfileInformationDriverFragment newInstance(String param1, String param2) {
        ProfileInformationDriverFragment fragment = new ProfileInformationDriverFragment();
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
        setToolbarTitle();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_information_driver, container, false);
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
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        ft = getFragmentManager().beginTransaction();
        switch (v.getId()){
            // TODO: abre el dialogo de captura de foto de perfil
            case R.id.image_view_picture:
                DialogPicture dp = new DialogPicture();
                dp.show(ft, "txn_picture_profile");
                break;
            // TODO: abre dialogo de datos de perfil
            case R.id.linear_layout_autobiography:
                DialogProfileAutobiography dpa = new DialogProfileAutobiography();
                dpa.show(ft, "txn_profile_autobiography");
                break;
            case R.id.button_edit_picture:
                break;
            // TODO: abre dialogo de captura de validacion de credencial
            case R.id.text_view_ine_ife:
                DialogInformacionINEIFE diii = new DialogInformacionINEIFE();
                diii.show(ft, "txt_file_ine_ife");
                break;
            // TODO: Abre dialog de validacion de numero telefonico
            case R.id.text_view_validate_number_phone:
                DialogValidatePhone dvp = new DialogValidatePhone();
                dvp.show(ft, "txt_validate_number_phone");
                break;
        }
    }

    private void setToolbarTitle(){
        TextView _titleTop;
        ImageView _arrowBack;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.top_title_center);
        _titleTop = (TextView) actionBar.getCustomView().findViewById(R.id.text_view_title);
        _arrowBack = (ImageView) actionBar.getCustomView().findViewById(R.id.image_view_back_navigation);
        _titleTop.setText("Perfil");
    }

    private void init(){
        _ivPicture = (ImageView) rootView.findViewById(R.id.image_view_picture);
        _llAutobiography = (LinearLayout) rootView.findViewById(R.id.linear_layout_autobiography);
        _tvIneIfe = (TextView) rootView.findViewById(R.id.text_view_ine_ife);
        _tvValidateNumberPhone = (TextView) rootView.findViewById(R.id.text_view_validate_number_phone);

        _ivPicture.setOnClickListener(this);
        _llAutobiography.setOnClickListener(this);
        _tvIneIfe.setOnClickListener(this);
        _tvValidateNumberPhone.setOnClickListener(this);
    }

}
