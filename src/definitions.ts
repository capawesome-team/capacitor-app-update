declare module '@capacitor/core' {
  interface PluginRegistry {
    AppUpdate: AppUpdatePlugin;
  }
}

export interface AppUpdatePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
