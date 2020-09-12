package com.example.agroapp.AddForm;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.Api.FeatureController;
import com.example.agroapp.Login.UserDetail;
import com.example.agroapp.R;

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
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA = 0;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102;
    EditText edt_seasonal, edt_irrigation, edt_soil, edt_growthdur, edt_area, edt_village, edt_tal, edt_dist, edt_state, edt_corptype;
    String season, irrigation, soil, growthdur, area, village, taluka, district, state, croptype;
    ImageView cropPRV, soilPRV, cropIcon, soilIcon;
    Bitmap bm, bm1;
    FeatureController featureController;
    String base64String = "";
    byte[] bb = null;
    String userid;
    UserDetail userDetail;
    private String userChoosenTask;

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
                    if (i == 1)
                        cameraIntent(1);
                    else if (i == 2)
                        cameraIntent(2);
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (i == 1)
                        galleryIntent(1);
                    if (i == 2)
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
                onSelectFromGalleryResult1(data, 2);
            else if (requestCode == 1)
                onSelectFromGalleryResult(data, 1);
            else if (requestCode == 3)
                onCaptureImageResult(data, 3);
            else if (requestCode == 4)
                onCaptureImageResult1(data, 4);

            //   else if (requestCode == 1)
            //     onCaptureImageResult(data);
        }
    }

    private boolean checkExtPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
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
        if (i == 1) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
        if (i == 2) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }

    }

    private void cameraIntent(int i) {
        if (i == 1) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            hasPermissionInManifest(this, MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 3);
        } else if (i == 2) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            hasPermissionInManifest(this, MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 4);
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(getApplicationContext(), "To access Camera permission, Please allow in App Settings for camera functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }


    private void init() {
        cropPRV = findViewById(R.id.imgPRV_crop);
        soilIcon = findViewById(R.id.soil_icon);
        soilPRV = findViewById(R.id.imgPRV_soil);
        cropIcon = findViewById(R.id.crop_icon);
        edt_area = findViewById(R.id.edt_croparea);
        edt_dist = findViewById(R.id.edt_district);
        edt_growthdur = findViewById(R.id.growthdur);
        edt_irrigation = findViewById(R.id.edt_irrigation);
        edt_soil = findViewById(R.id.edt_soiltype);
        edt_seasonal = findViewById(R.id.edt_season);
        edt_tal = findViewById(R.id.edt_taluka);
        edt_corptype = findViewById(R.id.edt_corptype);
        edt_village = findViewById(R.id.edt_village);
        edt_state = findViewById(R.id.edt_state);

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
            e.printStackTrace();
        }
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
        season = edt_seasonal.getText().toString();
        irrigation = edt_irrigation.getText().toString();
        soil = edt_soil.getText().toString();
        growthdur = edt_growthdur.getText().toString();
        area = edt_area.getText().toString();
        village = edt_village.getText().toString();
        taluka = edt_tal.getText().toString();
        district = edt_dist.getText().toString();
        state = edt_state.getText().toString();
        croptype = edt_corptype.getText().toString();
        userid = FeatureController.getInstance().getUserDetail().get(0).getId();
        submitdata();


    }

    private void submitdata() {
        AuthenticationApi api = ApiClient.getClient().create(AuthenticationApi.class);
        AddFormDataInput i = new AddFormDataInput();
        i.setOperation("form_data_added");
        i.setApiKey("cda11aoip2Ry07CGWmjEqYvPguMZTkBel1V8c3XKIxwA6zQt5s");
        i.setGiolocation("");
        i.setTime("");
        String cropimg = getBase64(bm);
        i.setCropImage(cropimg);
        String soilimg = getBase64(bm1);
        i.setSoilImage(soilimg);
        i.setSessionalCondition(season);
        i.setIrrigationType(irrigation);
        i.setSoilType(soil);
        i.setTypeOfCrop(croptype);
        i.setGrouthDuration(growthdur);
        i.setArea(area);
        i.setVillage(village);
        i.setTaluka(taluka);
        i.setDistrict(district);
        i.setState(state);
        i.setUserId(userid);
        Call<AddFormDatatOutput> call = api.sendData(i);
        call.enqueue(new Callback<AddFormDatatOutput>() {
            @Override
            public void onResponse(Call<AddFormDatatOutput> call, Response<AddFormDatatOutput> response) {
                if (response.body() != null) {
                    if (response.body().getResponseStatus() == 200) {
                        Toast.makeText(AddFormData.this, "Data Uploded Sucessfully .  " + response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddFormData.this, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(AddFormData.this, "Server Error!!!!!!!" + response.body().getResponseMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AddFormDatatOutput> call, Throwable t) {

            }
        });
    }
}
