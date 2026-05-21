// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SpendooApp",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(
            name: "SpendooApp",
            targets: ["SpendooApp"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "SpendooApp",
            path: "./build/XCFrameworks/release/SpendooApp.xcframework"
        ),
    ]
)
