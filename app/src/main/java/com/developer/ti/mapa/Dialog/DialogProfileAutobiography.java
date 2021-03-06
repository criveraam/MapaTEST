package com.developer.ti.mapa.Dialog;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.ti.mapa.R;
/**
 * Created by tecnicoairmovil on 20/06/17.
 */

public class DialogProfileAutobiography extends DialogFragment implements View.OnClickListener{
    private ImageView _imageViewClose;
    private Dialog dialog;
    private Button _btnEditPicture;
    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog!=null){
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_profile_autobiography, container, false);
        final Animation anim = AnimationUtils.loadAnimation(getActivity(),  R.anim.fadein);
        //root.startAnimation(anim);
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
            //FragmentTransaction transaction = getContext().getChildFragmentManager().beginTransaction();
            //ft = getFragmentManager().beginTransaction();
            case R.id.button_edit_picture:
                DialogPicture frag1 = new DialogPicture();
                frag1.show(getActivity().getSupportFragmentManager(), "txn_tag");
                break;
            case R.id.image_view_close:
                dialog.dismiss();
                break;
        }
    }

    private void init(){
        _imageViewClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        _btnEditPicture = (Button) rootView.findViewById(R.id.button_edit_picture);
        _imageViewClose.setOnClickListener(this);
        _btnEditPicture.setOnClickListener(this);
    }
}