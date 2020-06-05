package com.ybcphone.code.pi.ui.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ybcphone.code.pi.utils.Consts;
import com.ybcphone.myhttplibrary.utils.MyLog;
import com.ybcphone.myhttplibrary.utils.MyUtils;
import com.ybcphone.myhttplibrary.view.BaseActivity;
import com.ybcphone.code.pi.R;

import java.lang.ref.WeakReference;


/**
 * 登入
 *
 * @author forever
 */
public class WelcomeActivity extends BaseActivity {

    private final static int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 11;
    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 12;
    private final static int MY_PERMISSIONS_REQUEST_VIBRATE = 13;
    private final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 14;
    private final static int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 15;
    private final static int MY_PERMISSIONS_REQUEST_ALL = 16;


    //----------WeakReference Handler
    private static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> finalWeakObjct;

        public MyHandler(WelcomeActivity fromObject) {
            finalWeakObjct = new WeakReference<>(fromObject);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity thisObject = finalWeakObjct.get();
            if (thisObject != null) {
                //    —>   thisObject.
                switch (msg.what) {

                }
            }
        }
    }

    private final MyHandler myHandler = new MyHandler(this);
//----------WeakReference Handler--- end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.e(".============>     .onCreate   --->   " + this.getClass().getName());

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);


        ImageView activity_welocome_go = (ImageView) findViewById(R.id.activity_welocome_go);
        activity_welocome_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPP();
            }
        });
//        AnimationDrawable animationDrawable = (AnimationDrawable) activity_welocome_progress.getBackground();
//        animationDrawable.start();


    }


    private void startPP() {

        startService(new Intent(WelcomeActivity.this, FloatingPIButtonService.class));
        startService(new Intent(WelcomeActivity.this, FloatingFunctionBarService.class));

        Intent intent3 =
                getPackageManager()
                        .getLaunchIntentForPackage(
                                Consts.PACKAGE_NAME_spotify);
        startActivity(intent3);
    }


    private void requestPerim(String perm, int requestCode) {

        ActivityCompat.requestPermissions(WelcomeActivity.this,
                new String[]{perm},
                requestCode);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ALL:
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            case MY_PERMISSIONS_REQUEST_VIBRATE:
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    MyUtils.toastString(WelcomeActivity.this, "授權失敗,APP會有不正常情況！");

                }
                return;


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        askForSystemOverlayPermission();

    }


    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(WelcomeActivity.this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 234);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
