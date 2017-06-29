package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.ti.mapa.Activities.MainActivity;
import com.developer.ti.mapa.Activities.MenuTestActivity;
import com.developer.ti.mapa.Activities.WelcomeActivity;
import com.developer.ti.mapa.Fragments.DriverDestinationFragment;
import com.developer.ti.mapa.R;

public class DialogLogin extends DialogFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Dialog dialog;
    private ImageView ivClose;
    private TextView tvForgetAccount;
    private Button btnLogin;
    private View rootView;

    public DialogLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view){
        rootView = view;

        ivClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        tvForgetAccount = (TextView) rootView.findViewById(R.id.text_view_forget_account);
        btnLogin = (Button) rootView.findViewById(R.id.button_login);

        ivClose.setOnClickListener(this);
        tvForgetAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_close:
                Intent i = new Intent(getContext(), WelcomeActivity.class);
                startActivity(i);
                break;
            case R.id.text_view_forget_account:
                DialogForgetAccount f = new DialogForgetAccount();
                f.show(getActivity().getSupportFragmentManager(), "txn_tag");
                break;
            case R.id.button_login:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }
}
