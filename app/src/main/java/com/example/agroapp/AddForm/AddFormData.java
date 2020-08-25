package com.example.agroapp.AddForm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.R;
import com.example.agroapp.Registration.RegistartionInput;
import com.example.agroapp.Registration.RegistrationActivity;
import com.example.agroapp.Registration.RegistrationOutput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFormData extends AppCompatActivity {
    ImageView cropPRV,soilPRV,cropIcon,soilIcon;
    Bitmap bm,bm1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String userChoosenTask;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102;
    String base64String = "";
    private static final int REQUEST_CAMERA = 0;
    byte[] bb = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form_data);
        init();

    }

    private void openOptoinDialog(final int i) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(AddFormData.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (i==1)
                        cameraIntent(1);
                    else if (i==2)
                        cameraIntent(2);
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (i==1)
                        galleryIntent(1);
                    if (i==2)
                        galleryIntent(2);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult1(data,2);
            else if (requestCode==1)
                onSelectFromGalleryResult(data,1);
            else  if (requestCode==3)
                onCaptureImageResult(data,3);
            else  if (requestCode==4)
                onCaptureImageResult1(data,4);

            //   else if (requestCode == 1)
            //     onCaptureImageResult(data);
        }
    }
    private boolean checkExtPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void onCaptureImageResult1(Intent data, int i) {

        Bitmap bm1 = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm1.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo = null;
        try {

            boolean hasPermission = checkExtPermission();
            if (hasPermission == true) {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
            } else {
                requestExtPermission();
            }
            if (fo != null) {
                fo.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }
        soilPRV.setImageBitmap(bm1);
        getBase64(bm1);


    }

    private String getBase64(Bitmap bm1) {
        if (bm != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //  bmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bb = bos.toByteArray();
            base64String = Base64.encodeToString(bb, Base64.DEFAULT);
        }
        Log.i("Image", base64String);
        return base64String;
    }

    private void requestExtPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), "To access external write permission, Please allow in App Settings to write to external storage functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    private void onCaptureImageResult(Intent data, int i) {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo = null;
        try {

            boolean hasPermission = checkExtPermission();
            if (hasPermission == true) {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
            } else {
                requestExtPermission();
            }
            if (fo != null) {
                fo.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }
        cropPRV.setImageBitmap(bm);
        getBase64(bm);

    }

    private void onSelectFromGalleryResult(Intent data, int i) {
        bm = null;
        if (data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bm = BitmapFactory.decodeStream(imageStream);
                bm = getResizedBitmap(bm, 400);
                cropPRV.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        getBase64(bm);

    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void onSelectFromGalleryResult1(Intent data, int i) {
        bm1 = null;
        if (data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bm1 = BitmapFactory.decodeStream(imageStream);
                bm1 = getResizedBitmap(bm1, 400);
                soilPRV.setImageBitmap(bm1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        getBase64(bm1);

    }

    private void galleryIntent(int i) {
        if (i==1)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
        if (i==2)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }

    }

    private void cameraIntent(int i) {
        if (i==1)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            hasPermissionInManifest(this, MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 3);
        }
        else if (i==2){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            hasPermissionInManifest(this, MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 4);
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(getApplicationContext(), "To access Camera permission, Please allow in App Settings for camera functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }


    private void init() {
        cropPRV=findViewById(R.id.imgPRV_crop);
        soilIcon=findViewById(R.id.soil_icon);
        soilPRV=findViewById(R.id.imgPRV_soil);
        cropIcon=findViewById(R.id.crop_icon);
    }
    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(); }
        return false;
    }

    public void oncropImage(View view) {
        if (checkPermission()) {
            openOptoinDialog(1);
        } else {
            requestPermission();
        }

    }

    public void onSoilimage(View view) {
        if (checkPermission()) {
            openOptoinDialog(2);
        } else {
            requestPermission();
        }

    }

    public void onSubmit(View view) {
        AuthenticationApi api= ApiClient.getClient().create(AuthenticationApi.class);
        AddFormDataInput i=new AddFormDataInput();
        i.setOperation("");
        i.setApiKey("");
        i.setGiolocation("");
        i.setTime("");
        i.setCropImage("");
        i.setSoilImage("");
        i.setSessionalCondition("");
        i.setIrrigationType("");
        i.setSoilType("");
        i.setTypeOfCrop("");
        i.setGrouthDuration("");
        i.setArea("");
        i.setVillage("");
        i.setTaluka("");
        i.setDistrict("");
        i.setState("");
        i.setUserId("");
        Call<AddFormDatatOutput> call=api.sendData(i);
        call.enqueue(new Callback<AddFormDatatOutput>() {
            @Override
            public void onResponse(Call<AddFormDatatOutput> call, Response<AddFormDatatOutput> response) {
                if (response.body()!=null){
                    if (response.body().getResponseStatus()==200){
                        Toast.makeText(AddFormData.this,"Data Uploded Sucessfully .  "+response.body().getResponseMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(AddFormData.this,response.body().getResponseMessage().toString(),Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(AddFormData.this,"Server Error!!!!!!!"+response.body().getResponseMessage().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AddFormDatatOutput> call, Throwable t) {

            }
        });

    }
}