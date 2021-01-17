declare module '@capacitor/core' {
  interface PluginRegistry {
    AppUpdate: AppUpdatePlugin;
  }
}

export interface AppUpdatePlugin {
  /**
   * Supported platform(s): Android, iOS  
   * Returns app update informations.  
   */
  getAppUpdateInfo(): Promise<AppUpdateInfo>;
  /**
   * Supported platform(s): Android, iOS  
   * Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).  
   */
  openAppStore(): Promise<void>;
  /**
   * Supported platform(s): Android  
   * Request an in-app update.  
   */
  startInAppUpdate(): Promise<void>;
}

export interface AppUpdateInfo {
  /**
   * Supported platform(s): Android, iOS  
   * Version code (Android) or CFBundleShortVersionString (iOS) of the currently installed app version.  
   */
  currentVersion: string;
  /**
   * Supported platform(s): Android, iOS  
   * Version code (Android) or CFBundleShortVersionString (iOS) of the update.  
   */
  availableVersion?: string;
  /**
   * Supported platform(s): iOS  
   * Release date of the update in ISO 8601 (UTC) format.  
   */
  availableVersionReleaseDate?: string;
  /**
   * Supported platform(s): Android, iOS  
   * `true` if the available version is higher than the current one, otherwise `false`.  
   */
  updateAvailable: boolean;
  /**
   * Supported platform(s): Android, iOS  
   * Package name (Android) or Bundle identifier (iOS).  
   */
  packageName: string;
}
