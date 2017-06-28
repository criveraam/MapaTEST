package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.developer.ti.mapa.Activities.WelcomeActivity;
import com.developer.ti.mapa.Fragments.DriverDestinationFragment;
import com.developer.ti.mapa.R;

public class DialogCreateUser1 extends DialogFragment implements View.OnClickListener{
    private static final String TAG = DialogCreateUser1.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Dialog dialog;
    private Button btnContinue1;
    private View rootView;

    public DialogCreateUser1() {
        // Required empty public constructor
    }

    public static DialogCreateUser1 newInstance(String param1, String param2) {
        DialogCreateUser1 fragment = new DialogCreateUser1();
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        rootView = view;
        btnContinue1 = (Button) rootView.findViewById(R.id.button_continue1);

        btnContinue1.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_user1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            Log.d(TAG, "argumentos");
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDeslizeReturn;
        }else {
            Log.d(TAG, "sin argumentos");
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDeslize;
        }
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
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent i = new Intent(getContext(), WelcomeActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_continue1:
                DialogCreateUser2 f1 = new DialogCreateUser2();

                Bundle bundle = new Bundle();
                bundle.putString("name", "Nombre de prueba");
                bundle.putString("lastname", "Apellidos de prueba");
                f1.setArguments(bundle);
                f1.show(getActivity().getSupportFragmentManager(), "txn_tag");
                break;
        }
    }
}
