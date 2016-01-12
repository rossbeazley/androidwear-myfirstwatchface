package uk.co.rossbeazley.watchview;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Constructor;

class ViewClass {
    private WatchFaceService watchFaceService;
    private WatchFaceService.RotateEngine rotateEngine;
    private Context context;

    public ViewClass(WatchFaceService watchFaceService, WatchFaceService.RotateEngine rotateEngine, Context context) {
        this.watchFaceService = watchFaceService;
        this.rotateEngine = rotateEngine;
        this.context = context;
    }

    public View construct() {
        View rtn = null;
        try {
            ServiceInfo applicationInfo = watchFaceService.getPackageManager().getServiceInfo(new ComponentName(context,WatchFaceService.class), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            String viewClass = metaData.getString("watchFaceViewClass");
            if(viewClass==null) return rtn;

            System.out.println("Going to construct" + viewClass);
            Class<?> aClass = Class.forName(viewClass);
            Class<Context> contextType = Context.class;
            Constructor<?> constructor = aClass.getConstructor(contextType);
            rtn = (View) constructor.newInstance(context);
            rotateEngine.log("Found" + aClass.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
