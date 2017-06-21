package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.ti.mapa.Helper.SQLiteHelper;
import com.developer.ti.mapa.R;

/**
 * Created by tecnicoairmovil on 21/06/17.
 */

public class DialogInformacionINEIFE extends DialogFragment implements View.OnClickListener{
    private static final String TAG = DialogInformacionINEIFE.class.getSimpleName();
    private Dialog dialog;
    private View rootView;
    private ImageView _ivClose;
    private Button _btnConfirm;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        init();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_information_ineife, container, false);
        return rootView;
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
                dialog.dismiss();
                break;
            case R.id.button_confirm_datas_ine_ife:
                DialogINEIFE frag1 = new DialogINEIFE();
                frag1.show(getActivity().getSupportFragmentManager(), "txn_tag");
                break;
        }
    }

    private void init(){
        _ivClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        _btnConfirm = (Button) rootView.findViewById(R.id.button_confirm_datas_ine_ife);
        _ivClose.setOnClickListener(this);
        _btnConfirm.setOnClickListener(this);
    }
}
