Note: This is a fork of the original Attribouter repository for my personal use. It is not maintained by the original author and may not receive updates or support. Use at your own risk.
Support the original author @ https://github.com/fennifith/Attribouter

Android ABP
===

This is a forked version of Attribouter that has been updated to support the latest version of Android. For the original repository, please visit the link above. Most of the relevant documentation has been left below in its original state.

Original README
===

<p align="center"><img alt="Attribouter" width="128px" style="width: 128px;" src="https://raw.githubusercontent.com/fennifith/Attribouter/main/.github/images/icon.png" /></p>
<h1 align="center">Attribouter</h1>
<p align="center">
    Attribouter is a lightweight "about screen" for Android apps, built for developers to easily credit a project's contributors & dependencies while matching the style of their app. It ships with the ability to fetch metadata directly from GitHub, GitLab, or Gitea (see: <a href="https://code.horrific.dev/james/git-rest-wrapper">git-rest-wrapper</a>), allowing contributors and licenses to be updated or modified without explicit configuration.
</p>

### Screenshots

| Contributors | Contributor | Licenses | License | Night Theme |
|--------------|-------------|----------|---------|-------------|
| ![img](./.github/images/attribouter-contributors.png?raw=true) | ![img](./.github/images/attribouter-contributor.png?raw=true) | ![img](./.github/images/attribouter-licenses.png?raw=true) | ![img](./.github/images/attribouter-license.png?raw=true) | ![img](./.github/images/attribouter-night.png?raw=true) |

### APK

A demo apk of the sample project can be downloaded [here](../../releases/).

## Usage

This library will be published on [Maven Central](https://central.sonatype.com/), to add the dependency, copy this line into your app module's build.gradle file.

#### Maven Central
```gradle
implementation 'com.itachi1706.abp:attribouter:1.0.0'
```

#### GitHub Packages
```gradle
    // Root-level Gradle file (build.gradle)
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/itachi1706/AndroidABP")
        }
    }

    // App-level Gradle file (app/build.gradle)
    implementation 'com.itachi1706.abp:attribouter:1.0.0'
```

### Starting an Activity
This is pretty simple.

``` kotlin
attribouterActivity {
    withFile(R.xml.attribouter)
    withTheme(R.style.AttribouterTheme_DayNight)
    withGitHubToken("abc123")
}
```

<details>
<summary>Java</summary>
<div class="language-java highlighter-rouge">
<pre><code>
Attribouter.from(context)
    .withFile(R.xml.attribouter)
    .withTheme(R.style.AttribouterTheme_DayNight)
    .withGitHubToken("abc123")
    .show();
</code></pre>
</div>
</details>

### Creating a Fragment
This is also pretty simple.

``` kotlin
val fragment = attribouterFragment {
    withFile(R.xml.attribouter)
    withTheme(R.style.AttribouterTheme_DayNight)
    withGitHubToken("abc123")
}
```

<details>
<summary>Java</summary>
<div class="language-java highlighter-rouge">
<pre><code>
Attribouter.from(context)
    .withFile(R.xml.attribouter)
    .withTheme(R.style.AttribouterTheme_DayNight)
    .withGitHubToken("abc123")
    .show();
</code></pre>
</div>
</details>

---

**When using the fragment** with `R.style.AttribouterTheme_DayNight` (the default theme value), be sure that your activity also [uses a dark theme](https://developer.android.com/guide/topics/ui/look-and-feel/darktheme) in the `-night` configuration, or you will have problems with text contrast (the fragment does not have a background, so the parent activity's window background will be drawn behind it). You can also call `withTheme(R.style.AttribouterTheme)` (light) or `withTheme(R.style.AttribouterTheme_Dark)` to change this behavior.

## Things to Note

### Request Limits

This library does not use an auth key for any REST APIs by default. It does cache data to avoid crossing GitHub's [rate limits](https://developer.github.com/v3/rate_limit/), but if your project has more than a few contributors and libraries *or* you want it to have access to a private repository, you will need to provide an auth token by calling `withGitHubToken(token)` on your instance of `Attribouter`. For GitLab/Gitea instances, tokens can be provided per-hostname - for example, `withToken("code.horrific.dev", token)`.

_Be careful not to include these token with your source code._ There are other methods of providing your token at build-time, such as using a [BuildConfig field](https://developer.android.com/studio/build/gradle-tips#share-custom-fields-and-resource-values-with-your-app-code) with an environment variable, that can prevent this from being an issue. These tokens aren't especially dangerous without any scopes/permissions, but GitHub will automatically deactivate them if they show up in any commits/files on their services, which could cause problems for Attribouter.

### Configuration

By default, Attribouter will use the configuration file at [res/xml/attribouter.xml](./attribouter/src/main/res/xml/attribouter.xml). You can either name your configuration file "attribouter.xml" to override the resource, or name it differently and call `withFile(R.xml.[name])` on your instance of `Attribouter` instead.

The configuration file consists of a single root element, `<about>`, with many child elements that can be placed any amount of times in any order, the same as views in a layout file. These elements, called "wedges" in this library for no apparent reason, are created by Attribouter and added to the page in the order and hierarchy that they are defined in. To create your configuration file, you can either use the [file from the sample project](./app/src/main/res/xml/about.xml) as a template or use [the documentation](https://jfenn.me/projects/attribouter/wiki) to write your own.

### Proguard / Minification

For those using the R8 compiler, Attribouter's [proguard rules](./attribouter/consumer-rules.pro) should be conveniently bundled with the library already - otherwise, you will need to add them to your app's `proguard-rules.pro` file yourself to prevent running into any issues with `minifyEnabled` and the like.

Unfortunately, Attribouter still doesn't behave well with `shrinkResources`, as the compiler cannot detect references from Attribouter's config file and will exclude them from compilation. There is a [workaround](https://developer.android.com/studio/build/shrink-code#shrink-resources) to this, however - create a `<resources>` tag somewhere in your project, and specify `tools:keep="@{resource}"` for all of the strings and drawables referenced by your config file. For all of Attribouter's own resources, this has already been done - and if you are not referencing any other resources in your configuration, then there shouldn't be an issue.

## Acknowledgements

Huge thanks to [everyone that's helped with this library](https://github.com/fennifith/Attribouter/graphs/contributors), directly or otherwise!

Also, mega props to [Kevin Aguilar](https://twitter.com/kevttob) and [221 Pixels](https://221pxls.com/) for helping improve the library's design & interface.