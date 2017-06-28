package com.developer.ti.mapa.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.ti.mapa.Helper.SQLiteHelper;
import com.developer.ti.mapa.R;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by tecnicoairmovil on 21/06/17.
 */

public class DialogPicture extends DialogFragment implements View.OnClickListener{
    private static final String TAG = DialogPicture.class.getSimpleName();
    private Dialog dialog;
    private View rootView;
    private Animation animation;
    private ImageView _imClose, _imImage;
    private Button _btnOpenCarema, _btnOpenFiles;
    private int PICK_IMAGE_REQUEST = 1;
    private int RESULT_OK = -1;
    private static final int CAMERA_REQUEST = 1888;
    private static SQLiteHelper sqLiteHelper;


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

        sqLiteHelper = new SQLiteHelper(getContext(), "Picture.profile", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PICTURE (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOG)");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_picture_profile, container, false);
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
            case R.id.button_open_files:
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are muzaltiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.button_open_camera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "RequestCode: " +  requestCode + "\nresultCode: " + resultCode + "\ndata: " + data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                _imImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            _imImage.setImageBitmap(photo);
        }
    }

    private void init(){
        _imClose = (ImageView) rootView.findViewById(R.id.image_view_close);
        _btnOpenCarema = (Button) rootView.findViewById(R.id.button_open_camera);
        _btnOpenFiles = (Button) rootView.findViewById(R.id.button_open_files);
        _imImage = (ImageView) rootView.findViewById(R.id.profile_image);

        _imClose.setOnClickListener(this);
        _btnOpenCarema.setOnClickListener(this);
        _btnOpenFiles.setOnClickListener(this);
    }
}
