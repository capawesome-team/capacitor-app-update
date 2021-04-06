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

> 🚧 This project is currently under active development and has not yet been sufficiently tested. It might be changed in backward-incompatible ways.

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

## Usage

```typescript
import { AppUpdate } from '@robingenz/capacitor-app-update';

const getCurrentAppVersion = async () => {
  const info = await AppUpdate.getAppUpdateInfo();
  return info.currentVersion;
};

const getAvailableAppVersion = async () => {
  const info = await AppUpdate.getAppUpdateInfo();
  return info.availableVersion;
};

const openAppStore = async () => {
  await AppUpdate.openAppStore();
};

const performImmediateUpdate = async () => {
  const info = await AppUpdate.getAppUpdateInfo();
  if (info.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (!info.immediateUpdateAllowed) {
    return;
  }
  await AppUpdate.performImmediateUpdate();
};

const startFlexibleUpdate = async () => {
  const info = await AppUpdate.getAppUpdateInfo();
  if (info.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (!info.flexibleUpdateAllowed) {
    return;
  }
  await AppUpdate.startFlexibleUpdate();
};

const completeFlexibleUpdate = async () => {
  await AppUpdate.completeFlexibleUpdate();
};
```

## API

<docgen-index>

- [`getAppUpdateInfo()`](#getappupdateinfo)
- [`openAppStore()`](#openappstore)
- [`performImmediateUpdate()`](#performimmediateupdate)
- [`startFlexibleUpdate()`](#startflexibleupdate)
- [`completeFlexibleUpdate()`](#completeflexibleupdate)
- [`addListener('onFlexibleUpdateStateChange', ...)`](#addlisteneronflexibleupdatestatechange-)
- [Interfaces](#interfaces)
- [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAppUpdateInfo()

```typescript
getAppUpdateInfo() => Promise<AppUpdateInfo>
```

Supported platform(s): Android, iOS
Returns app update informations.

**Returns:** <code>Promise&lt;<a href="#appupdateinfo">AppUpdateInfo</a>&gt;</code>

---

### openAppStore()

```typescript
openAppStore() => Promise<void>
```

Supported platform(s): Android, iOS
Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).

---

### performImmediateUpdate()

```typescript
performImmediateUpdate() => Promise<AppUpdateResult>
```

Supported platform(s): Android
Performs an immediate in-app update.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

---

### startFlexibleUpdate()

```typescript
startFlexibleUpdate() => Promise<AppUpdateResult>
```

Supported platform(s): Android
Starts a flexible in-app update.

**Returns:** <code>Promise&lt;<a href="#appupdateresult">AppUpdateResult</a>&gt;</code>

---

### completeFlexibleUpdate()

```typescript
completeFlexibleUpdate() => Promise<void>
```

Supported platform(s): Android
Completes a flexible in-app update by restarting the app.

---

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

---

### Interfaces

#### AppUpdateInfo

| Prop                              | Type                                                                    | Description                                                                                                                            |
| --------------------------------- | ----------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **`currentVersion`**              | <code>string</code>                                                     | Supported platform(s): Android, iOS Version code (Android) or CFBundleShortVersionString (iOS) of the currently installed app version. |
| **`availableVersion`**            | <code>string</code>                                                     | Supported platform(s): Android, iOS Version code (Android) or CFBundleShortVersionString (iOS) of the update.                          |
| **`availableVersionReleaseDate`** | <code>string</code>                                                     | Supported platform(s): iOS Release date of the update in ISO 8601 (UTC) format.                                                        |
| **`updateAvailability`**          | <code><a href="#appupdateavailability">AppUpdateAvailability</a></code> | Supported platform(s): Android, iOS The app update availability.                                                                       |
| **`updatePriority`**              | <code>number</code>                                                     | Supported platform(s): Android In-app update priority for this update, as defined by the developer in the Google Play Developer API.   |
| **`immediateUpdateAllowed`**      | <code>boolean</code>                                                    | Supported platform(s): Android `true` if an immediate update is allowed, otherwise `false`.                                            |
| **`flexibleUpdateAllowed`**       | <code>boolean</code>                                                    | Supported platform(s): Android `true` if a flexible update is allowed, otherwise `false`.                                              |

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

The Android Developers documentation describes how to test [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) using [internal app sharing](https://support.google.com/googleplay/android-developer/answer/9303479): https://developer.android.com/guide/playcore/in-app-updates#internal-app-sharing

## Changelog

See [CHANGELOG.md](https://github.com/robingenz/capacitor-app-update/blob/master/CHANGELOG.md).

## License

See [LICENSE](https://github.com/robingenz/capacitor-app-update/blob/master/LICENSE).
