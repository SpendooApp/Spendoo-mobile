import SwiftUI
import FirebaseCore
import FirebaseCrashlytics
import SpendooApp
import FirebaseMessaging
import UserNotifications

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        #if DEBUG
        Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(false)
        #endif

        KMPNotifier.shared.initialize(configuration: NotificationPlatformConfigurationIos(
                showPushNotification: false,
                askNotificationPermissionOnStart: true,
                notificationSoundName: nil
            )
        )
        
        IosCrashLoggerBridge.shared.delegate = { throwable in
            let nsError = NSError(
                domain: "KotlinError",
                code: 0,
                userInfo: [
                    NSLocalizedDescriptionKey: throwable.message ?? "Unknown Kotlin Exception",
                    "KotlinStackTrace": throwable.stackTrace.joined(separator: "\n")
                ]
            )
            Crashlytics.crashlytics().record(error: nsError)
        }
        
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