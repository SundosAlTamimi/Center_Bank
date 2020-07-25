package com.falconssoft.centerbank;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LostAndStealing extends AppCompatActivity {
    String mCameraFileName, path;
    boolean isPermition;
    Button gallary,upload;
    Uri image;
    ImageView imageLost;
    Bitmap serverPicBitmap;
    String serverPic;
    ListView checkList;
    RadioButton onlyCheque,RangeCheque;
    EditText RangeEdText;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_stealing);

        initial();

        gallary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                File file = new File(Environment.getExternalStorageDirectory(),  "/DCIM/Camera/IMG_20200629_112400" +".jpg");//+(cal.getTimeInMillis()
//                if (ContextCompat.checkSelfPermission(EditerCheackActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//                    // Permission is not granted
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(EditerCheackActivity.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//
//                    } else {
//                        // No explanation needed; request the permission
//                        ActivityCompat.requestPermissions(EditerCheackActivity.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                1);
//                    }
//                }
//
//                if(!file.exists()){
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }else{
//                    file.delete();
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//                if (file.exists()){
//                    capturedImageUri = Uri.fromFile(file) ;
//                    Log.e("uri", capturedImageUri.getPath());
//                }
//                flag = 0;
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//
////                file = Uri.fromFile(getFile());
////
////                //Setting the file Uri to my photo
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,file);
//
//                if(cameraIntent.resolveActivity(getPackageManager())!=null)
//                {
//                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//                }
//                flag = 0;
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                isPermition = isStoragePermissionGranted();
                if (isPermition) {
                    cameraIntent();
                }

            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                image = data.getData();
                imageLost.setImageURI(image);
//                CheckPic.setVisibility(View.VISIBLE);
            }
            if (image == null && mCameraFileName != null) {
                image = Uri.fromFile(new File(mCameraFileName));
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png";
                serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png");
                imageLost.setImageBitmap(serverPicBitmap);
                serverPic = bitMapToString(serverPicBitmap);
                deleteFiles(path);
            }
            File file = new File(mCameraFileName);
            if (!file.exists()) {
                file.mkdir();
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png";
                serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png");
                imageLost.setImageBitmap(serverPicBitmap);
                serverPic = bitMapToString(serverPicBitmap);
                deleteFiles(path);
//                    Bitmap bitmap1 = StringToBitMap(serverPic);
//                    showImageOfCheck(bitmap1);
            } else {

                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png";
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                serverPicBitmap = BitmapFactory.decodeFile(path, options);
                serverPicBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lost.png");
                imageLost.setImageBitmap(serverPicBitmap);
                serverPic = bitMapToString(serverPicBitmap);
                deleteFiles(path);
//                Bitmap bitmap1 = StringToBitMap(serverPic);
//                showImageOfCheck(bitmap1);

            }
        }

    }


    private void cameraIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("_mm_ss");

        String newPicFile = "in" + ".png";
        String outPath = Environment.getExternalStorageDirectory() + File.separator + newPicFile;
        Log.e("Lost", "" + outPath);
        File outFile = new File(outPath);
        path = outPath;
        mCameraFileName = outFile.toString();
        Uri outuri = Uri.fromFile(outFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        startActivityForResult(intent, 2);
    }



    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("gg1", "Permission is granted");
                return true;
            } else {

                Log.e("gg2", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("gg3", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("jj4", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
//            if(flagINoUT==1){
//                ExportDbToExternal();
//            }else if (flagINoUT==2){
//                ImportDbToMyApp();
//            }

            cameraIntent();

        }
    }



    public String bitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
//            byte[] encoded = Base64.encode(arr, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

            String result = Base64.encodeToString(arr, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            return result;
        }
        return "";
    }

    public void deleteFiles(String path) {
        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) {

            }
        }

    }

    void initial(){
        gallary=findViewById(R.id.gallary);
        imageLost=findViewById(R.id.imageLost);
        checkList=findViewById(R.id.checkList);
        onlyCheque=findViewById(R.id.onlyCheque);
        RangeEdText=findViewById(R.id.RangeEdText);
        RangeCheque=findViewById(R.id.RangeCheque);
        onlyCheque=findViewById(R.id.onlyCheque);
        upload=findViewById(R.id.upload);
    }


}
