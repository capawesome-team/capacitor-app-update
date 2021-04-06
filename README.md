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

- `$androidPlayCore` version of `com.google.android.play:core` (default: `1.7.2`)

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
</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->
</docgen-api>

## Test with internal app-sharing

The Android Developers documentation describes how to test [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) using [internal app sharing](https://support.google.com/googleplay/android-developer/answer/9303479): https://developer.android.com/guide/playcore/in-app-updates#internal-app-sharing

## Changelog

See [CHANGELOG.md](https://github.com/robingenz/capacitor-app-update/blob/master/CHANGELOG.md).

## License

See [LICENSE](https://github.com/robingenz/capacitor-app-update/blob/master/LICENSE).
