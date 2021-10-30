<p align="center"><br><img src="https://user-images.githubusercontent.com/236501/85893648-1c92e880-b7a8-11ea-926d-95355b8175c7.png" width="128" height="128" /></p>
<h3 align="center">App Update</h3>
<p align="center"><strong><code>@robingenz/capacitor-app-update</code></strong></p>
<p align="center">
  Capacitor plugin that assists with app updates.
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2021?style=flat-square" />
  <a href="https://github.com/robingenz/capacitor-app-update/actions?query=workflow%3A%22CI%22"><img src="https://img.shields.io/github/workflow/status/robingenz/capacitor-app-update/CI/main?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@robingenz/capacitor-app-update"><img src="https://img.shields.io/npm/l/@robingenz/capacitor-app-update?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/@robingenz/capacitor-app-update"><img src="https://img.shields.io/npm/dw/@robingenz/capacitor-app-update?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@robingenz/capacitor-app-update"><img src="https://img.shields.io/npm/v/@robingenz/capacitor-app-update?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors-"><img src="https://img.shields.io/badge/all%20contributors-1-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

This plugin supports retrieving app update information on **Android** and **iOS**.  
Additionally, this plugin supports [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) on **Android**.

## Maintainers

| Maintainer | GitHub                                    | Social                                        |
| ---------- | ----------------------------------------- | --------------------------------------------- |
| Robin Genz | [robingenz](https://github.com/robingenz) | [@robin_genz](https://twitter.com/robin_genz) |

## Installation

```bash
npm install @robingenz/capacitor-app-update
npx cap sync
```

### Android Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidPlayCore` version of `com.google.android.play:core` (default: `1.9.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { AppUpdate } from '@robingenz/capacitor-app-update';

const getCurrentAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  return result.currentVersion;
};

const getAvailableAppVersion = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  return result.availableVersion;
};

const openAppStore = async () => {
  await AppUpdate.openAppStore();
};

const performImmediateUpdate = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (result.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (result.immediateUpdateAllowed) {
    await AppUpdate.performImmediateUpdate();
  }
};

const startFlexibleUpdate = async () => {
  const result = await AppUpdate.getAppUpdateInfo();
  if (result.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (result.flexibleUpdateAllowed) {
    await AppUpdate.startFlexibleUpdate();
  }
};

const completeFlexibleUpdate = async () => {
  await AppUpdate.completeFlexibleUpdate();
};
```

## API

<docgen-index>

* [`getAppUpdateInfo(...)`](#getappupdateinfo)
* [`openAppStore(...)`](#openappstore)
* [`performImmediateUpdate()`](#performimmediateupdate)
* [`startFlexibleUpdate()`](#startflexibleupdate)
* [`completeFlexibleUpdate()`](#completeflexibleupdate)
* [`addListener('onFlexibleUpdateStateChange', ...)`](#addlisteneronflexibleupdatestatechange-)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAppUpdateInfo(...)

```typescript
getAppUpdateInfo(options?: GetAppUpdateInfoOptions | undefined) => Promise<AppUpdateInfo>
```

Returns app update informations.

Only available for Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getappupdateinfooptions">GetAppUpdateInfoOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#appupdateinfo">AppUpdateInfo</a>&gt;</code>

--------------------


### openAppStore(...)

```typescript
openAppStore(options?: OpenAppStoreOptions | undefined) => Promise<void>
```

Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).

Only available for Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#openappstoreoptions">OpenAppStoreOptions</a></code> |

--------------------


### performImmediateUpdate()

```typescript
performImmediateUpdate() => Promise<AppUpdateResult>
```

Performs an immediate in-app update.

Only available for Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### startFlexibleUpdate()

```typescript
startFlexibleUpdate() => Promise<AppUpdateResult>
```

Starts a flexible in-app update.

Only available for Android.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

--------------------


### completeFlexibleUpdate()

```typescript
completeFlexibleUpdate() => Promise<void>
```

Completes a flexible in-app update by restarting the app.

Only available for Android.

--------------------


### addListener('onFlexibleUpdateStateChange', ...)

```typescript
addListener(eventName: 'onFlexibleUpdateStateChange', listenerFunc: (state: FlexibleUpdateState) => void) => PluginListenerHandle
```

Adds a flexbile in-app update state change listener.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onFlexibleUpdateStateChange'</code>                                              |
| **`listenerFunc`** | <code>(state: <a href="#flexibleupdatestate">FlexibleUpdateState</a>) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### AppUpdateInfo

| Prop                              | Type                                                                    | Description                                                                                                                            |
| --------------------------------- | ----------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **`currentVersion`**              | <code>string</code>                                                     | Version code (Android) or CFBundleShortVersionString (iOS) of the currently installed app version. Only available for Android and iOS. |
| **`availableVersion`**            | <code>string</code>                                                     | Version code (Android) or CFBundleShortVersionString (iOS) of the update. Only available for Android and iOS.                          |
| **`availableVersionReleaseDate`** | <code>string</code>                                                     | Release date of the update in ISO 8601 (UTC) format. Only available for iOS.                                                           |
| **`updateAvailability`**          | <code><a href="#appupdateavailability">AppUpdateAvailability</a></code> | The app update availability. Only available for Android and iOS.                                                                       |
| **`updatePriority`**              | <code>number</code>                                                     | In-app update priority for this update, as defined by the developer in the Google Play Developer API. Only available for Android.      |
| **`immediateUpdateAllowed`**      | <code>boolean</code>                                                    | `true` if an immediate update is allowed, otherwise `false`. Only available for Android.                                               |
| **`flexibleUpdateAllowed`**       | <code>boolean</code>                                                    | `true` if a flexible update is allowed, otherwise `false`. Only available for Android.                                                 |


#### GetAppUpdateInfoOptions

| Prop          | Type                | Description                                                                                                                                                                |
| ------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`country`** | <code>string</code> | The two-letter country code for the store you want to search. See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes. Only available for iOS. |


#### OpenAppStoreOptions

| Prop          | Type                | Description                                                                                                                                                                |
| ------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`country`** | <code>string</code> | The two-letter country code for the store you want to search. See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes. Only available for iOS. |


#### AppUpdateResult

| Prop       | Type                                                                |
| ---------- | ------------------------------------------------------------------- |
| **`code`** | <code><a href="#appupdateresultcode">AppUpdateResultCode</a></code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### FlexibleUpdateState

| Prop                       | Type                                                                                | Description                                                                                                                        |
| -------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **`installStatus`**        | <code><a href="#flexibleupdateinstallstatus">FlexibleUpdateInstallStatus</a></code> | Flexible in-app update install status.                                                                                             |
| **`bytesDownloaded`**      | <code>number</code>                                                                 | Returns the number of bytes downloaded so far. `undefined` if the install status is other than `DOWNLOADING`.                      |
| **`totalBytesToDownload`** | <code>number</code>                                                                 | Returns the total number of bytes to be downloaded for this update. `undefined` if the install status is other than `DOWNLOADING`. |


### Enums


#### AppUpdateAvailability

| Members                    | Value          |
| -------------------------- | -------------- |
| **`UNKNOWN`**              | <code>0</code> |
| **`UPDATE_NOT_AVAILABLE`** | <code>1</code> |
| **`UPDATE_AVAILABLE`**     | <code>2</code> |
| **`UPDATE_IN_PROGRESS`**   | <code>3</code> |


#### AppUpdateResultCode

| Members             | Value          | Description                                                                                 |
| ------------------- | -------------- | ------------------------------------------------------------------------------------------- |
| **`OK`**            | <code>0</code> | The user has accepted the update.                                                           |
| **`CANCELED`**      | <code>1</code> | The user has denied or cancelled the update.                                                |
| **`FAILED`**        | <code>2</code> | Some other error prevented either the user from providing consent or the update to proceed. |
| **`NOT_AVAILABLE`** | <code>3</code> | No update available.                                                                        |
| **`NOT_ALLOWED`**   | <code>4</code> | Update type not allowed.                                                                    |
| **`INFO_MISSING`**  | <code>5</code> | App update info missing. You must call `getAppUpdateInfo()` before requesting an update.    |


#### FlexibleUpdateInstallStatus

| Members           | Value           |
| ----------------- | --------------- |
| **`UNKNOWN`**     | <code>0</code>  |
| **`PENDING`**     | <code>1</code>  |
| **`DOWNLOADING`** | <code>2</code>  |
| **`INSTALLING`**  | <code>3</code>  |
| **`INSTALLED`**   | <code>4</code>  |
| **`FAILED`**      | <code>5</code>  |
| **`CANCELED`**    | <code>6</code>  |
| **`DOWNLOADED`**  | <code>11</code> |

</docgen-api>

## Test with internal app-sharing

The Android Developers documentation describes how to test [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) using [internal app sharing](https://support.google.com/googleplay/android-developer/answer/9303479): https://developer.android.com/guide/playcore/in-app-updates/test

## Changelog

See [CHANGELOG.md](https://github.com/robingenz/capacitor-app-update/blob/master/CHANGELOG.md).

## License

See [LICENSE](https://github.com/robingenz/capacitor-app-update/blob/master/LICENSE).
