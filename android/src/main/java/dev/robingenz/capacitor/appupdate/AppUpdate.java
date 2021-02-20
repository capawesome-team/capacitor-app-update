package dev.robingenz.capacitor.appupdate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
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
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;

@NativePlugin(
        requestCodes={AppUpdate.REQUEST_IMMEDIATE_UPDATE, AppUpdate.REQUEST_FLEXIBLE_UPDATE}
)
public class AppUpdate extends Plugin {
    /** Request code for immediate update */
    protected static final int REQUEST_IMMEDIATE_UPDATE = 10;
    /** Request code for flexible update */
    protected static final int REQUEST_FLEXIBLE_UPDATE = 11;
    /** Update result: update ok. */
    public static final int UPDATE_OK = 0;
    /** Update result: update canceled. */
    public static final int UPDATE_CANCELED = 1;
    /** Update result: update failed. */
    public static final int UPDATE_FAILED = 2;
    /** Update result: update not available. */
    public static final int UPDATE_NOT_AVAILABLE = 4;
    /** Update result: update not allowed. */
    public static final int UPDATE_NOT_ALLOWED = 5;
    /** Update result: update info missing. */
    public static final int UPDATE_INFO_MISSING = 6;
    private AppUpdateManager appUpdateManager;
    private AppUpdateInfo appUpdateInfo;
    private InstallStateUpdatedListener listener;

    public void load() {
        this.appUpdateManager = AppUpdateManagerFactory.create(this.getContext());
    }

    @PluginMethod
    public void getAppUpdateInfo(PluginCall call) {
        Task<AppUpdateInfo> appUpdateInfoTask = this.appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            this.appUpdateInfo = appUpdateInfo;
            PackageInfo pInfo = null;
            try {
                pInfo = this.getPackageInfo();
            } catch (PackageManager.NameNotFoundException e) {
                call.reject("Unable to get App Info");
                return;
            }
            JSObject ret = new JSObject();
            ret.put("currentVersion", String.valueOf(pInfo.versionCode));
            ret.put("availableVersion", String.valueOf(appUpdateInfo.availableVersionCode()));
            ret.put("updateAvailability", appUpdateInfo.updateAvailability());
            ret.put("updatePriority", appUpdateInfo.updatePriority());
            ret.put("immediateUpdateAllowed", appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE));
            ret.put("flexibleUpdateAllowed", appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE));
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

    @PluginMethod
    public void performImmediateUpdate(PluginCall call) {
        boolean ready = this.readyForUpdate(call, AppUpdateType.IMMEDIATE);
        if (!ready) {
            return;
        }
        saveCall(call);
        try {
            this.appUpdateManager.startUpdateFlowForResult(this.appUpdateInfo, AppUpdateType.IMMEDIATE, getActivity(), 0);
        } catch (IntentSender.SendIntentException e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void startFlexibleUpdate(PluginCall call) {
        boolean ready = this.readyForUpdate(call, AppUpdateType.IMMEDIATE);
        if (!ready) {
            return;
        }
        saveCall(call);
        try {
            this.listener = state -> {
                int installStatus = state.installStatus();
                JSObject ret = new JSObject();
                ret.put("installStatus", installStatus);
                if (installStatus == InstallStatus.DOWNLOADING) {
                    ret.put("bytesDownloaded", state.bytesDownloaded());
                    ret.put("totalBytesToDownload", state.totalBytesToDownload());
                }
                notifyListeners("onFlexibleUpdateStateChange", ret);
            };
            this.appUpdateManager.registerListener(this.listener);
            this.appUpdateManager.startUpdateFlowForResult(this.appUpdateInfo, AppUpdateType.FLEXIBLE, getActivity(), 0);
        } catch (IntentSender.SendIntentException e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void completeFlexibleUpdate(PluginCall call) {
        this.appUpdateManager.completeUpdate();
        call.resolve();
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            return;
        }
        JSObject ret = new JSObject();
        if (resultCode == RESULT_OK) {
            ret.put("code", this.UPDATE_OK);
        } else if (resultCode == RESULT_CANCELED) {
            ret.put("code", this.UPDATE_CANCELED);
        } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
            ret.put("code", this.UPDATE_FAILED);
        }
        savedCall.resolve(ret);
        if (requestCode == REQUEST_FLEXIBLE_UPDATE) {
            this.appUpdateManager.unregisterListener(this.listener);
            this.listener = null;
        }
        this.appUpdateInfo = null;
    }

    private PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.getContext().getPackageName();
        return this.getContext().getPackageManager().getPackageInfo(packageName, 0);
    }

    private boolean readyForUpdate(PluginCall call, int appUpdateType) {
        JSObject ret = new JSObject();
        if (this.appUpdateInfo == null) {
            ret.put("code", this.UPDATE_INFO_MISSING);
            call.resolve(ret);
            return false;
        }
        if (this.appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            ret.put("code", this.UPDATE_NOT_AVAILABLE);
            call.resolve(ret);
            return false;
        }
        if (appUpdateInfo.isUpdateTypeAllowed(appUpdateType) == false) {
            ret.put("code", this.UPDATE_NOT_ALLOWED);
            call.resolve(ret);
            return false;
        }
        return true;
    }
}
