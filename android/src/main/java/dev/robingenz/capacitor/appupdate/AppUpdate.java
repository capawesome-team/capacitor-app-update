package dev.robingenz.capacitor.appupdate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.tasks.Task;

@NativePlugin
public class AppUpdate extends Plugin {
    private AppUpdateManager appUpdateManager;

    public void load() {
        this.appUpdateManager = AppUpdateManagerFactory.create(this.getContext());
    }

    @PluginMethod
    public void getAppUpdateInfo(PluginCall call) {
        Task<AppUpdateInfo> appUpdateInfoTask = this.appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            PackageInfo pInfo = null;
            try {
                pInfo = this.getPackageInfo();
            } catch (PackageManager.NameNotFoundException e) {
                call.reject("Unable to get App Info");
                return;
            }
            JSObject ret = new JSObject();
            ret.put("currentVersion", pInfo.versionCode);
            ret.put("availableVersion", appUpdateInfo.availableVersionCode());
            ret.put("updateAvailable", appUpdateInfo.updateAvailability());
            ret.put("updatePriority", appUpdateInfo.updatePriority());
            ret.put("appId", pInfo.packageName);
            call.resolve(ret);
        });
        appUpdateInfoTask.addOnFailureListener(failure -> {
            String message = failure.getMessage();
            call.reject(message);
        });
    }

    @PluginMethod
    public void openAppStore(PluginCall call) {
        String packageName = this.getContext().getPackageName();
        Intent launchIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        try {
            this.getBridge().getActivity().startActivity(launchIntent);
        } catch (ActivityNotFoundException ex) {
            launchIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            this.getBridge().getActivity().startActivity(launchIntent);
        }
        call.resolve();
    }

    private PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.getContext().getPackageName();
        return this.getContext().getPackageManager().getPackageInfo(packageName, 0);
    }
}
