# capacitor-app-update

[![maintenance](https://img.shields.io/maintenance/yes/2021)](https://github.com/robingenz/capacitor-app-update)
[![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/robingenz/capacitor-app-update/CI/main)](https://github.com/robingenz/capacitor-app-update/actions?query=workflow%3ACI)
[![npm version](https://img.shields.io/npm/v/@robingenz/capacitor-app-update)](https://www.npmjs.com/package/@robingenz/capacitor-app-update)
[![license](https://img.shields.io/github/license/robingenz/capacitor-app-update)](https://github.com/robingenz/capacitor-app-update/blob/main/LICENSE)

⚡️ [Capacitor](https://capacitorjs.com/) plugin that assists with app updates.

> 🚧 This project is currently in active development and is not ready to be used. Check back later.

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

const getCurrentAppVersion = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  return info.currentVersion;
};

const getAvailableAppVersion = async () => {
  const info = await Plugins.AppUpdate.getAppUpdateInfo();
  return info.availableVersion;
};

const openAppStore = async () => {
  await Plugins.AppUpdate.openAppStore();
};

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
