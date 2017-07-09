# MindValley Downloader Task

A file Downloading with first class caching library for Android, and It's handle for many common pitfalls for loading image automatically like:
1. Complex image transformations with minimal memory use.
1. Automatic memory caching.
1. Abstract library that you can download with it image, file, json string.

**Usage for Downloading image:**

```java
MindValleyDownloader mindValleyDownloader = new MindValleyDownloader(context);
mindValleyDownloader.load(url, imageView);
```

**Usage for Downloading json string:**

```java
MindValleyDownloader mindValleyDownloader = new MindValleyDownloader(context);
mindValleyDownloader.load(url, new MindValleyDownloader.LoadJson() {
            @Override
            public void onJsonLoaded(String jsonObject) {
               
            }
            @Override
            public void onJsonFailed(String jsonObject) {
               
            }
        });
```

# Downoad

Add the following to the project build.gradle:

```groovy
repositories {
        jcenter()
    }
```
Add the following to your depedencies:

```groovy
compile 'com.mindvalley.downloader:minddownloader:0.0.1'
```



