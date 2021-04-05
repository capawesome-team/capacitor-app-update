package dev.robingenz.capacitor.appupdate;

import com.getcapacitor.JSObject;
import com.google.android.play.core.appupdate.AppUpdateInfo;

public interface AppUpdateInfoResultCallback {
    void onDone(AppUpdateInfo info);
    void onError(String message);
}
