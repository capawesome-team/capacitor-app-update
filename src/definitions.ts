import type { PluginListenerHandle } from "@capacitor/core";

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
   performImmediateUpdate(): Promise<AppUpdateResult>;
   /**
    * Supported platform(s): Android
    * Starts a flexible in-app update.
    */
   startFlexibleUpdate(): Promise<AppUpdateResult>;
   /**
    * Supported platform(s): Android
    * Completes a flexible in-app update by restarting the app.
    */
   completeFlexibleUpdate(): Promise<void>;
   /**
    * Adds a flexbile in-app update state change listener.
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
   availableVersion: string;
   /**
    * Supported platform(s): iOS
    * Release date of the update in ISO 8601 (UTC) format.
    */
   availableVersionReleaseDate?: string;
   /**
    * Supported platform(s): Android, iOS
    * The app update availability.
    */
   updateAvailability: AppUpdateAvailability;
   /**
    * Supported platform(s): Android
    * In-app update priority for this update, as defined by the developer in the Google Play Developer API.
    */
   updatePriority?: number;
   /**
    * Supported platform(s): Android
    * `true` if an immediate update is allowed, otherwise `false`.
    */
   immediateUpdateAllowed?: boolean;
   /**
    * Supported platform(s): Android
    * `true` if a flexible update is allowed, otherwise `false`.
    */
   flexibleUpdateAllowed?: boolean;
 }
 
 export enum AppUpdateAvailability {
   UNKNOWN = 0,
   UPDATE_NOT_AVAILABLE = 1,
   UPDATE_AVAILABLE = 2,
   UPDATE_IN_PROGRESS = 3,
 }
 
 export interface FlexibleUpdateState {
   /**
    * Flexible in-app update install status.
    */
   installStatus: FlexibleUpdateInstallStatus;
   /**
    * Returns the number of bytes downloaded so far.
    * `undefined` if the install status is other than `DOWNLOADING`.
    */
   bytesDownloaded: number | undefined;
   /**
    * Returns the total number of bytes to be downloaded for this update.
    * `undefined` if the install status is other than `DOWNLOADING`.
    */
   totalBytesToDownload: number | undefined;
 }
 
 export enum FlexibleUpdateInstallStatus {
   UNKNOWN = 0,
   PENDING = 1,
   DOWNLOADING = 2,
   INSTALLING = 3,
   INSTALLED = 4,
   FAILED = 5,
   CANCELED = 6,
   DOWNLOADED = 11,
 }
 
 export interface AppUpdateResult {
   code: AppUpdateResultCode;
 }
 
 export enum AppUpdateResultCode {
   /**
    * The user has accepted the update.
    */
   OK = 0,
   /**
    * The user has denied or cancelled the update.
    */
   CANCELED = 1,
   /**
    * Some other error prevented either the user from providing consent or the update to proceed.
    */
   FAILED = 2,
   /**
    * No update available.
    */
   NOT_AVAILABLE = 3,
   /**
    * Update type not allowed.
    */
   NOT_ALLOWED = 4,
   /**
    * App update info missing.
    * You must call `getAppUpdateInfo()` before requesting an update.
    */
   INFO_MISSING = 5,
 }
 