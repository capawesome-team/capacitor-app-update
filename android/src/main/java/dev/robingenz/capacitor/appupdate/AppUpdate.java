package dev.robingenz.capacitor.appupdate;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

public class AppUpdate {
    private Context context;
    private Bridge bridge;
    private AppUpdateManager appUpdateManager;
    private AppUpdateInfo appUpdateInfo = null;
    private InstallStateUpdatedListener listener;

    AppUpdate(Context context, Bridge bridge) {
        this.context = context;
        this.appUpdateManager = AppUpdateManagerFactory.create(context);
    }

    public void getAppUpdateInfo(AppUpdateInfoResultCallback resultCallback) {
        Task<AppUpdateInfo> appUpdateInfoTask = this.appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(
            appUpdateInfo -> {
                this.appUpdateInfo = appUpdateInfo;
                resultCallback.onDone(appUpdateInfo);
            }
        );
        appUpdateInfoTask.addOnFailureListener(
            failure -> {
                String message = failure.getMessage();
                resultCallback.onError(message);
            }
        );
    }

    public AppUpdateInfo getLastAppUpdateInfo() {
        return this.appUpdateInfo;
    }

    public void openAppStore() {
        String packageName = this.context.getPackageName();
        Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        try {
            this.bridge.getActivity().startActivity(launchIntent);
        } catch (ActivityNotFoundException ex) {
            launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            this.bridge.getActivity().startActivity(launchIntent);
        }
    }

    public void performImmediateUpdate() throws IntentSender.SendIntentException {
        this.appUpdateManager.startUpdateFlowForResult(this.appUpdateInfo, AppUpdateType.IMMEDIATE, bridge.getActivity(), 0);
    }

    public PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.context.getPackageName();
        return this.context.getPackageManager().getPackageInfo(packageName, 0);
    }
}
