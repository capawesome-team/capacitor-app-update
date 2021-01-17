import { WebPlugin } from '@capacitor/core';
import { AppUpdatePlugin } from './definitions';

export class AppUpdateWeb extends WebPlugin implements AppUpdatePlugin {
  constructor() {
    super({
      name: 'AppUpdate',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const AppUpdate = new AppUpdateWeb();

export { AppUpdate };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(AppUpdate);
