package com.tang.make.flashlightdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * Created by tangyc on 2016/1/4.
 */
public class FBService extends Service implements View.OnClickListener, SurfaceHolder.Callback {

    private WindowManager wManager;// 窗口管理者
    private WindowManager.LayoutParams mParams;// 窗口的属性

    private View windowView;
    private SurfaceHolder holder;
    public static final String ACTION_ALPHA = "com.fb.alpha";

    @Override
    public void onCreate() {
        super.onCreate();
        wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;// 系统提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        // mParams.format = PixelFormat.RGBA_8888;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;// 窗口的宽和高
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.START | Gravity.TOP;
        mParams.y = 100;
        mParams.x = 100;
        mParams.windowAnimations = android.R.style.Animation_Toast;
        // mParams.alpha = 0.8f;//窗口的透明度

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        windowView = layoutInflater.inflate(R.layout.float_button_layout, null);
        // ivFlashLight = (ImageView)
        // windowView.findViewById(R.id.iv_flashlight);
        SurfaceView localSurfaceView = (SurfaceView) windowView.findViewById(R.id.sfPreview);
        this.holder = localSurfaceView.getHolder();
        this.holder.addCallback(this);
        windowView.setVisibility(View.VISIBLE);
        wManager.addView(windowView, mParams);// 添加窗口
        windowView.findViewById(R.id.iv_icon).setOnClickListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon:
                //手电
                if(!FlashLightUtils.isOpen()){

                    try {
                        FlashLightUtils.openFlash(this);
                        Toast.makeText(this,"open flashlight",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this,"open flashlight fail",Toast.LENGTH_LONG).show();
                    }
                }else{
                    try {
                        FlashLightUtils.closeFlash();
                        Toast.makeText(this,"close flashlight",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this,"close flashlight",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                break;

        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
