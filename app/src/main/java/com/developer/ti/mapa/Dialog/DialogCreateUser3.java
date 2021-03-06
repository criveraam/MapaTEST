package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.ti.mapa.R;

public class DialogCreateUser3 extends DialogFragment implements View.OnClickListener{
    private static final String TAG = DialogCreateUser3.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Dialog dialog;
    private ImageView ivBack;
    private Button btnContinue;

    public DialogCreateUser3() {
        // Required empty public constructor
    }

    public static DialogCreateUser3 newInstance(String param1, String param2) {
        DialogCreateUser3 fragment = new DialogCreateUser3();
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

    private void init(View view){
        rootView = view;

        ivBack = (ImageView) rootView.findViewById(R.id.image_view_back_create_user_3);
        btnContinue = (Button) rootView.findViewById(R.id.button_continue3);

        ivBack.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_create_user3, container, false);
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
        }else {
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDeslize;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_back_create_user_3:
                backParams();
                break;
            case R.id.button_continue3:
                DialogCreateUser4 f1 = new DialogCreateUser4();
                Bundle bundle = new Bundle();
                bundle.putString("email", "email");
                bundle.putInt("retorno", 0);
                f1.setArguments(bundle);
                f1.show(getActivity().getSupportFragmentManager(), "txn_tag");
                break;
        }
    }

    private void backParams(){
        DialogCreateUser2 f = new DialogCreateUser2();
        Bundle bundle = new Bundle();
        bundle.putBoolean("retorno", true);
        f.setArguments(bundle);
        f.show(getActivity().getSupportFragmentManager(), "txn_tag");
    }
}
