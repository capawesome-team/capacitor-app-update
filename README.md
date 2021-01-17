# capacitor-app-update

[![maintenance](https://img.shields.io/maintenance/yes/2021)](https://github.com/robingenz/capacitor-app-update)
[![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/robingenz/capacitor-app-update/CI/main)](https://github.com/robingenz/capacitor-app-update/actions?query=workflow%3ACI)
[![npm version](https://img.shields.io/npm/v/@robingenz/capacitor-app-update)](https://www.npmjs.com/package/@robingenz/capacitor-app-update)
[![license](https://img.shields.io/github/license/robingenz/capacitor-app-update)](https://github.com/robingenz/capacitor-app-update/blob/main/LICENSE)

⚡️ [Capacitor](https://capacitorjs.com/) plugin that assists with app updates.  

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

WIP

## API Reference+

WIP

## Changelog

See [CHANGELOG.md](https://github.com/robingenz/capacitor-app-update/blob/main/CHANGELOG.md).

## License

See [LICENSE](https://github.com/robingenz/capacitor-app-update/blob/main/LICENSE).