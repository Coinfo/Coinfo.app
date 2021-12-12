# Coinfo

## Static code analysis tools

| Tool                                                           | Config file                                                                                              | Check command                     | Fix command |
|----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|-----------------------------------|-----------------------------|
| [detekt](https://github.com/arturbosch/detekt)                 | [/.detekt](https://github.com/ChamichApps/Coinfo/blob/master/.detekt/)                                   | `./gradlew detekt`                | -                           |
| [ktlint](https://github.com/pinterest/ktlint)                  | -                                                                                                        | `./gradlew ktlintCheck`           | `./gradlew ktlintFormat`    |
| [spotless](https://github.com/diffplug/spotless)               | -                                                                                                        | `./gradlew spotlessCheck`         | `./gradlew spotlessApply`   |