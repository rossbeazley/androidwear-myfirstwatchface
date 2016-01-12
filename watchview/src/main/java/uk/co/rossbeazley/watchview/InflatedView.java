package uk.co.rossbeazley.watchview;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

class InflatedView {
    private WatchFaceService watchFaceService;
    private WatchViewRoot viewRoot;

    public InflatedView(WatchFaceService watchFaceService, WatchViewRoot viewRoot) {
        this.watchFaceService = watchFaceService;
        this.viewRoot = viewRoot;
    }

    public View infate() {
        View rtn = null;
        try {
            ServiceInfo applicationInfo = watchFaceService.getPackageManager().getServiceInfo(new ComponentName(viewRoot.getContext(), WatchFaceService.class), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            System.out.println("Going to try inflating");

            int layoutId = metaData.getInt("watchFaceViewLayout");
            System.out.println("Going to inflate " + layoutId);
            LayoutInflater layoutInflater = LayoutInflater.from(viewRoot.getContext());
            rtn = layoutInflater.inflate(layoutId, viewRoot, false);
        } catch (Exception e) {

        }

        return rtn;
    }
}
