package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.developer.ti.mapa.Helper.ColoredSnackBar;
import com.developer.ti.mapa.Helper.ProgressGenerator;
import com.developer.ti.mapa.R;
import com.dd.processbutton.iml.SubmitProcessButton;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogValidatePhone extends DialogFragment implements View.OnClickListener, ProgressGenerator.OnCompleteListener{
    private static final String TAG = DialogValidatePhone.class.getSimpleName();
    private Dialog dialog;
    private View rootView;
    private ImageView _ivClose;
    private Button _btnValidateOk;
    private EditText _etNumberPhone, _etCode;
    private TextView _tvMsjWait;
    private final SubmitProcessButton submitProcessButton = null;
    private int btnSend = 0;
    final ProgressGenerator progressGenerator = new ProgressGenerator(this);
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    private ActionProcessButton mBtnAction;

    public DialogValidatePhone() {
        // Required empty public constructor
    }

    public static DialogValidatePhone newInstance(String numberPhone, int status){
        DialogValidatePhone dvp = new DialogValidatePhone();
        Bundle args = new Bundle();
        args.putString("numberPhone", numberPhone);
        args.putInt("status", status);
        dvp.setArguments(args);
        return dvp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        init();

        final ProgressGenerator progressGenerator = new ProgressGenerator(this);

        mArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_validate_phone, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.image_view_close:
                if(btnSend != 1){
                    Snackbar snackbar = Snackbar
                            .make(v, "Al parecer, quieres cerrar esta seción, y aun no haz validado tu número", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);

                    snackbar.show();
                    ColoredSnackBar.error(snackbar);
                }
                break;

        }
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete >>>");
    }

    private void init(){
        _ivClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        _tvMsjWait = (TextView) rootView.findViewById(R.id.text_view_msj_wait);
        _etNumberPhone = (EditText) rootView.findViewById(R.id.edit_text_number_phone);
        _etCode = (EditText) rootView.findViewById(R.id.edit_text_code);
        // get the button view

        /*final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) rootView.findViewById(R.id.btnSignIn);
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnSignIn);
                btnSignIn.setProgress(50);
                btnSignIn.setEnabled(false);
            }
        });*/

        mBtnAction = (ActionProcessButton) rootView.findViewById(R.id.btnAction);

        mBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mBtnAction.setProgress(50);
                int msj = validateNumberPhone();
                notificationMsj(msj);
            }
        });
        _ivClose.setOnClickListener(this);
    }

    private void sendNumberPhone(){
        Fragment borrar =  this;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DialogValidatePhone fragmento = DialogValidatePhone.newInstance(_etNumberPhone.getText().toString(), 1);
        borrar.onDestroy();
        ft.remove(borrar).replace(R.id.container, fragmento).addToBackStack(null).commit();
    }

    private void mArguments(){
        if(getArguments() != null){
            Log.e(TAG, "ARGUMENTS >>> " + getArguments().toString());
        }
    }

    private int validateNumberPhone(){
        int typeIdMsj = 0;
        String numberPhone = _etNumberPhone.getText().toString();
        if(numberPhone.isEmpty()){
            typeIdMsj = 1;
        }else{
            typeIdMsj = 2;
        }
        return typeIdMsj;
    }

    private void notificationMsj(int val){
        if(val == 1){
            Snackbar snackbar = Snackbar.make(rootView, "No haz agregado un número", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.error(snackbar);
        }

        if(val == 2){
            progressGenerator.start(mBtnAction);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        //mBtnAction.setText("Procesando...");
                        for(int i = 0; i <= 3; i++){
                            Log.e(TAG, "--->" + i);
                            Thread.sleep(100);
                            if(i==3){
                                Log.d(TAG, ">>>>" + i);
                                DialogCodePhone dialog = new DialogCodePhone();
                                dialog.show(getActivity().getSupportFragmentManager(), "txt_dialog_code_phone");
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 3000);
        }

    }
}
