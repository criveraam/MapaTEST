package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.developer.ti.mapa.Fragments.DriverDestinationFragment;
import com.developer.ti.mapa.Fragments.ProfileInformationDriverFragment;
import com.developer.ti.mapa.Helper.ColoredSnackBar;
import com.developer.ti.mapa.Helper.ProgressGenerator;
import com.developer.ti.mapa.R;

public class DialogCodePhone extends DialogFragment implements View.OnClickListener, ProgressGenerator.OnCompleteListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Dialog dialog;
    private ImageView _ivClose;
    final ProgressGenerator progressGenerator = new ProgressGenerator(this);
    private ActionProcessButton mBtnAction;
    private EditText _etCode;

    public DialogCodePhone() {
        // Required empty public constructor
    }

    public static DialogCodePhone newInstance(String param1, String param2) {
        DialogCodePhone fragment = new DialogCodePhone();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_code_phone, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_close:
                dialog.dismiss();
                break;
        }
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    private void init(){
        _ivClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        _etCode = (EditText) rootView.findViewById(R.id.edit_text_code);

        _ivClose.setOnClickListener(this);

        mBtnAction = (ActionProcessButton) rootView.findViewById(R.id.btnAction1);
        mBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mBtnAction.setProgress(50);
                int msj = validateCode();
                notificationMsj(msj);
            }
        });
    }

    @Override
    public void onComplete() {

    }

    private int validateCode(){
        int val = 1;

        String mString = _etCode.getText().toString();
        if(mString.isEmpty()){
            val = 0;
        }else if(mString.length() <= 4){
            val = 1;
        }else{
            val = 2;
        }

        Log.e("TAG", ">>> " + val );
        return val;
    }

    private void notificationMsj(int val) {

        if (val == 0) {
            Snackbar snackbar = Snackbar.make(rootView, "Al parecer no hay código que validar", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
        }else if(val == 1){
            Snackbar snackbar = Snackbar.make(rootView, "Ingresa el código completo", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
        }else if(val == 2){
            ProfileInformationDriverFragment fragment2 = new ProfileInformationDriverFragment();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.content, fragment2).commit();
            Log.e("TAG", ">>> " + val + " ++++");
        }

    }
}
