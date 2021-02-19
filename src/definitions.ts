import { PluginListenerHandle } from "@capacitor/core";

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
   * Performs an immediate in-app update.
   */
  performImmediateUpdate(): Promise<void>;
  /**
   * Supported platform(s): Android  
   * Starts a flexible in-app update.
   */
  startFlexibleUpdate(options: AppUpdateOptions): Promise<void>;
  /**
   * Supported platform(s): Android  
   * Completes a flexible in-app update by restarting the app.  
   */
  completeFlexibleUpdate(): Promise<void>;
  /**
   * Flexbile in-app update state change.
   */
  addListener(
    eventName: 'onFlexibleUpdateStateChange',
    listenerFunc: (state: FlexibleUpdateState) => void,
  ): PluginListenerHandle;
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
  availableVersion: string | null;
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
   * Supported platform(s): Android
   * In-app update priority for this update, as defined by the developer in the Google Play Developer API.
   */
  updatePriority?: number;
  /**
   * Supported platform(s): Android, iOS  
   * Package name (Android) or Bundle identifier (iOS).
   */
  appId: string;
}

export interface FlexibleUpdateState {
  /**
   * Flexible in-app update install status.
   */
  installStatus: FlexibleUpdateInstallStatus;
  bytesDownloaded: number;
  totalBytesToDownload: number;
}

export enum FlexibleUpdateInstallStatus {
  CANCELED = 6,
  DOWNLOADED = 11,
  DOWNLOADING = 2,
  FAILED = 5,
  INSTALLED = 4,
  INSTALLING = 3,
  PENDING = 1,
  UNKNOWN = 0,
}

export enum AppUpdateType {
  IMMEDIATE = 1,
  FLEXIBLE = 0
}

export interface AppUpdateOptions {
  type: AppUpdateType
}