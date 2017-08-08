package com.example.hafizit.test;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;
import static android.R.attr.data;
import static com.example.hafizit.test.AppController.TAG;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tt", "tet");
        TextView hello = (TextView) findViewById(R.id.hello_world);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        hello.setOnClickListener(new View.OnClickListener() {
            public static final int CAMERA_PERMISSION = 1;

            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    } else {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }
                } catch (Exception e){
                    Log.e("error", e.toString());
                }
            }
        });

        final String tag_json_obj = "json_obj_req";

        final String url = "https://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();


// Adding request to request queue
        Thread ex = new Thread(
                new Runnable(){
                    public void run(){
                        for(int i = 0; i < 20; i++){
                            Log.d("urutan", String.valueOf(i));
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                    url, null,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                                            // Vibrate for 500 milliseconds
//                                            v.vibrate(500);
                                            try{
                                                Log.d("name", response.getString("name"));
                                            } catch (Exception e){
                                                Log.e("error", e.toString());
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("name", "Androidhive");
                                    params.put("email", "abc@androidhive.info");
                                    params.put("password", "password123");

                                    return params;
                                }

                            };
                            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                            try{
                                Thread.sleep(6000);
                            } catch (Exception e){
                                Log.d("error", e.toString());
                            }
                        }
                    }
                }
        );
        ex.start();
//        for(int i = 0; i < 10; i++) {
//
//            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
////            AppController.getInstance().addToRequestQueue(jsonObjReq);
////            Log.d("urutan", String.valueOf(i));
//
//        }
        pDialog.hide();
    }
//    private String mCameraId;
//    private Size mPreviewSize;
//    private CameraDevice mCameraDevice;
//    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
//
//        @Override
//        public void onOpened(CameraDevice camera) {
//            mCameraDevice = camera;
//            Toast.makeText(getApplicationContext(), "Camera Opened!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onDisconnected(CameraDevice camera) {
//            camera.close();
//            mCameraDevice = null;
//        }
//
//        @Override
//        public void onError(CameraDevice camera, int error) {
//            camera.close();
//            mCameraDevice = null;
//        }
//    };
//    private void setupCamera(int width, int height) {
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            for(String cameraId : cameraManager.getCameraIdList()) {
//                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
//                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
//                        CameraCharacteristics.LENS_FACING_FRONT) {
//                    continue;
//                }
//                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                mPreviewSize = getPreferredPreviewSize(map.getOutputSizes(SurfaceTexture.class), width, height);
//                mCameraId = cameraId;
//                return;
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Size getPreferredPreviewSize(Size[] mapSizes, int width, int height) {
//        List<Size> collectorSizes = new ArrayList<>();
//        for(Size option : mapSizes) {
//            if(width > height) {
//                if(option.getWidth() > width &&
//                        option.getHeight() > height) {
//                    collectorSizes.add(option);
//                }
//            } else {
//                if(option.getWidth() > height &&
//                        option.getHeight() > width) {
//                    collectorSizes.add(option);
//                }
//            }
//        }
//        if(collectorSizes.size() > 0) {
//            return Collections.min(collectorSizes, new Comparator<Size>() {
//                @Override
//                public int compare(Size lhs, Size rhs) {
//                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
//                }
//            });
//        }
//        return mapSizes[0];
//    }
//
//    private void openCamera() {
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(photo);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.this.imageView.setVisibility(View.INVISIBLE);

                                    }
                                });


                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
            try {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);
                mBuilder.setSmallIcon(R.id.imageView);
                mBuilder.setContentTitle("Notification Alert, Click Me!");
                mBuilder.setContentText("Hi, This is Android Notification Detail!");
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
