package com.developer.ti.mapa.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.ti.mapa.Helper.ColoredSnackBar;
import com.developer.ti.mapa.R;
import com.developer.ti.mapa.tools.RequestPermissionsTool;
import com.developer.ti.mapa.tools.RequestPermissionsToolImpl;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;


/**
 * Created by tecnicoairmovil on 21/06/17.
 */

public class DialogINEIFE extends DialogFragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback{
    private static final String TAG = DialogINEIFE.class.getSimpleName();
    private Dialog dialog;
    private View rootView;
    private ImageView _ivIneIfe;
    private Button _btnValidate;
    static final int PHOTO_REQUEST_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private int RESULT_OK = -1;
    private static final int CAMERA_REQUEST = 1888;

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequesCameraPermissionID = 1001;

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

        cameraView = (SurfaceView) rootView.findViewById(R.id.surfaceview);
        textView = (TextView) rootView.findViewById(R.id.text1);




        /*        Button captureImg = (Button) rootView.findViewById(R.id.action_btn);
        if (captureImg != null) {
            captureImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });
        }
        */

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            _ivIneIfe.setImageBitmap(photo);


        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_ine_ife, container, false);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_validate:
                Snackbar snackbar = Snackbar.make(v, "Espere un momento...", Snackbar.LENGTH_LONG).setAction("Action", null);
                ColoredSnackBar.confirm(snackbar);
                snackbar.show();
                break;
        }
    }



    private void init(){
        //_ivIneIfe = (ImageView) rootView.findViewById(R.id.image_view_ine_ife);
        _btnValidate= (Button) rootView.findViewById(R.id.text_view_validate);
        _btnValidate.setOnClickListener(this);
    }

}
