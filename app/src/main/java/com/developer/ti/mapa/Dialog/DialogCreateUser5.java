package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.ti.mapa.Activities.MainActivity;
import com.developer.ti.mapa.R;

public class DialogCreateUser5 extends DialogFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Dialog dialog;
    private View rootview;
    private ImageView ivBack;
    private Button btnContinue;

    public DialogCreateUser5() {
        // Required empty public constructor
    }

    public static DialogCreateUser5 newInstance(String param1, String param2) {
        DialogCreateUser5 fragment = new DialogCreateUser5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_create_user5, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            if(getArguments().getBoolean("retorno")){
                getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDeslizeReturn;
            }else{
                getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDeslize;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    backParams();
                    return true;
                }
                return false;
            }
        });
    }

    private void init(View view){
        rootview = view;

        ivBack = (ImageView) rootview.findViewById(R.id.image_view_back_create_user_5);
        btnContinue = (Button) rootview.findViewById(R.id.button_continue5);

        ivBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back_create_user_5:
                backParams();
                break;
            case R.id.button_continue5:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }

    private void backParams(){
        DialogCreateUser4 f = new DialogCreateUser4();
        Bundle bundle = new Bundle();
        bundle.putBoolean("retorno", true);
        f.setArguments(bundle);
        f.show(getActivity().getSupportFragmentManager(), "txn_tag");
    }
}
