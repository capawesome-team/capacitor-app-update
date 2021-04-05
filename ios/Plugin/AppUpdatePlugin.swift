import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppUpdatePlugin)
public class AppUpdatePlugin: CAPPlugin {
    private static let updateAvailabilityNotAvailable = 1
    private static let updateAvailabilityAvailable = 2

    @objc func getAppUpdateInfo(_ call: CAPPluginCall) {
        DispatchQueue.global().async {
            do {
                guard
                    let info = Bundle.main.infoDictionary,
                    let bundleId = info["CFBundleIdentifier"] as? String,
                    let currentVersion = info["CFBundleShortVersionString"] as? String,
                    let lookupUrl = URL(string: "https://itunes.apple.com/lookup?bundleId=\(bundleId)")
                else {
                    call.reject("Invalid bundle info provided")
                    return
                }
                let data = try Data(contentsOf: lookupUrl)
                guard
                    let json = try JSONSerialization.jsonObject(with: data, options: [.allowFragments]) as? [String: Any],
                    let result = (json["results"] as? [Any])?.first as? [String: Any],
                    let availableVersion = result["version"] as? String,
                    let availableVersionReleaseDate = result["currentVersionReleaseDate"] as? String
                else {
                    call.reject("Required app information could not be fetched")
                    return
                }
                var updateAvailability = AppUpdate.updateAvailabilityNotAvailable
                let updateAvailable = self.compareVersions(currentVersion, availableVersion) == .orderedDescending
                if updateAvailable {
                    updateAvailability = AppUpdate.updateAvailabilityAvailable
                }
                call.resolve([
                    "currentVersion": currentVersion,
                    "availableVersion": availableVersion,
                    "availableVersionReleaseDate": availableVersionReleaseDate,
                    "updateAvailability": updateAvailability
                ])
            } catch let error {
                call.reject(error.localizedDescription)
            }
        }
    }

    @objc func openAppStore(_ call: CAPPluginCall) {
        DispatchQueue.global().async {
            do {
                guard
                    let info = Bundle.main.infoDictionary,
                    let bundleId = info["CFBundleIdentifier"] as? String,
                    let lookupUrl = URL(string: "https://itunes.apple.com/lookup?bundleId=\(bundleId)")
                else {
                    call.reject("Invalid bundle info provided")
                    return
                }
                let data = try Data(contentsOf: lookupUrl)
                guard
                    let json = try JSONSerialization.jsonObject(with: data, options: [.allowFragments]) as? [String: Any],
                    let result = (json["results"] as? [Any])?.first as? [String: Any],
                    let trackId = result["trackId"] as? Int,
                    let storeUrl = URL(string: "itms-apps://itunes.apple.com/app/id\(trackId)")
                else {
                    call.reject("Required app information could not be fetched")
                    return
                }
                DispatchQueue.main.async {
                    UIApplication.shared.open(storeUrl) { (_) in
                        call.resolve()
                    }
                }
            } catch let error {
                call.reject(error.localizedDescription)
            }
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
