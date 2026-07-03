import SwiftUI
import FirebaseCore
#if canImport(FirebaseCrashlytics)
import FirebaseCrashlytics
#endif
import SpendooApp
import FirebaseMessaging
import UserNotifications

#if canImport(KMPNotifier)
import KMPNotifier
#endif

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        #if DEBUG && canImport(FirebaseCrashlytics)
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(false)
        #endif

        #if canImport(KMPNotifier)
        KMPNotifier.shared.initialize(
            configuration: NotificationPlatformConfigurationIos(
                showPushNotification: false,
                askNotificationPermissionOnStart: true,
                notificationSoundName: nil
            )
        )
        #endif

        #if canImport(SpendooApp)
        IosCrashLoggerBridge.shared.delegate = { (throwable: KotlinThrowable) in
            let nsError = NSError(
                domain: "KotlinError",
                code: 0,
                userInfo: [
                    NSLocalizedDescriptionKey: throwable.message ?? "Unknown Kotlin Exception",
                    "KotlinStackTrace": throwable.stackTrace.joined(separator: "\n")
                ]
            )
            #if canImport(FirebaseCrashlytics)
            Crashlytics.crashlytics().record(error: nsError)
            #endif
        }
        #endif

        MainViewControllerKt.onApplicationStart()
        return true
    }

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }
}

@main
struct iOSApp: App {
    // register app delegate for Firebase setup
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}