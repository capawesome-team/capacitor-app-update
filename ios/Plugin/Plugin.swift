import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppUpdate)
public class AppUpdate: CAPPlugin {
    private static let UPDATE_AVAILABILITY_NOT_AVAILABLE = 1
    private static let UPDATE_AVAILABILITY_AVAILABLE = 2

    @objc func getAppUpdateInfo(_ call: CAPPluginCall) {
        DispatchQueue.global().async {
            do {
                guard
                    let info = Bundle.main.infoDictionary,
                    let bundleId = info["CFBundleIdentifier"] as? String,
                    let currentVersion = info["CFBundleShortVersionString"] as? String,
                    let url = URL(string: "https://itunes.apple.com/lookup?bundleId=\(bundleId)")
                else {
                    call.reject("Invalid bundle info provided");
                    return;
                }
                let data = try Data(contentsOf: url)
                guard
                    let json = try JSONSerialization.jsonObject(with: data, options: [.allowFragments]) as? [String: Any],
                    let result = (json["results"] as? [Any])?.first as? [String: Any],
                    let availableVersion = result["version"] as? String,
                    let availableVersionReleaseDate = result["currentVersionReleaseDate"] as? String
                else {
                    call.reject("App update information not found");
                    return;
                }
                var updateAvailability = AppUpdate.UPDATE_AVAILABILITY_NOT_AVAILABLE
                let updateAvailable = self.compareVersions(currentVersion, availableVersion) == .orderedDescending
                if updateAvailable {
                    updateAvailability = AppUpdate.UPDATE_AVAILABILITY_AVAILABLE
                }
                call.resolve([
                    "currentVersion": currentVersion,
                    "availableVersion": availableVersion,
                    "availableVersionReleaseDate": availableVersionReleaseDate,
                    "updateAvailability": updateAvailability,
                ])
            } catch let error {
                call.reject(error.localizedDescription)
            }
        }
    }
    
    @objc func openAppStore(_ call: CAPPluginCall) {
        guard
            let info = Bundle.main.infoDictionary,
            let bundleId = info["CFBundleIdentifier"] as? String,
            let url = URL(string: "itms-apps://itunes.apple.com/app/\(bundleId)")
        else {
            call.reject("Invalid bundle info provided");
            return;
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url)
        }
    }
    
    @objc func performImmediateUpdate(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }
    
    @objc func startFlexibleUpdate(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }
    
    @objc func completeFlexibleUpdate(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }
    
    @objc func compareVersions(_ currentVersion: String, _ availableVersion: String) -> ComparisonResult {
        return availableVersion.compare(currentVersion, options: .numeric)
    }
}
