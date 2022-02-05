# Coinfo

## Static code analysis tools

| Tool                                                           | Config file                                                                                              | Check command                     | Fix command |
|----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|-----------------------------------|-----------------------------|
| [detekt](https://github.com/arturbosch/detekt)                 | [/.detekt](https://github.com/ChamichApps/Coinfo/blob/master/.detekt/)                                   | `./gradlew detekt`                | -                           |
| [ktlint](https://github.com/pinterest/ktlint)                  | -                                                                                                        | `./gradlew ktlintCheck`           | `./gradlew ktlintFormat`    |
| [spotless](https://github.com/diffplug/spotless)               | -                                                                                                        | `./gradlew spotlessCheck`         | `./gradlew spotlessApply`   |

## Knowledge Base

| Library Name                                                   | Useful Links                                                                                                                                                                                                                                           |
|----------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Navigation](https://developer.android.com/guide/navigation)   | [Navigation best practices for multi-module projects](https://developer.android.com/guide/navigation/navigation-multi-module), [Advanced Sample Code](https://github.com/android/architecture-components-samples/tree/master/NavigationAdvancedSample), [Deep Link with Arguments](https://stackoverflow.com/questions/50887228/jetpack-navigation-deeplink-with-query-parameters) |
