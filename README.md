[![](https://jitpack.io/v/com.stash/meta-lottie-android.svg)](https://jitpack.io/#com.stash/meta-lottie-android)

# Meta-Lottie
Meta-Lottie is a Lottie file preprocessor that enhances the capabilities of Lottie through metadata.

Light | Dark
:---:|:---:
<img src="/assets/light.gif" alt="Demo Screen Capture" width="300px" /> | <img src="/assets/dark.gif" alt="Demo Screen Capture" width="300px" />


## Installation

**Step 1. Add the JitPack repository to your project build file**

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2. Add the Meta-Lottie dependency**

```groovy
dependencies {
  implementation 'com.stash.meta-lottie-android:meta-lottie:x.x.x'
}
```

**Step 3. Add the plugin to your root build gradle**
```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    
    dependencies {
        classpath 'com.stash.meta-lottie-android:meta-lottie-plugin:x.x.x'
    }
}

apply plugin: 'meta-lottie-plugin'
```

**Step 4. (Optional) Add theme definition to the root build gradle**

Configure a theme definition that will be used when generating new Meta-Lottie files.

_Note: You can also manually pass a theme definition when generating a new Meta-Lottie file_
```groovy
metalottie {
   themeFile "<path>/theme_file.json"
}
```

## Usage

### Create a Theme Definition
Definitions are broken up into `strokes` and `fills`. These define what a shapes line colors are (`strokes`) and what color to use to fill the shape (`fill`).

The token mapping object is made up of a theme token (e.g. material designs `colorPrimary`) and the color matching color hex value.
```json
{
   "strokeTokens": [
     {
      "name": "colorPrimary",
      "color": "0B1620"
    }
  ],
  "fillTokens": [
    {
      "name": "colorSecondary",
      "color": "FFFFFF"
    }
  ]
}
```

### Generating Meta-Lottie File
The Meta-Lottie file contains all the additional information needed to enhance a Lottie file (such as theming). To generate this file, you will need to preprocess your Lottie file with the Meta-Lottie plugin by running the `createMetaLottie` Gradle task.

```bash
./gradlew createMetaLottie --lottieFile="<path>/src/main/res/raw/my_lottie.json" --themeFile="<path>/theme_definition.json"
```
_Note: `themeFile` can be omitted if it has been defined in the Gradle configuration as seen in the installation step 4_

The Gradle task will generate a new file with the same name as the Lottie full, but with `metadata` appended (e.g. `my_lottie_metadata.json`).

### Defining a ThemeProvider
The `ThemeProvider` acts as an interpreter between Lottie and theme metadata stored in the Meta-Lottie file. It will provide concrete color definitions for theme tokens present in your theme definition file.

```kotlin
class MyThemeValueProvider : ThemeValueProvider {
    companion object {
        val THEME_ATTR_MAP = mapOf(
            "colorPrimary" to R.color.color1,
            "colorSecondary" to R.color.olor2,
            "colorAccent" to R.color.color_accent_1c
        )
    }

    override fun getValue(context: Context, key: String): Int? {
        return THEME_ATTR_MAP[key]?.let { context.getColor(it) }
    }
}
```

### Using Meta-Lottie
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
