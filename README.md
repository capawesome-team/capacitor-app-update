# capacitor-app-update

[![maintenance](https://img.shields.io/maintenance/yes/2021)](https://github.com/robingenz/capacitor-app-update)
[![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/robingenz/capacitor-app-update/CI/main)](https://github.com/robingenz/capacitor-app-update/actions?query=workflow%3ACI)
[![npm version](https://img.shields.io/npm/v/@robingenz/capacitor-app-update)](https://www.npmjs.com/package/@robingenz/capacitor-app-update)
[![license](https://img.shields.io/github/license/robingenz/capacitor-app-update)](https://github.com/robingenz/capacitor-app-update/blob/main/LICENSE)

⚡️ [Capacitor](https://capacitorjs.com/) plugin that assists with app updates.

> 🚧 This project is currently under active development and has not yet been sufficiently tested.

This plugin supports retrieving app update information on **Android** and **iOS**.  
Additionally, this plugin supports [in-app updates](https://developer.android.com/guide/playcore/in-app-updates) on **Android**.

## Installation

```
npm install @robingenz/capacitor-app-update
npx cap sync
```

On **iOS**, no further steps are needed.

On **Android**, register the plugin in your main activity:

```java
import dev.robingenz.capacitor.appupdate.AppUpdate;

public class MainActivity extends BridgeActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(
        savedInstanceState,
        new ArrayList<Class<? extends Plugin>>() {

          {
            // Additional plugins you've installed go here
            // Ex: add(TotallyAwesomePlugin.class);
            add(AppUpdate.class);
          }
        }
      );
  }
}
```

## Configuration

No configuration required for this plugin.

## Usage

```js
import { Plugins } from '@capacitor/core';
import '@robingenz/capacitor-app-update';

/**
 * Supported platform(s): Android, iOS
 * Returns current app version.
 */
const getCurrentAppVersion = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  return info.currentVersion;
};

/**
 * Supported platform(s): Android, iOS
 * Returns available app version.
 */
const getAvailableAppVersion = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  return info.availableVersion;
};

/**
 * Supported platform(s): Android, iOS
 * Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).
 */
const openAppStore = async () => {
  await Plugins.AppUpdate.openAppStore();
};

/**
 * Supported platform(s): Android
 * Performs an immediate in-app update.
 */
const performImmediateUpdate = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  if (info.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (!info.immediateUpdateAllowed) {
    return;
  }
  await Plugins.AppUpdate.performImmediateUpdate();
};

/**
 * Supported platform(s): Android
 * Starts a flexible in-app update.
 */
const startFlexibleUpdate = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  if (info.updateAvailability !== AppUpdateAvailability.UPDATE_AVAILABLE) {
    return;
  }
  if (!info.flexibleUpdateAllowed) {
    return;
  }
  await Plugins.AppUpdate.startFlexibleUpdate();
};

/**
 * Supported platform(s): Android
 * Completes a flexible in-app update by restarting the app.
 */
const completeFlexibleUpdate = async () => {
  await Plugins.AppUpdate.completeFlexibleUpdate();
};
```

## API

🚧 WIP

For now, you can take a look at the [definitions.ts](https://github.com/robingenz/capacitor-app-update/blob/main/src/definitions.ts) file.

## Changelog

See [CHANGELOG.md](https://github.com/robingenz/capacitor-app-update/blob/main/CHANGELOG.md).

## License

See [LICENSE](https://github.com/robingenz/capacitor-app-update/blob/main/LICENSE).
