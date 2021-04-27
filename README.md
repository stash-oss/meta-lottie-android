# Project Name
Meta-Lottie is a Lottie file preprocessor that enhances its capabilities through metadata.

Light | Dark
:---:|:---:
<img src="/assets/light.gif" alt="Demo Screen Capture" width="300px" /> | <img src="/assets/dark.gif" alt="Demo Screen Capture" width="300px" />


## Installation

Step 1. Add the JitPack repository to your build file

```groovy
buildscript {
    dependencies {
        classpath("com.stash:meta-lottie-plugin:x.x.x")
    }
}

allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Import the plugin at the top of your `/app/build.gradle` and add the dependency

```groovy
plugins {
   id("meta-lottie-plugin")
}

dependencies {
  implementation 'com.stash:meta-lottie:x.x.x'
}
```

## Usage

MetaLottie can be called using this function:

```kotlin
MetaLottie.loadInto(
    imageView = myImageView,
    themeValueProvider = myThemeValueProvider,
    lottieResource = LottieResource(
        lottieRawRes = R.raw.my_lottie,
        lottieMetadataRawRes = R.raw.my_lottie_metadata
    )
)
```

Create your `ThemeValueProvider` class, which holds the mapping from your token to your resource colors.

```kotlin
class MyThemeValueProvider : ThemeValueProvider {
    companion object {
        val THEME_ATTR_MAP = mapOf(
            "primary" to R.color.color1,
            "accent" to R.color.color_accent_1,
            "background" to R.color.color2
        )
    }

    override fun getValue(context: Context, key: String): Int? {
        return THEME_ATTR_MAP[key]?.let { context.getColor(it) }
    }
}
```

## Plugin Usage

MetaLottie relies on a metadata file to work. The plugin is used to generate your metadata json files.

First declare your theme map file like so in your gradle file:

```groovy
metalottie { themeFile = "/Users/user1/theme_map.json" }
```

To create a metadata file, import the plugin in your dependency. Then, invoke this command on your lottie file:

```shell script
./gradlew createMetaLottie --lottieFile="/Users/user1/my_lottie.json"
```

## License
```
Copyright 2021 Stash Financial, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
