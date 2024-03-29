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

apply plugin: 'com.stash.metalottie-plugin'
```

**Step 4. (Optional) Add theme definition to the root build gradle**

Configure a theme definition that will be used when generating new Meta-Lottie files.

_Note: You can also manually pass a theme definition when generating a new Meta-Lottie file_
```groovy
metalottie {
   themeFile = "<relative_path>/theme_file.json"
}
```

## Setup

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

## Usage

### Generating Meta-Lottie File
The Meta-Lottie file contains all the additional information needed to enhance a Lottie file (such as theming). To generate this file, you will need to preprocess your Lottie file with the Meta-Lottie plugin by running the `createMetaLottie` Gradle task.

```bash
./gradlew createMetaLottie --lottieFile="<relative_path>/src/main/res/raw/my_lottie.json" --themeFile="<relative_path>/theme_definition.json"
```
_Note: `themeFile` can be omitted if it has been defined in the Gradle configuration as seen in the installation step 4_

The Gradle task will generate a new file with the same name as the Lottie file, but with `metadata` appended (e.g. `my_lottie_metadata.json`).

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

## Usage (Compose)

### Generating Dynamic Properties
Lottie uses `LottieDynamicProperties` composable state to manipulate the Lottie animation at runtime. To automatically generate this file based on your theme, you will execute the Meta-Lottie plugin Gradle task `createMetaLottieCompose`.

```bash
./gradlew createMetaLottieCompose --lottieFile="<relative_path>/src/main/res/raw/my_lottie.json" --themeFile="<relative_path>/theme_definition.json" --sourceDir="<absolute_path>/<destination_folder>"
```
_Note: `themeFile` can be omitted if it has been defined in the Gradle configuration as seen in the installation step 4_

The Gradle task will generate a new kotlin file and expose a composable function using the name of the Lottie file (e.g. `MyLottieDynamicProperties`, `rememberMyLottieDynamicProperties`)

### Generating a ThemeColorProvider
The `ThemeColorProvider` acts as an interpreter between Lottie and the generated `DynamicProperties` state. It will provide concrete color definitions for theme tokens present in your theme definition file. To generate the `ThemeColorProvider`, you will execute the Meta-Lottie plugin Gradle task `createMetaLottieComposeTheme`.

```bash
./gradlew createMetaLottieComposeTheme --themeFile="<relative_path>/theme_definition.json" --themeName="MyThemeColorProvider" --sourceDir="<absolute_path>/<destination_folder>"
```
_Note: `themeFile` can be omitted if it has been defined in the Gradle configuration as seen in the installation step 4_

The Gradle task will generate a new kotlin file using the `themeName` as the file name used in the Gradle task (e.g. `MyThemeColorProvider`)


```kotlin
object MyThemeColorProvider : ThemeColorProvider {
    @Composable
    override fun getValue(identifier: ColorValueIdentifier): Int? {
        return when (identifier.type) {
            ColorType.STROKE -> getStrokeColorValue(key = identifier.key)
            ColorType.FILL -> getFillColorValue(key = identifier.key)
        }
    }

    @Composable
    private fun getStrokeColorValue(key: String): Int? {
        return when (key) {
            "primary" -> MaterialTheme.colors.primary.toArgb()
            "background" -> MaterialTheme.colors.primaryVariant.toArgb()
            "accent" -> MaterialTheme.colors.secondary.toArgb()
            else -> null
        }
    }

    @Composable
    private fun getFillColorValue(key: String): Int? {
        return when (key) {
            "primary" -> MaterialTheme.colors.primary.toArgb()
            "background" -> MaterialTheme.colors.primaryVariant.toArgb()
            "accent" -> MaterialTheme.colors.secondary.toArgb()
            else -> null
        }
    }
}
```

### Configuring Meta-Lottie
Meta-Lottie utilizes `Local Compoistion` within compose to propagate the Lottie metadata though the view hierarchy. To include the `LocalMetaLottie` provider in the `Local Compoistion`, you should provide it in your compose theme definition.

```kotlin
@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // Add Meta-Lottie to the local composition using the generated ThemeColorProvider
    CompositionLocalProvider(LocalMetaLottie provides MetaLottie(MyThemeColorProvider)) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}
```

### Using Meta-Lottie
MetaLottie can be called using the generated `DynamicProperties` state function.

```kotlin
@Composable
private fun LottieAnimationExample(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    
    // Get the generated DynamicProperties state for the Lottie file
    val dynamicProperties = rememberSuccessDynamicProperties()

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}
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
