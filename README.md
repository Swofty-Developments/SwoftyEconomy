# Swofty Economy
![badge](https://img.shields.io/github/v/release/Swofty-Developments/SwoftyEconomy)
![badge](https://img.shields.io/github/last-commit/Swofty-Developments/SwoftyEconomy)
[![badge](https://img.shields.io/discord/830345347867476000?label=discord)](https://discord.gg/atlasmc)
[![badge](https://img.shields.io/github/license/Swofty-Developments/SwoftyEconomy)](https://github.com/Swofty-Developments/SwoftyEconomy/blob/master/LICENSE.txt)

**[JavaDoc 1.0.0](https://swofty-developments.github.io/SwoftyEconomy/)**

A fully custom Economy plugin with no external libraries. All message are configurable. Current supported commands are;

# To Do

## Table of contents

* [Images](#images)
* [Getting started](#getting-started)
* [Setting up the configuration files](#setting-up-the-configuration-file)
* [License](#license)

## Images

## Getting started

This API does not support stand-alone usage, and you will need to add the project jar into your **plugins** folder. An updated version of the API jar can be found inside of the releases tab on the right of this readme. This projects JavaDoc (documentation for every method) can be found [here](https://swofty-developments.github.io/SwoftyEconomy/).

### Add SwoftyEconomy to your project

![badge](https://img.shields.io/github/v/release/Swofty-Developments/SwoftyEconomy)

First, you need to setup the dependency inside of your Java project. Replace **VERSION** with the version of the release.

> Maven
```xml
<dependency>
    <groupId>net.swofty</groupId>
    <artifactId>economy</artifactId>
    <version>VERSION</version>
</dependency>
```

> Gradle
```gradle
dependencies {
    implementation 'net.swofty:economy:VERSION'
}
```

## Setting up the configuration file

> CONFIG.yml
```yaml
#
#      Swofty Economy
#       Swofty#0001
#
#      Plugin Config
#

```

> MESSAGES.yml
```yaml
#
#      Swofty Economy
#       Swofty#0001
#
#     Messages Config
#

#
#  To use hex colors inside of messages, merely add the variable name and its
#  hex color value here. After you have done this you can use the variable
#  inside any of the messages
#
#  So to use this example hex color, you should use the $HEXCOLOREXAMPLE variable
#
hex-colors:
  HEXCOLOREXAMPLE: "#3CEC5D"

messages:
```

## License
SwoftyEconomy is licensed under the permissive MIT license. Please see [`LICENSE.txt`](https://github.com/Swofty-Developments/SwoftyEconomy/blob/master/LICENSE.txt) for more information.
