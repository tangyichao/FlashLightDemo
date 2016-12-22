package com.tang.make.flashlightdemo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

/**
 * 闪光灯控制器工具类
 *
 * @author tangyc
 */
public class FlashLightUtils {

    private static Camera sCamera;

    public FlashLightUtils() {
        try {
            sCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化闪光灯
     *
     * @param context
     *
     * @return
     */
    public static Camera getCamera(Context context) {
        if (!hasFlash(context))
            return null;
        if (sCamera == null) {
            sCamera = Camera.open();
        }
        return sCamera;
    }

    private static void ensureCamera(Context context) {
        getCamera(context);
    }

    /**
     * 打开闪光灯
     *
     * @param context
     */
    @SuppressLint("NewApi")
    public static void openFlash(Context context) throws Exception {
        ensureCamera(context);
        if (sCamera == null)
            return;
        try {
            Camera.Parameters parameters = sCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            sCamera.setParameters(parameters);
//            if (android.os.Build.VERSION.SDK_INT >= 11) {
//                sCamera.setPreviewTexture(new SurfaceTexture(0)); // 11 8
//            }

            sCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static boolean isOpen() {
        boolean isOpen = false;
        if (sCamera == null)
            return false;
        try {
            Camera.Parameters p = sCamera.getParameters();
            String openString = p.getFlashMode();// 获取闪光灯的状态
            Log.i("Tag",openString); //torch
            if (openString.equals(Parameters.FLASH_MODE_TORCH)) {
                isOpen = true;//关闭
            } else {
                isOpen = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isOpen = false;
        }
        return isOpen;
    }

    /**
     * 关闭闪光灯
     */
    public static void closeFlash() {
        if (sCamera == null)
            return;
        try {
            Camera.Parameters parameters = sCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            sCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放
     */
    public static void release() {
        if (sCamera != null) {
            sCamera.release();
            sCamera = null;
        }
    }

    /**
     * 检查设备是否存在闪光灯
     *
     * @param context
     * @return
     */
    public static boolean hasFlash(Context context) {
        PackageManager pm = context.getPackageManager();
        FeatureInfo[] featureInfos = pm.getSystemAvailableFeatures();
        for (FeatureInfo f : featureInfos) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                return true;
            }
        }
        return false;
    }

}
