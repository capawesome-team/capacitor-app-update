import { registerWebPlugin, WebPlugin } from '@capacitor/core';
import { AppUpdateInfo, AppUpdatePlugin } from './definitions';

export class AppUpdateWeb extends WebPlugin implements AppUpdatePlugin {
  constructor() {
    super({
      name: 'AppUpdate',
      platforms: ['web'],
    });
  }

  async getAppUpdateInfo(): Promise<AppUpdateInfo> {
    throw new Error('Web platform is not supported.');
  }

  async openAppStore(): Promise<void> {
    throw new Error('Web platform is not supported.');
  }
}

const AppUpdate = new AppUpdateWeb();

export { AppUpdate };

registerWebPlugin(AppUpdate);
