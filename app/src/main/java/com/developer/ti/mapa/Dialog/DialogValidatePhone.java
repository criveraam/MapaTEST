package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private int btnSend = 0, clickCampaning = 0;
    final ProgressGenerator progressGenerator = new ProgressGenerator(this);
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    private LinearLayout _ll01, _ll02, _ll03, _ll04, _ll05;

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
        _etNumberPhone = (EditText) rootView.findViewById(R.id.edit_text_number_phone);
        _ll01 = (LinearLayout) rootView.findViewById(R.id.linear_layout_01);
        _ll02 = (LinearLayout) rootView.findViewById(R.id.linear_layout_02);
        _ll03 = (LinearLayout) rootView.findViewById(R.id.linear_layout_03);
        _ll04 = (LinearLayout) rootView.findViewById(R.id.linear_layout_04);
        _ll05 = (LinearLayout) rootView.findViewById(R.id.linear_layout_05);

        selectCampaning1(_ll01);
        selectCampaning2(_ll02);
        selectCampaning3(_ll03);
        selectCampaning4(_ll04);
        selectCampaning5(_ll05);

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

        _etNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e(TAG, ">>> " + s);

                if (s.length() == 2 ) {
                    s.append('-');
                }

            }
        });
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
            typeIdMsj = 0;
        }else if(numberPhone.length() < 10){
            typeIdMsj = 1;
        }else{
            typeIdMsj = 2;
        }
        return typeIdMsj;
    }

    private boolean selectCampaning(){
        boolean b = false;
        if(clickCampaning == 1){
            b = true;
        }else{
            b = false;
        }
        return b;
    }

    private void notificationMsj(int val){

        if(selectCampaning() == false && val == 0){
            Snackbar snackbar = Snackbar.make(rootView, "Selecciona tu compañia e ingresa un teléfono", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
        }

        if(selectCampaning() == false && val == 1){
            Snackbar snackbar = Snackbar.make(rootView, "Selecciona tu compañia y tu número teléfono no es valido", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
        }

        if(val == 0){
            Snackbar snackbar = Snackbar.make(rootView, "No haz agregado un número", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
        }

        if(val == 1){
            Snackbar snackbar = Snackbar.make(rootView, "El número de teléfono no está completo", Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
            ColoredSnackBar.warning(snackbar);
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

    private void selectCampaning1(final LinearLayout linearLayout){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                clickCampaning = 1;
                _ll02.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll03.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll04.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll05.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            }
        });
    }

    private void selectCampaning2(final LinearLayout linearLayout){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                clickCampaning = 1;
                _ll01.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll03.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll04.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll05.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            }
        });
    }

    private void selectCampaning3(final LinearLayout linearLayout){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                clickCampaning = 1;
                _ll01.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll02.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll04.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll05.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            }
        });
    }

    private void selectCampaning4(final LinearLayout linearLayout){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                clickCampaning = 1;
                _ll01.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll02.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll03.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll05.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            }
        });
    }

    private void selectCampaning5(final LinearLayout linearLayout){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                clickCampaning = 1;
                _ll01.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll02.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll03.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
                _ll04.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));
            }
        });
    }
}
