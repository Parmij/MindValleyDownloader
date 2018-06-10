# MindValleyDownloader

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
compile 'com.mindvalley.downloader:minddownloader:0.0.2'
```

License
--------

    Copyright 2017 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
